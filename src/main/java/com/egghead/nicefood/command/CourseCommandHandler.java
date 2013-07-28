package com.egghead.nicefood.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.egghead.nicefood.Constants;
import com.egghead.nicefood.check.BaseException;
import com.egghead.nicefood.check.SysException;
import com.egghead.nicefood.dal.CourseDO;
import com.egghead.nicefood.dao.CourseDAO;
import com.egghead.nicefood.error.ErrorEnum;
import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.news.NewsMessage;
import com.egghead.nicefood.message.news.NewsMessageItem;
import com.egghead.nicefood.message.text.TextMessage;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午08:01:30
 * 
 */
public class CourseCommandHandler implements CommandHandler{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public BaseSendMessage handle(String command, String fromUserName,
			Map<String, Object> message) throws BaseException{
		
		logger.debug("receive command [" + command + "] from user [" + fromUserName + "] with message [" + message+"]");
		
		BaseSendMessage responseMsg = null;
		String toUserName = fromUserName;
		
		if( command.equals("text") ){
			String responeContent = "Hi 我已经接收到了你发出的消息'"+command+"',可惜本账号正在测试请您谅解！";
			responseMsg = new TextMessage(toUserName, Constants.OPEN_USERID, 0, responeContent);
		}else if( command.equals("test") ) {
			NewsMessage newsMessage = new NewsMessage(toUserName, Constants.OPEN_USERID, 0);
			NewsMessageItem item = new NewsMessageItem("这是个测试消息", "我已经接收到了你发出的消息'"+command+"',可惜本账号正在测试请您谅解！", "http://m2.img.libdd.com/farm4/2013/0725/16/F255AF63BB2180E4C5B12D2ED746E5B7C768FF47D599C_500_332.jpg", "http://www.baidu.com");
			newsMessage.addItem(item);
			responseMsg = newsMessage;
		}else{
			List<CourseDO> courses;
			try {
				//courses = CourseDAO.getMiniCourseByName(command, Constants.MAX_NEWS_COUNT);
				courses = new ArrayList<CourseDO>();
				CourseDO courseDO = new CourseDO();
				courseDO.setCoid(123);
				courseDO.setName(command);
				String[] pics = new String[]{"http://cul.shangdu.com/chinacul/20101222/P_158100_1__408346349.jpg"};
				courseDO.setPics(pics);
				courseDO.setDescription("食材简单,酸甜可口");
				courses.add(courseDO);
			} catch (Exception e) {
				logger.error("query mini course by name error!command="+command,e);
				throw new SysException(ErrorEnum.ERROR_S_SERVER_ERROR);
			}
			if(courses == null || courses.size() == 0 ){
				logger.debug("not find course!courses is " + courses);
				String responeContent = "Sorry!小厨有罪,没找到這道菜!!泪奔找菜去了 555 555 %>_<%";
				responseMsg = new TextMessage(toUserName, Constants.OPEN_USERID, 0, responeContent);
				return responseMsg;
			}
			logger.debug("findd " + courses.size() + " course!");
			NewsMessage newsMessage = new NewsMessage(toUserName, Constants.OPEN_USERID, 0);
			for (CourseDO courseDO : courses ) {
				int coid = courseDO.getCoid();
				String description = StringUtils.defaultString(courseDO.getDescription(), "");
				String[] pics = courseDO.getPics();
				String picUrl = "";
				if( pics != null && pics.length > 0 ){
					picUrl = pics[0];
				}
				String courseUrl = StringUtils.replace(Constants.COURSE_URL,"${coid}",String.valueOf(coid));
				NewsMessageItem item = new NewsMessageItem(courseDO.getName(), description, picUrl, courseUrl);
				newsMessage.addItem(item);
			}
			responseMsg = newsMessage;
		}
		return responseMsg;
	}
}