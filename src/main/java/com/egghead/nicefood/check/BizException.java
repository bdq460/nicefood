package com.egghead.nicefood.check;

import com.egghead.nicefood.error.ErrorEnum;

/**
 * 业务输入异常
 * 属于业务异常,需在回复用户提示
 * @author zhangjun.zyk 
 * @since 2013-7-24 下午06:44:43
 * 
 */
public class BizException extends BaseException{

	/**
	 * @param errorEnum
	 */
	public BizException(ErrorEnum errorEnum) {
		super(errorEnum);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1382510098837577784L;

}