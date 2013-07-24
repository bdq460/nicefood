package com.egghead.nicefood.message.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.egghead.nicefood.message.MessageTypeEnum;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 07:18:25
 * 
 */
public class MessageProcessorFactory implements InitializingBean{

	Map<MessageTypeEnum, MessageProcessor> msgProcessorMap = new HashMap<MessageTypeEnum, MessageProcessor>();
	
	List<MessageProcessor> msgProcessorList = null;

	public void setMsgProcessorList(List<MessageProcessor> msgProcessorList) {
		this.msgProcessorList = msgProcessorList;
	}

	public MessageProcessor create(MessageTypeEnum messageType){
		return msgProcessorMap.get(messageType);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if( msgProcessorList != null ){
			for( MessageProcessor msgProcessor : msgProcessorList ){
				msgProcessorMap.put(msgProcessor.fetchMessageType(), msgProcessor);
			}
		}
	}
}
