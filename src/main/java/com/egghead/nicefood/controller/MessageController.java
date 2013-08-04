package com.egghead.nicefood.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.egghead.nicefood.biz.BizEngine;
import com.egghead.nicefood.check.WeixinMessageChecker;
import com.egghead.nicefood.message.MessageTransformer;

/**
 * @author zhangjun.zyk
 * @since 2013-7-28 下午03:54:23
 * 
 */
@Controller
@RequestMapping("/process")
public class MessageController {

	Logger logger = Logger.getLogger(this.getClass());

	private static final String MSG_NOT_FROM_WX = "receive msg not from weixin";

	@Resource
	private BizEngine bizEngine;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam("echostr") String echostr) throws IOException {
		boolean isFromWX = checkIsFromWX(signature, timestamp, nonce);
		String responseText = null;
		if (isFromWX) {
			responseText = echostr;
		} else {
			responseText = MSG_NOT_FROM_WX;
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("response");
		modelAndView.addObject("responseText", responseText);
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void doPost(@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			HttpServletRequest request, 
	        HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=UTF8");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("response");

		//String responseText = null;
		
		boolean isFromWX = true;//checkIsFromWX(signature, timestamp, nonce);
		if (isFromWX == false) {
			PrintWriter writer = response.getWriter();
			writer.write(MSG_NOT_FROM_WX);
			writer.flush();
			return;
		}

		Enumeration<String> headerNames = request.getHeaderNames();
		logger.debug("------ request header start -------");
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			logger.debug(headerName + ":" + request.getHeader(headerName));
		}
		logger.debug("------ request header end -------");

		StringBuffer postData = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				postData.append(line);
			}
		} catch (Exception e) {
			logger.error("read post data error!", e);
		}

		logger.debug("postData:[" + postData.toString() + "]");

		Map<String, Object> messageMap = null;
		try {
			messageMap = MessageTransformer.xml2Map(postData.toString());
			if (messageMap == null || messageMap.isEmpty()) {
				throw new Exception("request message map is empty!");
			}
		} catch (Exception e) {
			logger.warn("xml not well format!message:[" + postData.toString()
					+ "]", e);
			response.getWriter().print("request xml not well format!");
			response.getWriter().flush();
			return;
		}

		logger.debug("messageMap:[" + messageMap + "]");

		try {
			String resultMessage = bizEngine.process(messageMap);
			if (StringUtils.isNotBlank(resultMessage)) {
				response.getWriter().write(resultMessage);
			}
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error(
					"process message failed!message:[" + postData.toString()
							+ "]", e);
		}
	}

	private boolean checkIsFromWX(String signature, String timestamp,
			String nonce) throws IOException {

		logger.debug("signature=" + signature + ",timestamp=" + timestamp
				+ ",nonce=" + nonce);
		boolean isFromWX = WeixinMessageChecker.checkSource(signature,
				timestamp, nonce);
		logger.debug("isFromWX:[" + isFromWX + "]");
		return isFromWX;
	}
}
