package com.egghead.nicefood.controller;

import com.egghead.nicefood.dal.CourseDO;
import com.egghead.nicefood.dal.StepDO;
import com.egghead.nicefood.dao.CourseDAO;
import com.egghead.nicefood.dao.TagDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zhangjun.zyk
 * @since 2013-7-28 下午03:54:23
 * 
 */
@Controller
public class TagController {

	Logger logger = Logger.getLogger(this.getClass());

	@Resource
    TagDAO tagDAO;
	
	@RequestMapping("/update_tag")
	public ModelAndView update_tag() throws IOException, SQLException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("response");

        tagDAO.update_date();
		
		modelAndView.addObject("responseText", "success");
		return modelAndView;
	}
}
