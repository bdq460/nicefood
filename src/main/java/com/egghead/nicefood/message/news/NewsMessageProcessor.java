package com.egghead.nicefood.message.news;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.MessageTypeEnum;
import com.egghead.nicefood.message.processor.MessageProcessor;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 下午08:01:46
 * 
 */
@Component
public class NewsMessageProcessor implements MessageProcessor{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public BaseSendMessage process(Map<String, Object> message , String fromUserName) throws Exception {
		
		String toUserName = fromUserName;
		NewsMessage responseMsg = new NewsMessage(toUserName, fromUserName, 0);
		NewsMessageItem item = new NewsMessageItem("这是个测试消息", "测试描述", "http://pcdn.500px.net/41047288/660ab983a0a86a7bb42474d5d7dd2144b0010588/2048.jpg", "http://www.baidu.com");
		responseMsg.addItem(item);
		logger.debug("responseMsg:["+responseMsg+"]");
		return responseMsg;
	}

	@Override
	public MessageTypeEnum fetchMessageType() {
		return MessageTypeEnum.news;
	}
}
