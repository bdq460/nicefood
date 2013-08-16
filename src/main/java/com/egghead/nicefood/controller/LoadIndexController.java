package com.egghead.nicefood.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.egghead.nicefood.lucene.CourseLuceneIndex;

/**
 * @author zhangjun.zyk
 * @since 2013-7-28 下午03:54:23
 * 
 */
@Controller
public class LoadIndexController {

	Logger logger = Logger.getLogger(this.getClass());

	@Resource
	CourseLuceneIndex courseLuceneIndex;
	
	@RequestMapping("/loadIndex")
	public ModelAndView loadIndex() throws IOException, SQLException {

		logger.debug("start load index");
		courseLuceneIndex.initIndex();
		logger.debug("end load index");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("response");
		modelAndView.addObject("responseText", "start reload index ... ...");
		return modelAndView;
	}
}