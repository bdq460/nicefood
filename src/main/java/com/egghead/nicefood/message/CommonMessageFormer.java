package com.egghead.nicefood.message;

import java.util.HashMap;
import java.util.Map;

import com.egghead.nicefood.Constants;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 20:20:25
 * 
 */
public class CommonMessageFormer {

	/**
	 * @param fromUser
	 * @param toUser
	 * @return
	 */
	public static Map<String, Object> formMessage(String fromUser , String toUser ) {
		Map<String, Object> msgMap = new HashMap<String, Object>();
		
		msgMap.put(Constants.FROMUSERNAME, fromUser);
		msgMap.put(Constants.TOUSERNAME, toUser);
		msgMap.put(Constants.CREATETIME, System.currentTimeMillis());
		msgMap.put(Constants.FUNCFLAG, "0");
		return msgMap;
	}
}
