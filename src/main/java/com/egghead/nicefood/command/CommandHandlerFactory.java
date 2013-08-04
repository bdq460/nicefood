package com.egghead.nicefood.command;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午08:06:57
 * 
 */
@Component
public class CommandHandlerFactory {

	@Resource
	private CourseCommandHandler courseCommandHandler;
	
	public CommandHandler fetchHandlerHandler( CommandTypeEnum commandTyp ){
		return courseCommandHandler;
	}
}
