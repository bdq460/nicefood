package com.egghead.nicefood.message;

import java.util.Map;

import com.egghead.nicefood.message.text.TextMessageFormer;

/**
 * 
 * @author zhangjun.zyk 
 * @since 2013-7-23 07:34:36
 * 
 */
public class MessageStore {

	public static final Map<String, Object> SERVER_ERROR_MSG = TextMessageFormer.formMessage(null, null, "�Բ����������쳣���Ը��������������?Ǹ��");

}
