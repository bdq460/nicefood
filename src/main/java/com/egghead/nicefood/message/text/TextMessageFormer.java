package com.egghead.nicefood.message.text;

import java.util.Map;

import com.egghead.nicefood.Constants;
import com.egghead.nicefood.message.CommonMessageFormer;
import com.egghead.nicefood.message.MessageTypeEnum;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 08:20:08
 * 
 */
public class TextMessageFormer {

	/**
	 * @param fromUser
	 * @param toUser
	 * @return
	 */
	public static Map<String, Object> formMessage(String fromUser , String toUser , String content ) {
		Map<String, Object> msgMap = CommonMessageFormer.formMessage(fromUser, toUser);
		msgMap.put(Constants.MSGTYPE, MessageTypeEnum.text.toString());
		msgMap.put(Constants.CONTENT, content);
		return msgMap;
	}
}
