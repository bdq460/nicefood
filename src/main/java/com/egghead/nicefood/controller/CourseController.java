package com.egghead.nicefood.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egghead.nicefood.dal.CourseDO;
import com.egghead.nicefood.dal.StepDO;
import com.egghead.nicefood.dao.CourseDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangjun.zyk
 * @since 2013-7-28 下午03:54:23
 * 
 */
@Controller
public class CourseController {

	Logger logger = Logger.getLogger(this.getClass());

	@Resource
	CourseDAO courseDAO;
	
	@RequestMapping("/course")
	public ModelAndView fetchCourse(@RequestParam("coid") int coid) throws IOException, SQLException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("course");
		
		logger.debug("start fetch courseDO coid:"+coid);
		CourseDO courseDO = courseDAO.get(coid);
		logger.debug("fetch courseDO:"+courseDO);
		
		modelAndView.addObject("course", courseDO);
		return modelAndView;
	}
	
	//requestParam可获取get与post方法传递的参数
	@RequestMapping("/addCourse")
	public ModelAndView addCourse(@RequestParam("course") String courseJson) throws IOException, Exception {
		
		try{
			ObjectMapper objmapper = new ObjectMapper();
			CourseDO courseDO = objmapper.readValue(courseJson, CourseDO.class);	
			int id = courseDAO.insert(courseDO);
			int coid = courseDO.getCoid();
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("addCourseResult");
			modelAndView.addObject("id", id);
			modelAndView.addObject("coid", coid);
			return modelAndView;
		}catch (Exception e) {
			logger.error("addCourse failed!course="+courseJson,e);
			throw e;
		}
	}
	
	@RequestMapping("/addTestCourse")
	public ModelAndView addTestCourse() throws IOException, SQLException {
		
		CourseDO courseDO = new CourseDO();
		
		courseDO.setName("荔枝豆腐");
		courseDO.setDescription("制作简单,营养丰富,好吃,好好吃 ^_^!!! 对不住各位吃货!小厨!还在测试中~~~,即将与各位见面");
		String[] pics = new String[]{"http://cul.shangdu.com/chinacul/20101222/P_158100_1__408346349.jpg",
				"http://m2.img.libdd.com/farm4/2013/0727/23/54B920861E644C7A142EB2D5054205859D8067C9EAB8F_500_593.jpg"};
		courseDO.setPics(pics);
		
		String[] materials = new String[]{"小葱(1根)","麻酱(2两)"};
		courseDO.setMaterials(materials);
		
		StepDO[] stepDOs = new StepDO[2];
		stepDOs[0] = new StepDO();
		stepDOs[1] = new StepDO();
		stepDOs[0].setNumber(1).setAction("拿出锅").setPicUrl("http://pic0");
		stepDOs[1].setNumber(2).setAction("开始做").setPicUrl("http://pic1");
 		courseDO.setSteps(stepDOs);
 		
 		String[] tags = new String[]{"夜宵","甜点"};
 		courseDO.setTags(tags);
 		
 		courseDO.setSourceUrl("http://www.baidu.com");
		
		int id = courseDAO.insert(courseDO);
		int coid = courseDO.getCoid();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("courseTest");
		modelAndView.addObject("id", id);
		modelAndView.addObject("coid", coid);
		return modelAndView;
	}
	
	@RequestMapping("/miniCourse")
	public ModelAndView miniCourse(@RequestParam("name") String name) throws IOException, SQLException {
		
		List<CourseDO> courseDOs = courseDAO.getMiniCourseByName(name, 3 , 2);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("courseTest");
		modelAndView.addObject("size", courseDOs.size());
		for( int i = 0 ; i < courseDOs.size() ; i++ ){
			modelAndView.addObject("course"+i, courseDOs.get(i));
		}
		return modelAndView;
	}
	
	@RequestMapping("/test")
	public ModelAndView test(HttpServletRequest request, 
	        HttpServletResponse response) throws IOException, SQLException {
		
		logger.debug("-------request header start---------");
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String header = (String) headerNames.nextElement();
			String value = request.getHeader(header);
			logger.debug(header+":"+value);
		}
		logger.debug("-------request header end-----------");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("courseTest");
		return modelAndView;
	}
}
