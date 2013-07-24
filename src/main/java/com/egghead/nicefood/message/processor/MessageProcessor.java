package com.egghead.nicefood.message.processor;

import java.util.Map;

import com.egghead.nicefood.message.MessageTypeEnum;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 07:18:49
 * 
 */
public interface MessageProcessor {
	public MessageTypeEnum fetchMessageType();
	public Map<String,Object> process(Map<String, Object> message , String fromUserName) throws Exception;
}
