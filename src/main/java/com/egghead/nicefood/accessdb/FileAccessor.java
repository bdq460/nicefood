package com.egghead.nicefood.accessdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.egghead.nicefood.dal.CourseDO;
import com.egghead.nicefood.dal.StepDO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangjun.zyk
 * @since 2013-8-3 02:32:22
 * 
 */
public class FileAccessor {

	final static String HOST = "115.28.47.106";

	public static void main(String[] args) throws Exception {
		File sourceFile = new File(
				"D:/personal/learn/weixin/projects/nicefood.txt");
		File recordFile = new File(
				"D:/personal/learn/weixin/projects/record.txt");
		File errorFile = new File(
		"D:/personal/learn/weixin/projects/error.txt");
		upload(sourceFile, recordFile,errorFile);
		//System.out.println(trimS(". fad"));
		//String mm = "1247	TRUE	FALSE	鸡蛋青瓜炒虾仁	http://bcs.duapp.com/xiachufangnew/recipe_pic/526/aa/cf/175296.1.jpg	家常菜#快手菜#		虾仁(200克)#青瓜(半条)#鸡蛋(2个)#生姜()#葱()#生抽()#料酒()#生粉(少许)#	\"#SNUM#1#SACT#将虾仁挑去沙线，放入少许盐、料酒腌制二十分钟#SNUM#2#SACT#青瓜洗净先切成约3厘米的段,再将其切成片;鸡蛋打散成蛋液，姜切片，葱切成段并将葱白与葱叶分开#SNUM#3#SACT#热锅放油，倒入蛋液，快速划炒成小块状后舀出待用#SNUM#4#SACT#锅内再放油，下入姜片与葱白爆香，再放入腌制好的虾仁，快速翻炒几下后放入青瓜，大火翻炒一分钟左右放入事先炒好的鸡蛋#SNUM#5#SACT#加入适量的盐、葱段、生抽炒匀，再倒入水淀粉勾薄芡即可\"	http://www.xiachufang.com/recipe/175296/";
		//CourseDO courseDO = parseCourse(mm);
		//System.out.println(courseDO);
	}

	public static void upload(File sourceFile, File recordFile,File errorFile)
			throws Exception {

		Set<String> uploadedUrls = readUploaded(recordFile);
		FileWriter recordWriter = new FileWriter(recordFile, true);
		FileOutputStream errorFileOS = new FileOutputStream(errorFile,true);
		OutputStreamWriter errorWriter = new OutputStreamWriter(errorFileOS, "UTF8");
		FileInputStream fis = new FileInputStream(sourceFile);
		InputStreamReader isr = new InputStreamReader(fis, "UNICODE");
		BufferedReader bufferedReader = new BufferedReader(isr);
		String line = null;
		int i = 0;
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println("upload " + i++);
			CourseDO courseDO = null;
			try {
				courseDO = parseCourse(line);
				
				if (courseDO != null
						&& uploadedUrls.contains(courseDO.getSourceUrl()) == false) {
					uploadCourseDO(courseDO);
					recordUploaded(courseDO, recordWriter);
					uploadedUrls.add(courseDO.getSourceUrl());
					System.out.println("courseDO "+courseDO);
				}else if( courseDO == null ){
					System.out.println("line " + line);
				}
			} catch (Exception e) {
				errorWriter.write("error line:" + line+"\n");
				errorWriter.flush();
				System.err.println("error line:" + line);
				System.err.println("courseDO :" + courseDO);
				//throw e;
			}
		}
		recordWriter.flush();
		recordWriter.close();
		errorWriter.flush();
		errorWriter.close();
	}

	/**
	 * @param recordFile
	 * @return
	 * @throws Exception
	 */
	private static Set<String> readUploaded(File recordFile) throws Exception {
		Set<String> uploadedUrls = new HashSet<String>();

		if (recordFile.exists() == false) {
			return uploadedUrls;
		}
		FileInputStream fis = new FileInputStream(recordFile);
		InputStreamReader isr = new InputStreamReader(fis, "UTF8");
		BufferedReader bufferedReader = new BufferedReader(isr);
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			uploadedUrls.add(line);
		}
		bufferedReader.close();
		return uploadedUrls;
	}

	/**
	 * @param courseDO
	 * @throws IOException
	 */
	private static void recordUploaded(CourseDO courseDO,
			FileWriter recordWriter) throws IOException {
		recordWriter.write(courseDO.getSourceUrl() + "\n");
		recordWriter.flush();
	}

	/**
	 * @param courseDO
	 * @throws MalformedURLException
	 */
	private static void uploadCourseDO(CourseDO courseDO) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String courseJson = mapper.writeValueAsString(courseDO);
		String uploadUrl = "http://" + HOST + "/nicefood/addCourse.do";
	
		URL url = new URL(uploadUrl);
		URLConnection urlConnection = url.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
		httpURLConnection.setRequestMethod("POST");
		//httpURLConnection.setRequestProperty("connection", "Keep-Alive"); 
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setInstanceFollowRedirects(false);
		//httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		httpURLConnection.connect();
		PrintWriter out = new PrintWriter(httpURLConnection.getOutputStream());
		out.print("course="+URLEncoder.encode(courseJson,"UTF8"));
		out.flush();
		out.close();
		//httpURLConnection.connect();
		//out close前不能打开input
		InputStream is = httpURLConnection.getInputStream();
		is.read();
		is.close();
		httpURLConnection.disconnect();
	}

	public static String trimS(String str){
		return StringUtils.strip(str, " .．");
	}
	
	public static CourseDO parseCourse(String fline ) {

		String line = StringEscapeUtils.unescapeHtml3(fline);
		line = fline.replaceAll("<[^>]+>", "");
		line = line.replaceAll("<*?>", line);
		String[] fields = line.split("\t");
		if (NumberUtils.isDigits(fields[0]) == false) {
			return null;
		}
		int lineNumber = Integer.parseInt(fields[0]);
		String name = fields[3];
		String pic = fields[4];
		String[] pics = new String[1];
		pics[0] = pic;
		String tag = fields[5];
		String[] tags = tag.split("#");
		String description = fields[6];
		description = trimS(description);
		String materials = fields[7];
		String[] materialArray = StringUtils.replace(materials, "()", "")
				.split("#");
		String steps = fields[8];
		steps = StringUtils.strip(steps, "\"");
		List<StepDO> stepDOList = new ArrayList<StepDO>();
		String[] stepss = steps.split("#SNUM#", -1);
		int stepNum = 1;
		if (stepss != null) {
			for (String step : stepss) {
				if (StringUtils.isEmpty(step)) {
					continue;
				}
				step = trimS(step);
				String[] stepFields = step.split("#SACT#");
				StepDO stepDO = new StepDO();
				stepDO.setNumber(Integer.parseInt(stepFields[0]));
				if( stepFields.length >= 2 ){
					String stepActionPic = stepFields[1];
					String[] actionFields = stepActionPic.split("#SIMG#");
					if( actionFields.length >= 1 ){
						stepDO.setNumber(stepNum++);
						stepDO.setAction(actionFields[0]);
						if (actionFields.length >= 2) {
							stepDO.setPicUrl(actionFields[1]);
						}
						stepDOList.add(stepDO);
					}
				}
			}
		}

		StepDO[] stepDOs = stepDOList.toArray(new StepDO[0]);
		String sourceUrl = fields[9];

		CourseDO courseDO = new CourseDO();
		courseDO.setName(name);
		courseDO.setPics(pics);
		courseDO.setTags(tags);
		courseDO.setMaterials(materialArray);
		courseDO.setSteps(stepDOs);
		courseDO.setDescription(description);
		courseDO.setSourceUrl(sourceUrl);
		return courseDO;
	}
}
