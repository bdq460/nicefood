package com.egghead.nicefood.check;

import org.apache.commons.codec.digest.DigestUtils;

import com.egghead.nicefood.Constants;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-19 21:45:26
 * 
 */
public class WeixinMessageChecker {
	
	/**
	 * @param signatrue
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSource(String signature,String timestamp,String nonce){
		
		String digest = DigestUtils.shaHex(nonce+timestamp+Constants.TOKEN);
		if( digest != null && digest.equals(signature) ){
			return true;
		}
		return false;
	}
}
