package com.egghead.nicefood.command;

import java.util.Map;

import com.egghead.nicefood.check.BaseException;
import com.egghead.nicefood.message.BaseSendMessage;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午08:01:30
 * 
 */
public interface CommandHandler {
	
	/**
	 * @param content
	 * @param fromUserName
	 * @param message
	 */
	public BaseSendMessage handle(String command, String fromUserName,
			Map<String, Object> message) throws BaseException;
}
