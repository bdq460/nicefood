package com.egghead.nicefood.dao;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangjun.zyk 
 * @since 2013-8-20 下午03:16:25
 * 
 */
@Component
public class TagDAO extends BaseDAO{

    //@Select("select id from tag")
	//public int getId();

    Logger logger = Logger.getLogger(this.getClass());

    /**
     * 更新tag
     * @return
     */
    public void update_date(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("update_time", new Date());
        sqlSessionTemplate.update("update_tag", params);
    }
}
