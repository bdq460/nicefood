package com.egghead.nicefood.check;

import com.egghead.nicefood.error.ErrorEnum;

/**
 * 系统异常
 * 需提示用户
 * @author zhangjun.zyk 
 * @since 2013-7-24 下午06:44:43
 * 
 */
public class SysException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3692703949817480860L;

	/**
	 * @param errorEnum
	 */
	public SysException(ErrorEnum errorEnum) {
		super(errorEnum);
		// TODO Auto-generated constructor stub
	}

}