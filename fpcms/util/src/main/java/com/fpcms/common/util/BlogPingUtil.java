package com.fpcms.common.util;

import java.util.HashMap;
import java.util.Map;

public class BlogPingUtil {
	
	public static boolean baiduPing(String blogName,String blogHomeUrl,String newBlogUrl,String blogRssUrl){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("blogName", blogName);
		params.put("blogHomeUrl", blogHomeUrl);
		params.put("newBlogUrl", newBlogUrl);
		params.put("blogRssUrl", blogRssUrl);
		
		String pingXml = FreemarkerUtil.readFreemarkerClassPathResource("/ping/baidu_ping_template.xml",params);
		String result = NetUtil.httpPost("http://ping.baidu.com/ping/RPC2", pingXml,"text/xml");
		if(result.contains("<int>0</int>")) {
			return true;
		}else if(result.contains("<int>1</int>")) {
			return false;
		}else {
			throw new RuntimeException("unknow response xml string:"+result+" please see:http://www.baidu.com/search/blogsearch_help.html#n7");
		}
	}

}
