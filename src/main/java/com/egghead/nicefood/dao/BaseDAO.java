package com.egghead.nicefood.dao;

import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-29 下午06:29:40
 * 
 */
public class BaseDAO {

	@Resource
	SqlSessionTemplate sqlSessionTemplate;
}
