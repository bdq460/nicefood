package com.egghead.nicefood.check;

import com.egghead.nicefood.error.ErrorEnum;
/**
 * 业务输入异常
 * 属于业务异常,需在回复用户提示
 * @author zhangjun.zyk 
 * @since 2013-7-24 下午06:44:43
 * 
 */
public class BaseException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6127781611188445317L;

	String code;
	
	public BaseException( ErrorEnum errorEnum ){
		super(errorEnum.getMsg());
		this.code = errorEnum.toString();
	}

	public String getCode() {
		return code;
	}
}