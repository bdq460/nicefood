package com.egghead.nicefood.dal;

import com.egghead.nicefood.BaseDO;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午07:28:13
 * 
 */
public class StepDO extends BaseDO{

	/**
	 * 第幾步
	 */
	private int number;
	
	private String action;
	
	private String picUrl;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}
