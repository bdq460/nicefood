package com.egghead.nicefood.check;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.egghead.nicefood.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-19 21:45:26
 * 
 */
public class WeixinMessageChecker {
	
	static Logger logger = Logger.getLogger(WeixinMessageChecker.class);
	
	/**
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSource(String signature,String timestamp,String nonce){

        String[] sig_array = {nonce,timestamp,Constants.TOKEN};
        Arrays.sort(sig_array);
        String dig_info_str = "";
        for(String sig : sig_array){
            dig_info_str += sig;
        }
        String digest = DigestUtils.shaHex(dig_info_str);
		logger.debug("digest:"+digest);
		if( digest != null && digest.equals(signature) ){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String signature="9874dc3b0712470d966b83e75e797df2d8080728";
		String timestamp="1374669357";
		String nonce="1374598757";
		
		System.out.println(WeixinMessageChecker.checkSource(signature, timestamp, nonce));
	}
}
