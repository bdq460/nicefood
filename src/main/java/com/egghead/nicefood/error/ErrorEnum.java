package com.egghead.nicefood.error;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-24 下午06:50:25
 * 
 */
public enum ErrorEnum {

	//格式错误以F开头
	ERROR_F_FROM_USER_EMPTY("来源用户信息为空"),
	ERROR_F_INVALID_MSG_TYPE("非法消息类型"),
	ERROR_F_INVALID_EVENT_TYPE("非法事件消息类型"),
	//业务错误以B开头
	
	//系统错误以S开头
	ERROR_S_NO_FOUND_PROCESSOR("未找到匹配的数据处理器"),
	ERROR_S_SERVER_ERROR("后台服务错误");
	
	private String msg;
	
	private ErrorEnum( String msg ){
		this.msg = msg;
	};
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
