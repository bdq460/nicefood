package com.egghead.nicefood.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.egghead.nicefood.biz.BizEngine;
import com.egghead.nicefood.check.WeixinMessageChecker;
import com.egghead.nicefood.message.MessageTransformer;

/**
 * Servlet implementation class Processor
 */
@WebServlet("/process")
public class ProcessServlet extends HttpServlet {
	
	private static final String MSG_NOT_FROM_WX = "receive msg not from weixin";
	
	private BizEngine bizEngine;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		bizEngine = (BizEngine) wac.getBean("bizEngine");
	}
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("UTF8");
		//response.setCharacterEncoding("UTF8");
		response.setContentType("text/html;charset=UTF8");
		
		String echostr = request.getParameter("echostr");
		boolean isFromWX = checkIsFromWX(request,response);
		String responeText = null;
		if( isFromWX ){
			responeText = echostr;
		}else{
			responeText = MSG_NOT_FROM_WX;
		}
		PrintWriter writer = response.getWriter();
		writer.write(responeText);
		writer.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//request.setCharacterEncoding("UTF8");
		//response.setCharacterEncoding("UTF8");
		response.setContentType("text/html;charset=UTF8");
		
		boolean isFromWX = true;//checkIsFromWX(request,response);
		if( isFromWX == false ){
			PrintWriter writer = response.getWriter();
			writer.write(MSG_NOT_FROM_WX);
			writer.flush();
			return;
		}
		
		Enumeration<String> headerNames = request.getHeaderNames();
		logger.debug("------ request header start -------");
		while ( headerNames.hasMoreElements() ) {
			String headerName = headerNames.nextElement();
			logger.debug(headerName+":"+request.getHeader(headerName));
		}
		logger.debug("------ request header end -------");
		
		StringBuffer postData = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null){
				postData.append(line);
			}
		} catch (Exception e) {
			logger.error("read post data error!",e);
		}
		
		logger.debug("postData:["+postData.toString()+"]");
		
		Map<String,Object> messageMap = null;
		try{
			messageMap = MessageTransformer.xml2Map(postData.toString());
			if( messageMap == null || messageMap.isEmpty() ){
				throw new Exception("request message map is empty!");
			}
		}catch (Exception e) {
			logger.warn("xml not well format!message:["+postData.toString()+"]",e);
			response.getWriter().print("request xml not well format!");
			response.getWriter().flush();
			return;
		}
		
		logger.debug("messageMap:["+messageMap+"]");

		try{
			String resultMessage = bizEngine.process(messageMap);
			if( StringUtils.isNotBlank(resultMessage) ){
				response.getWriter().write(resultMessage);
			}
			response.getWriter().flush();
		}catch (Exception e) {
			logger.error("process message failed!message:["+postData.toString()+"]",e);
		}
	}
	
	private boolean checkIsFromWX(HttpServletRequest request , HttpServletResponse response)  throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		logger.debug("receive queryString [" + request.getQueryString()+ "]");

		boolean isFromWX = WeixinMessageChecker.checkSource(signature, timestamp, nonce);
		
		logger.debug("isFromWX:["+isFromWX+"]");
		return isFromWX;
	}
}
