package com.egghead.nicefood.biz;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.egghead.nicefood.Constants;
import com.egghead.nicefood.check.BizException;
import com.egghead.nicefood.check.FormatException;
import com.egghead.nicefood.check.MessageCommonChecker;
import com.egghead.nicefood.error.ErrorEnum;
import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.MessageTransformer;
import com.egghead.nicefood.message.MessageTypeEnum;
import com.egghead.nicefood.message.processor.MessageProcessor;
import com.egghead.nicefood.message.processor.MessageProcessorFactory;
import com.egghead.nicefood.message.text.TextMessage;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 15:00:12
 * 
 */
public class BizEngine {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	MessageProcessorFactory messageProcessorFactory;
	
	/**
	 * @param message
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws Exception 
	 */
	public String process(Map<String, Object> requestMsg) throws BizException,Exception{
		
		checkCommonMessageForamt(requestMsg);
		
		String fromUserName = (String)requestMsg.get(Constants.FROMUSERNAME);
		String msgType = (String)requestMsg.get(Constants.MSGTYPE);
		
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.valueOf(msgType);
		MessageProcessor messageProcessor = messageProcessorFactory.create(messageTypeEnum);
		if( messageProcessor == null ){
			//throw new SysException(ErrorEnum.ERROR_S_NO_FOUND_PROCESSOR);
			logger.warn("unsupport message type:" + msgType);
			return null;
		}
		logger.debug("messageProcessor:" + messageProcessor.getClass().getName());
		
		BaseSendMessage responseMsg = null;
		try{
			responseMsg = messageProcessor.process(requestMsg,fromUserName);
		}catch (Exception e) {
			logger.error("process message failed!requestMsg:["+requestMsg+"]",e);
			responseMsg = new TextMessage(fromUserName, Constants.OPEN_USERNAME, 0, ErrorEnum.ERROR_S_SERVER_ERROR.getMsg());
		}
		
		String responseMsgText = MessageTransformer.message2Xml(responseMsg);
		logger.debug("responseText:["+responseMsgText+"]");
		return responseMsgText;
	}

	/**
	 * 校验格式输入是否正确
	 * @param requestMsg
	 * @throws BizException 
	 */
	private void checkCommonMessageForamt(Map<String, Object> requestMsg) throws FormatException {
		MessageCommonChecker.check(requestMsg);
	}
}
