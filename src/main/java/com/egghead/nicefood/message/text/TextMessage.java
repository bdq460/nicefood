package com.egghead.nicefood.message.text;

import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.MessageTypeEnum;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-25 下午09:43:30
 * 
 */
public class TextMessage extends BaseSendMessage{

	@JacksonXmlProperty(localName="Content")
	private String content;
	
	/**
	 * @param toUserName
	 * @param fromUserName
	 * @param msgType
	 * @param funcFlag
	 */
	public TextMessage(String toUserName, String fromUserName,
			int funcFlag,String content) {
		super(toUserName, fromUserName, MessageTypeEnum.text, funcFlag);	
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
