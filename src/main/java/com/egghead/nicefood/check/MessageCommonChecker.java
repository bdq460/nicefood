package com.egghead.nicefood.check;

import java.util.Map;
import org.springframework.util.StringUtils;
import com.egghead.nicefood.Constants;
import com.egghead.nicefood.error.ErrorEnum;
import com.egghead.nicefood.message.MessageTypeEnum;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-24 下午06:41:28
 * 
 */
public class MessageCommonChecker {

	public static void check(Map<String, Object> requestMsg) throws FormatException{
		String fromUserName = (String)requestMsg.get(Constants.FROMUSERNAME);
		String msgType = (String)requestMsg.get(Constants.MSGTYPE);
		//fromUserName不能为null
		if( StringUtils.isEmpty(fromUserName) == true ){
			throw new FormatException(ErrorEnum.ERROR_F_FROM_USER_EMPTY);
		}
		//msgType必须有效
		try{
			MessageTypeEnum.valueOf(msgType);
		}catch (Exception e) {
			throw new FormatException(ErrorEnum.ERROR_F_INVALID_MSG_TYPE);
		}
	}
}
