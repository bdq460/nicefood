package com.egghead.nicefood.check;

import com.egghead.nicefood.error.ErrorEnum;
/**
 * 输入格式异常
 * 属于系统异常,不在界面提示
 * @author zhangjun.zyk 
 * @since 2013-7-24 下午06:44:43
 * 
 */
public class FormatException extends BaseException{

	/**
	 * @param errorEnum
	 */
	public FormatException(ErrorEnum errorEnum) {
		super(errorEnum);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 747229453872320747L;

}