package com.egghead.nicefood.message.event;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.egghead.nicefood.Constants;
import com.egghead.nicefood.check.FormatException;
import com.egghead.nicefood.error.ErrorEnum;
import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.MessageTypeEnum;
import com.egghead.nicefood.message.processor.MessageProcessor;
import com.egghead.nicefood.message.text.TextMessage;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 下午08:01:46
 * 
 */
@Component
public class EventMessageProcessor implements MessageProcessor{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public BaseSendMessage process(Map<String, Object> message , String fromUserName) throws Exception {
		
		String toUserName = fromUserName;
		String events = (String)message.get(Constants.EVENT);
		
		EventEnum event = null;
		try{
			event = EventEnum.valueOf(events);
		}catch (Exception e) {
			logger.error("invalid event "+events, e);
			throw new FormatException(ErrorEnum.ERROR_F_INVALID_EVENT_TYPE);
		}
		
		String responeContent = null;
		switch ( event ) {
			case subscribe:
				responeContent = "Hi 非常感谢关注'"+Constants.OPEN_USERNAME+"',美食尽在这里哟！";
			break;
			case unsubscribe:
				responeContent = "Hi 感谢您的关注,美食forever！";
			break;	
			case CLICK:
				//responeContent = "Hi 我已经接收到了你发出的消息'"+content+"',可惜本账号正在测试请您谅解！";
			break;
		default:
			break;
		}
		logger.debug("responeContent:["+responeContent+"]");
		TextMessage responseMsg = null;
		if(StringUtils.isEmpty(responeContent) == false ){
			responseMsg = new TextMessage(toUserName, Constants.OPEN_USERID, 0, responeContent);
		}
		logger.debug("responseMsg:["+responseMsg+"]");
		return responseMsg;
	}

	@Override
	public MessageTypeEnum fetchMessageType() {
		return MessageTypeEnum.event;
	}
}
