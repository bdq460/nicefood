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

	public StepDO setNumber(int number) {
		this.number = number;
		return this;
	}

	public String getAction() {
		return action;
	}

	public StepDO setAction(String action) {
		this.action = action;
		return this;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public StepDO setPicUrl(String picUrl) {
		this.picUrl = picUrl;
		return this;
	}
}
