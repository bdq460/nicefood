package com.egghead.nicefood.message;

import com.egghead.nicefood.BaseDO;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-25 下午09:15:55
 * 
 */
@JacksonXmlRootElement(localName="xml")
public class BaseSendMessage extends BaseDO{

	@JacksonXmlProperty(localName="ToUserName")
	private String toUserName;
	@JacksonXmlProperty(localName="FromUserName")
	private String fromUserName;
	@JacksonXmlProperty(localName="CreateTime")
	private long createTime;
	@JacksonXmlProperty(localName="MsgType")
	private MessageTypeEnum msgType;
	@JacksonXmlProperty(localName="FuncFlag")
	private int funcFlag;
	
	public BaseSendMessage(String toUserName , String fromUserName , MessageTypeEnum msgType , int funcFlag){
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.msgType = msgType;
		this.funcFlag = funcFlag;
		this.createTime = System.currentTimeMillis();
	}

	public String getToUserName() {
		return toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public MessageTypeEnum getMsgType() {
		return msgType;
	}

	public int getFuncFlag() {
		return funcFlag;
	}
}
