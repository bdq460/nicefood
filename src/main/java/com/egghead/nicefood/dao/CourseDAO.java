package com.egghead.nicefood.dao;

import java.sql.SQLException;
import java.util.List;
import com.egghead.nicefood.dal.CourseDO;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-28 下午07:34:54
 * 
 */
public class CourseDAO {

	public static CourseDO get(int coid) throws SQLException{
		CourseDO courseDO = null;
		return courseDO;
	}
	
	/**
	 * 仅查出CourseDO部分数据
	 * 1.id
	 * 2.name
	 * 3.pic
	 * 4.desc
	 * 
	 * @param name
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public static List<CourseDO> getMiniCourseByName(String name,int limit) throws SQLException{
		List<CourseDO> courses = null;
		return courses;
	}
	
	/**
	 * 插入数据
	 * @param courseDO
	 * @return
	 */
	public static int insert(CourseDO courseDO){
		return -1;
	}
}
