package com.egghead.nicefood.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author zhangjun.zyk 
 * @since 2013-8-20 下午03:16:25
 * 
 */
@Component
public interface TagDAO {

	@Select("select id from tag")
	public int getId();
}
