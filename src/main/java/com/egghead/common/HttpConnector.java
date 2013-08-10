package com.egghead.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author zhangjun.zyk 
 * @since 2013-8-10 下午08:21:40
 * 
 */
public class HttpConnector {

	public static String get(String url) throws IOException{
	
		URL urlObj = new URL(url);
		URLConnection urlConnection = urlObj.openConnection();
		
		HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setRequestProperty("connection", "Keep-Alive");
		httpURLConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpURLConnection.setRequestProperty("user-agent","Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/28.0.1500.16 Mobile/10B329 Safari/8536.25");
		httpURLConnection.setRequestProperty("accept-encoding","gzip,deflate,sdch");
		httpURLConnection.setRequestProperty("accept-language","zh-CN,zh;q=0.8");
		
		httpURLConnection.setInstanceFollowRedirects(false);
		httpURLConnection.connect();
		
		System.out.println(httpURLConnection.getHeaderFields());
		
		InputStream is = urlConnection.getInputStream();
		
		byte[] bytes = new byte[1024 * 1000];
		
		int len = is.read(bytes);
		byte[] contentBytes = new byte[len];
		System.arraycopy(bytes, 0, contentBytes, 0, len);
		is.close();
		httpURLConnection.disconnect();
		System.out.println(new String(contentBytes,"UTF8"));
		return null;
	}
	
	public static String get(String url , String content){
		return  null;
	}
	
	public static void main(String[] args) throws IOException {
		HttpConnector.get("http://weixin.qq.com/r/unVBWVPEdg4JrRc69yCo");
	}
}
