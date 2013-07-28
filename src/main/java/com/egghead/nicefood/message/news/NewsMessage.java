package com.egghead.nicefood.message.news;

import java.util.ArrayList;
import java.util.List;

import com.egghead.nicefood.message.BaseSendMessage;
import com.egghead.nicefood.message.MessageTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-25 下午09:23:21
 * 
 */
public class NewsMessage extends BaseSendMessage{

	@JacksonXmlElementWrapper(localName="Articles")
	@JacksonXmlProperty(localName="item")
	List<NewsMessageItem> articles;
	
	@JacksonXmlProperty(localName="ArticleCount")
	int articleCount;
	
	/**
	 * @param toUserName
	 * @param fromUserName
	 * @param msgType
	 * @param funcFlag
	 */
	public NewsMessage(String toUserName, String fromUserName,
			int funcFlag) {
		super(toUserName, fromUserName, MessageTypeEnum.news, funcFlag);
		articles = new ArrayList<NewsMessageItem>();
		articleCount = 0;
	}
	
	public void addItem(NewsMessageItem item){
		articles.add(item);
		articleCount++;
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		//Map<String, Object> map = new HashMap<String, Object>();
		
		NewsMessage newsMessage = new NewsMessage("xiaoming", "xiaowang", 0);
		
		NewsMessageItem newsMessageItem = new NewsMessageItem("too", "doo", "poo","uoo");
		NewsMessageItem newsMessageItems = new NewsMessageItem("too", "doo", "poo","uoo");
		
		newsMessage.addItem(newsMessageItem);
		newsMessage.addItem(newsMessageItems);
		//map.put("a", value)
		ObjectMapper xmlMapper = new XmlMapper();
		String xml = xmlMapper.writeValueAsString(newsMessage);
		System.out.println(xml);
	}
}
