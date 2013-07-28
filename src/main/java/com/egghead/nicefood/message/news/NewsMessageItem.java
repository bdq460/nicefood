package com.egghead.nicefood.message.news;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


/**
 * @author zhangjun.zyk 
 * @since 2013-7-25 下午08:52:56
 * 
 */
//@JacksonXmlRootElement(localName="item")
public class NewsMessageItem {

	@JacksonXmlProperty(localName="Title")
	private String title;
	@JacksonXmlProperty(localName="Description")
	private String description;
	@JacksonXmlProperty(localName="PicUrl")
	private String picUrl;
	@JacksonXmlProperty(localName="Url")
	private String url;
	
	public NewsMessageItem(String title , String description , String picUrl , String url){
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
