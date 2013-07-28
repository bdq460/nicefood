package com.egghead.nicefood.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egghead.nicefood.dal.CourseDO;

/**
 * @author zhangjun.zyk
 * @since 2013-7-28 下午03:54:23
 * 
 */
@Controller
public class CourseController {

	Logger logger = Logger.getLogger(this.getClass());

	@RequestMapping("/course")
	public ModelAndView fetchCourse(@RequestParam("coid") int coid) throws IOException, SQLException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("course");
		
		//CourseDO courseDO = CourseDAO.get(coid);
		CourseDO courseDO = new CourseDO();
		courseDO.setCoid(123);
		courseDO.setName("荔枝豆腐");
		courseDO.setDescription("制作简单,营养丰富,好吃,好好吃 ^_^!!! 对不住各位吃货!小厨!还在测试中~~~,即将与各位见面");
		String[] pics = new String[]{"http://cul.shangdu.com/chinacul/20101222/P_158100_1__408346349.jpg"};
		courseDO.setPics(pics);
		modelAndView.addObject("course", courseDO);
		
		return modelAndView;
	}
}
