package com.egghead.nicefood.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.egghead.nicefood.dal.CourseDO;
import com.egghead.nicefood.dal.StepDO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午07:34:54
 * 
 */
@Component
public class CourseDAO extends BaseDAO{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private void convertJsonToArray(CourseDO courseDO){
		
		if( courseDO == null ){
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		try{
			String picsJson = courseDO.getPicsJson();
			if( StringUtils.isNotEmpty(picsJson) ){
				String[] pics = mapper.readValue(picsJson, String[].class);
				courseDO.setPics(pics);
			}
			
			String stepsJson = courseDO.getStepsJson();
			if( StringUtils.isNotEmpty(stepsJson) ){
				StepDO[] steps = mapper.readValue(stepsJson, StepDO[].class);
				courseDO.setSteps(steps);
			}
			
			String tagsJson = courseDO.getTagsJson();
			if( StringUtils.isNotEmpty(tagsJson) ){
				String[] tags = mapper.readValue(tagsJson, String[].class);
				courseDO.setTags(tags);
			}
			
			String materialsJson = courseDO.getMaterialsJson();
			if( StringUtils.isNotEmpty(materialsJson)){
				String[] materials = mapper.readValue(materialsJson, String[].class);
				courseDO.setMaterials(materials);
			}
		}catch (Exception e) {
			logger.error("convertJsonToArray failed!courseDO:"+courseDO, e);
		}
	}
	
	private void convertArrayToJson(CourseDO courseDO){
		
		if( courseDO == null ){
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		
		try{
			String[] pics = courseDO.getPics();
			if( pics != null ){
				courseDO.setPicsJson(mapper.writeValueAsString(pics));
			}
			
			StepDO[] steps = courseDO.getSteps();
			if( steps != null ){
				courseDO.setStepsJson(mapper.writeValueAsString(steps));
			}
			
			String[] tags = courseDO.getTags();
			if( tags != null ){
				courseDO.setTagsJson(mapper.writeValueAsString(tags));
			}
			
			String[] materials = courseDO.getMaterials();
			if( materials != null ){
				courseDO.setMaterialsJson(mapper.writeValueAsString(materials));
			}
		}catch (Exception e) {
			logger.error("convertArrayToJson failed!courseDO:"+courseDO, e);
		}
	}
	
	public CourseDO get(int coid) throws SQLException{
		CourseDO courseDO = sqlSessionTemplate.selectOne("get", coid);
		convertJsonToArray(courseDO);
		return courseDO;
	}
	
	/**
	 * 仅查出CourseDO部分数据
	 * 1.id
	 * 2.name
	 * 3.pic
	 * 4.desc
	 * 
	 * @param name
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public List<CourseDO> getMiniCourseByName(String name,int limit) throws SQLException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		params.put("limit", limit);
		List<CourseDO> courses = sqlSessionTemplate.selectList("getMiniCourseByName", params);
		for( CourseDO courseDO : courses ){
			convertJsonToArray(courseDO);
		}
		return courses;
	}
	
	/**
	 * 插入数据
	 * @param courseDO
	 * @return
	 */
	public int insert(CourseDO courseDO){
		convertArrayToJson(courseDO);
		return sqlSessionTemplate.insert("insert", courseDO);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode("鸡丁","UTF8"));
	}
}
