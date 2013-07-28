package com.egghead.nicefood.command;

import org.springframework.stereotype.Component;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午08:10:47
 * 
 */
@Component
public class CommandTypeParser {

	public CommandTypeEnum parse(String content){
		return CommandTypeEnum.course;
	}
}
