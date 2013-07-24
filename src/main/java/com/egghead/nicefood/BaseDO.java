package com.egghead.nicefood;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-24 下午07:24:38
 * 
 */
public class BaseDO {

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
