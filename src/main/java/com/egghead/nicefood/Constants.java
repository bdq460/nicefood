package com.egghead.nicefood;



/**
 * @author zhangjun.zyk 
 * @since 2013-7-22 07:57:32
 * 
 */
public class Constants {

	public static final String TOKEN = "nicefood";
	
	//公共账号名
	public static final String OPEN_USERNAME = "美味厨房";
	//公共账号ID
	public static final String OPEN_USERID = "gh_bcca10da9998";
	
	public static final String TOUSERNAME = "ToUserName";
	public static final String FROMUSERNAME = "FromUserName";
	public static final String CREATETIME = "CreateTime";
	public static final String MSGID = "MsgId";
	public static final String MSGTYPE = "MsgType";
	public static final String FUNCFLAG = "FuncFlag";
	public static final String CONTENT = "Content";
	public static final String EVENT = "Event";
	public static final String EVENTKEY = "EventKey";
	
	//多图文消息包含消息最大个数
	public static final int MAX_NEWS_COUNT = 3;
	
	//Course信息获取url,${coid}进行变量替换
	public static final String COURSE_URL = "http://115.28.47.106/nicefood/course.do?coid=${coid}";
}


