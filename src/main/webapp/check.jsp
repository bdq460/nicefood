<%@ page contentType="text/html;charset=UTF8" %><%@ page import="com.egghead.nicefood.check.Checker" %><%
	String signature = request.getParameter("signature");
	String timestamp = request.getParameter("timestamp");
	String nonce = request.getParameter("nonce");
	String echostr = request.getParameter("echostr");
	System.out.println("params:"+request.getQueryString());
	
	boolean isFromWX = Checker.checkSource(signature,timestamp,nonce);
	String responeText = null;
	if( isFromWX ){
		responeText = echostr;
	}else{
		responeText = "receive msg not from weixin";
	}
%><%=responeText%>