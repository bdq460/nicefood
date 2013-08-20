package com.egghead.nicefood.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.egghead.nicefood.dal.CourseDO;
import com.egghead.nicefood.dao.CourseDAO;

/**
 * 课程lucene 
 * @author zhangjun.zyk 
 * @since 2013-8-16 下午05:38:57
 *
 */
public class CourseLuceneIndex {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private static final Version usingVersion = Version.LUCENE_33;
	private static final int pageSize = 10000;
	
	@Resource
	private CourseDAO courseDAO;
	
	private String indexDirectory;
	private final Analyzer analyzer;
	Directory directory = null;
	private IndexSearcher searcher;
	private AtomicBoolean isBuildingIndex = new AtomicBoolean(false);
	private AtomicBoolean isBuilded = new AtomicBoolean(false);
	/**
	 * 生成course lucene
	 * @param directory
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public CourseLuceneIndex(String indexDirectory) throws CorruptIndexException, IOException{
		analyzer = new StandardAnalyzer(usingVersion);
		this.indexDirectory = indexDirectory;
	}
	
	public void initIndex() throws IOException{
		
		if( isBuildingIndex.getAndSet(true) == false && isBuilded.get() == false){
			logger.debug("load index data from " + indexDirectory);
			directory = FSDirectory.open(new File(indexDirectory));
			if( directory.listAll() == null || directory.listAll().length == 0 ){
				logger.debug("start load data from db");
				loadFromDB();
				logger.debug("end load data from db");
			}
			
			IndexReader reader = IndexReader.open(directory);
			searcher = new IndexSearcher(reader);
			isBuilded.set(true);
			isBuildingIndex.set(false);
		}
	}
	
	public void loadFromDB() throws IOException{
		
		IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(
				usingVersion, analyzer));
		
		String startTime = "1970-01-01";
		logger.debug("query course count from " + startTime);
		int count = courseDAO.getCourseCountByModified(startTime);
		logger.debug("fetch course count = " + count);
		if ( count <= 0 ) {
			return;
		}
		
		//获取所有course创建doc
		for( int start = 0 ; start < count ; start = start + pageSize ){
			logger.debug("fetch course start = " + start + " , pageSize = " + pageSize);
			List<CourseDO> courseDOs = courseDAO.getCourseByModified(startTime, start, pageSize);
			for ( CourseDO courseDO : courseDOs) {
				Document doc = formDocument(courseDO);
				writer.addDocument(doc);
			}
		}

		writer.commit();
		writer.close();
		
		
	}
	
	/**
	 * 根据course创建document
	 * @param courseDO
	 * @return
	 */
	private Document formDocument(CourseDO courseDO){
		
		Document doc = new Document();
		doc.add(new Field(CourseFieldEnum.COID.toString(), String.valueOf(courseDO.getCoid()),
				Store.YES, Index.NOT_ANALYZED));
		
		doc.add(new Field(CourseFieldEnum.NAME.toString(), courseDO.getName(), Store.YES, Index.ANALYZED));
		
		if (courseDO.getDescription() != null ) {
			doc.add(new Field(CourseFieldEnum.DESC.toString(), courseDO.getDescription(), Store.YES, Index.NOT_ANALYZED));
		}
		
		if( courseDO.getPics() != null && courseDO.getPics().length >= 1 ){
			doc.add(new Field(CourseFieldEnum.PIC.toString(), courseDO.getPics()[0], Store.YES, Index.NOT_ANALYZED));
		}
		
		if( courseDO.getMaterials() != null ){
			for(String material : courseDO.getMaterials() ){
				doc.add(new Field(CourseFieldEnum.MATREIAL.toString(), material, Store.NO, Index.ANALYZED));
			}
		}
		
		if( courseDO.getTags() != null ){
			for(String tag : courseDO.getTags() ){
				doc.add(new Field(CourseFieldEnum.TAG.toString(), tag, Store.NO, Index.ANALYZED));
			}
		}

		return doc;
	}
	
	public List<CourseDO> search(String keywords , int counts , CourseFieldEnum courseField , final FieldComparator<?> fieldComparator )
			throws CorruptIndexException, IOException {
		
		if (StringUtils.isBlank(keywords) || courseField == null ) {
			return null;
		}
		
		if( isBuilded.get() == false ){
			initIndex();
		}
		
		if( searcher == null ){
			logger.error("index should not be null");
			throw new IllegalStateException("index should not be null");
		}
		
		BooleanQuery query = new BooleanQuery();

		BooleanQuery fieldQuery = new BooleanQuery();
		try {
			QueryParser qp = new QueryParser(usingVersion, courseField.toString(), analyzer);
			qp.setDefaultOperator(Operator.AND);
			qp.setAutoGeneratePhraseQueries(true);
			qp.setPhraseSlop(5);
			fieldQuery.add(qp.parse(keywords.toLowerCase()),
					BooleanClause.Occur.SHOULD);
		} catch (ParseException e) {
			logger.error("关键词解析错误keywords=[" + keywords + "]", e);
			throw new IOException("关键词解析错误", e);
		}
		query.add(fieldQuery, BooleanClause.Occur.MUST);
		
		TopDocs topDocs = null;
		if( fieldComparator == null ){
			topDocs = searcher.search(query, counts);
		}else {
			
			Sort sort = new Sort(new SortField(courseField.toString(), new FieldComparatorSource() {
				
				private static final long serialVersionUID = -5165274913716136490L;

				@Override
				public FieldComparator<?> newComparator(String fieldname, int numHits,
						int sortPos, boolean reversed) throws IOException {
					return fieldComparator;
				}
			}));
			topDocs = searcher.search(query , counts , sort);
		}
		return parseDocs(topDocs);
	}

	private List<CourseDO> parseDocs(TopDocs docs) throws CorruptIndexException,
			IOException {
		if (docs == null || docs.scoreDocs == null
				|| docs.scoreDocs.length == 0) {
			return null;
		}

		List<CourseDO> l = new ArrayList<CourseDO>(docs.scoreDocs.length);
		for (ScoreDoc scoreDoc : docs.scoreDocs) {
			CourseDO courseDO = formCourseDO(scoreDoc);
			l.add(courseDO);
		}
		return l;
	}

	/**
	 * @param scoreDoc
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	private CourseDO formCourseDO(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
		
		CourseDO courseDO =  new CourseDO();
		
		Document doc = searcher.doc(scoreDoc.doc);
		
		logger.debug("doc="+doc);
		
		Integer coid = Integer.valueOf(doc.get(CourseFieldEnum.COID.toString()));
		courseDO.setCoid(coid);
		
		String name = doc.get(CourseFieldEnum.NAME.toString());
		courseDO.setName(name);
		
		String pic = doc.get(CourseFieldEnum.PIC.toString());
		if( pic != null ){
			courseDO.setPics(new String[]{pic});
		}
		
		String desc = doc.get(CourseFieldEnum.DESC.toString());
		if( desc != null ){
			courseDO.setDescription(desc);
		}
		
		return courseDO;
	}
	
	
}
