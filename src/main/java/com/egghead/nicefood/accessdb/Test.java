package com.egghead.nicefood.accessdb;

import java.io.IOException;

import com.egghead.nicefood.dal.CourseDO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangjun.zyk 
 * @since 2013-8-3 下午04:23:25
 * 
 */
public class Test {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String mm = "{\"coid\":0}";
		ObjectMapper objmapper = new ObjectMapper();
		CourseDO courseDO = objmapper.readValue(mm, CourseDO.class);	
		System.out.println(courseDO);
	}
}
