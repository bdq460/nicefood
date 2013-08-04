package com.egghead.nicefood.message.text;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.egghead.nicefood.Constants;
import com.egghead.nicefood.command.CommandHandler;
import com.egghead.nicefood.command.CommandHandlerFactory;
import com.egghead.nicefood.command.CommandTypeEnum;
import com.egghead.nicefood.command.CommandTypeParser;
import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.MessageTypeEnum;
import com.egghead.nicefood.message.processor.MessageProcessor;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 下午08:01:46
 * 
 */
@Component
public class TextMessageProcessor implements MessageProcessor{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	CommandTypeParser commandTypeParser;
	
	@Resource
	CommandHandlerFactory commandHandlerFactory;
	
	@Override
	public BaseSendMessage process(Map<String, Object> message , String fromUserName) throws Exception {
		
		String content = (String)message.get(Constants.CONTENT);
		BaseSendMessage responseMsg;
		
		CommandTypeEnum commandType = commandTypeParser.parse(content);
		CommandHandler commandHandler = commandHandlerFactory.fetchHandlerHandler(commandType);
		responseMsg = commandHandler.handle(content,fromUserName,message);
		
		logger.debug("responseMsg:["+responseMsg+"]");
		return responseMsg;
	}
	
	

	@Override
	public MessageTypeEnum fetchMessageType() {
		return MessageTypeEnum.text;
	}
}