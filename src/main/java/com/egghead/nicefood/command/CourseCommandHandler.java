package com.egghead.nicefood.command;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.egghead.nicefood.Constants;
import com.egghead.nicefood.check.BaseException;
import com.egghead.nicefood.check.SysException;
import com.egghead.nicefood.dal.CourseDO;
import com.egghead.nicefood.dao.CourseDAO;
import com.egghead.nicefood.error.ErrorEnum;
import com.egghead.nicefood.lucene.CourseFieldEnum;
import com.egghead.nicefood.lucene.CourseLuceneIndex;
import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.news.NewsMessage;
import com.egghead.nicefood.message.news.NewsMessageItem;
import com.egghead.nicefood.message.text.TextMessage;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午08:01:30
 * 
 */
@Component
public class CourseCommandHandler implements CommandHandler{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private CourseLuceneIndex courseLuceneIndex;
	
	@Resource
	private CourseDAO courseDAO;
	
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
				logger.debug("query by course name");
				courses = courseLuceneIndex.search(command, Constants.MAX_NEWS_COUNT, CourseFieldEnum.NAME);
				if(courses == null || courses.size() == 0 ){//若按照名称未没找到course，则尝试按照食材查找
					logger.debug("query by material name");
					courses = courseLuceneIndex.search(command, Constants.MAX_NEWS_COUNT, CourseFieldEnum.MATREIAL);
				}
				if(courses == null || courses.size() == 0 ){//若未没找到course，则尝试按照tag查找
					logger.debug("query by tag name");
					courses = courseLuceneIndex.search(command, Constants.MAX_NEWS_COUNT, CourseFieldEnum.TAG);
				}
				//courses = courseDAO.getMiniCourseByName(command, Constants.MAX_NEWS_COUNT,2);
				//if(courses == null || courses.size() == 0 ){//若按照名称未没找到course，则尝试按照食材查找
					//courses = courseDAO.getMiniCourseByMaterial(command, Constants.MAX_NEWS_COUNT,3);
				//}
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
			logger.debug("find " + courses.size() + " courses!");
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
