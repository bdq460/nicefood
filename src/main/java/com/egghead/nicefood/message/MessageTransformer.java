package com.egghead.nicefood.message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author zhangjun.zyk 
 * @since 2013-7-23 02:37:18
 * 
 */
public class MessageTransformer {

	@SuppressWarnings("unchecked")
	public static Map<String,Object> xml2Map(String xml) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper xmlMapper = new XmlMapper();
		return xmlMapper.readValue(xml, Map.class);
	}
	
	public static String map2Xml(Map<String, Object> map) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper xmlMapper = new XmlMapper();
		String xml = xmlMapper.writeValueAsString(map);
		String startTag = "<HashMap xmlns=\"\">";
		String endTag = "</HashMap>";
		xml = xml.substring(startTag.length());
		xml = xml.substring(0,xml.length() - endTag.length());
		xml = "<xml>"+xml+"</xml>";
		return xml;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> mm = new HashMap<String, Object>();
		mm.put("a", "b");
		mm.put("c", "d");
		Map<String, Object> www = new HashMap<String, Object>();
		www.put("t", "�Ҿ�����hoho");
		mm.put("w", www);
		String responseMsgText = MessageTransformer.map2Xml(mm);
		String startTag = "<HashMap xmlns=\"\">";
		String endTag = "</HashMap>";
		responseMsgText = responseMsgText.substring(startTag.length());
		responseMsgText = responseMsgText.substring(0,responseMsgText.length() - endTag.length());
		responseMsgText = "<xml>"+responseMsgText+"</xml>";
		System.out.println(responseMsgText);
	}
}
