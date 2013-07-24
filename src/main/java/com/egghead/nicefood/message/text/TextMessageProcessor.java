package com.egghead.nicefood.message.text;

import java.util.Map;

import org.apache.log4j.Logger;

import com.egghead.nicefood.Constants;
import com.egghead.nicefood.message.MessageTypeEnum;
import com.egghead.nicefood.message.processor.MessageProcessor;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 下午08:01:46
 * 
 */
public class TextMessageProcessor implements MessageProcessor{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Map<String, Object> process(Map<String, Object> message , String fromUserName) throws Exception {
		
		String content = (String)message.get(Constants.CONTENT);
		String toUserName = fromUserName;
		String responeContent = "Hi 我已经接收到了你发出的消息'"+content+"',可惜本账号正在测试请您谅解！";
		Map<String, Object> responseMsg = TextMessageFormer.formMessage(Constants.OPEN_USERNAME,toUserName,responeContent);
		
		logger.debug("responseMsg:["+responseMsg+"]");
		
		return responseMsg;
	}

	@Override
	public MessageTypeEnum fetchMessageType() {
		return MessageTypeEnum.text;
	}
}
