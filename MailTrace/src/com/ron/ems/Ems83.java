package com.ron.ems;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.ron.weixin.bean.StepBean;
import com.ron.weixin.pereference.SystemGlobals;
import com.sun.istack.internal.logging.Logger;

public class Ems83 {
	private static Logger log = Logger.getLogger(Ems83.class);
	
	public JSONObject GetDetail(String mailnum) throws ClientProtocolException, IOException, URISyntaxException{
		URI uri = new  URIBuilder()
				.setScheme("http")
				.setHost("192.168.202.13")
				.setPath("/ems/newsystem/thesecond/ttq/ttqMailquery.jsp")
				.build();
		
		HttpGet httpget = new HttpGet(uri);
		
		httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.setHeader("Accept-Encoding", "gzip, deflate");
		httpget.setHeader("Accept-Language", "en-US,en;q=0.5");
		httpget.setHeader("Connection", "keep-alive");
		httpget.setHeader("Host", "192.168.202.13");
		
		CloseableHttpResponse response = SystemGlobals.getEmsHttpclient().execute(httpget);
		
		JSONObject json = new JSONObject();
		
		try {
			HttpEntity entity = response.getEntity();
			Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"myEmsbarCode\" value=\"(.*)\"/>");
			Matcher matcher = pattern.matcher(EntityUtils.toString(entity));
			if(matcher.find()){
//				System.out.println(matcher.group(1));
			}else{
				System.out.println("error!");
				return null;
			}
			
			URI uri2 = new  URIBuilder()
					.setScheme("http")
					.setHost("192.168.202.13")
					.setPath("/ems/qcgzBaseQueryAction.do")
					.setParameter("select", "1")
					.setParameter("mailNum", mailnum)
					.setParameter("reqCode", "regoUrl")
					.setParameter("myEmsbarCode", matcher.group(1))
					.setParameter("Image17.x", "17")
					.setParameter("Image17.y", "8")
					.build();

			HttpGet httpget2 = new HttpGet(uri2);
			
			httpget2.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpget2.setHeader("Accept-Encoding", "gzip, deflate");
			httpget2.setHeader("Accept-Language", "en-US,en;q=0.5");
			httpget2.setHeader("Connection", "keep-alive");
			httpget2.setHeader("Host", "192.168.202.13");	
			httpget2.setHeader("Referer", "http://192.168.202.13/ems/newsystem/thesecond/ttq/ttqMailquery.jsp");	
			httpget2.setHeader("Content-Type", "application/x-www-form-urlencoded");	
			
			CloseableHttpResponse response2 = SystemGlobals.getEmsHttpclient().execute(httpget2);
			HttpEntity entity2 = response2.getEntity();
			
			pattern = Pattern.compile("<iframe src=\"../../../([^\"]*)\"");
			matcher = pattern.matcher(EntityUtils.toString(entity2));
			String[] p2 = null;
			if(matcher.find()){
//				System.out.println(matcher.group(1));
				String[] p = matcher.group(1).split("\\&");
				p2 = p[2].split("=");
			}else{
				return null;
			}
			
			URI uri3 = new  URIBuilder()
				.setScheme("http")
				.setHost("192.168.202.13")
				.setPath("/ems/qcgzBaseQueryAction.do")
				.setParameter("reqCode", "browseBASE")
				.setParameter("select", "1")
				.setParameter("md5Mail", p2[1])
				.setParameter("mailNum", mailnum)
				.setParameter("clctDate", "null")
				.setParameter("clctTime", "null")
				.setParameter("clctSendOrg", "null")
				.setParameter("fromLikeList", "null")
				.setParameter("searchBase", "2")
				.build();

			HttpGet httpget3 = new HttpGet(uri3);
			
			httpget3.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpget3.setHeader("Accept-Encoding", "gzip, deflate");
			httpget3.setHeader("Accept-Language", "en-US,en;q=0.5");
			httpget3.setHeader("Connection", "keep-alive");
			httpget3.setHeader("Host", "192.168.202.13");	
			httpget3.setHeader("Referer", "http://192.168.202.13/ems/qcgzBaseQueryAction.do");	
			httpget3.setHeader("Content-Type", "application/x-www-form-urlencoded");	
			
			CloseableHttpResponse response3 = SystemGlobals.getEmsHttpclient().execute(httpget3);
			HttpEntity entity3 = response3.getEntity();
			
			pattern = Pattern.compile("<table[^>]*+>([^<]*+(?:(?!</?+table)<[^<]*+)*+)</table>");
			matcher = pattern.matcher(EntityUtils.toString(entity3));
			int i = 0;
			List<StepBean> list = new ArrayList<StepBean>();
			while(matcher.find()){
				if(i == 2){
					pattern = Pattern.compile("<tr[^>]*+>([^<]*+(?:(?!</?+tr)<[^<]*+)*+)</tr>");
					matcher = pattern.matcher(matcher.group(1));
					while(matcher.find()){
						Pattern pattern1 = Pattern.compile("<td[^>]*+>([^<]*+(?:(?!</?+td)<[^<]*+)*+)</td>");
						Matcher matcher1 = pattern1.matcher(matcher.group(1));
						int j = 0;
						
						String tempStep = null;
						String time = "";
						String station = "";
						String action = "";
						String comment = "";
						
						while(matcher1.find()){
							if(j <= 3){
								if(j == 2){
									if(Pattern.matches("收寄", matcher1.group(1)) || Pattern.matches("揽收", matcher1.group(1))){
								//		System.out.println(matcher1.group(1));
										tempStep = matcher1.group(1);
										action = tempStep;
										break;
									}
									if(Pattern.matches(".*封发.*", matcher1.group(1))){
						//				System.out.println(matcher1.group(1) +"－＞离开处理中心");
										tempStep = "离开处理中心";
										action = tempStep;
										j++;
										continue;
									}
									if(Pattern.matches(".*开拆.*", matcher1.group(1))){
						//				System.out.println(matcher1.group(1) +"->进入处理中心");
										tempStep = "进入处理中心";
										action = tempStep;
										j++;
										continue;
									}
								}
						//		System.out.println(matcher1.group(1));
								tempStep = matcher1.group(1);
							}
							
							switch(j){
								case 0:
									time = tempStep;
									break;
								case 1:
									station = tempStep;
									break;
								case 2:
									action = tempStep;
									break;
								case 3:
									comment = tempStep;
									break;
							}
								
							j++;
						}
			//			System.out.println(time + ":" + station + ":" + action + ":" + comment);
						if(!station.isEmpty()){
							StepBean sb = new StepBean(time, station, action, comment);
							list.add(sb);
						}
					}
				}
				i++;
			}
			JSONArray ja = JSONArray.fromObject(list);
			
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("results", 110);
            m.put("rows", ja);
            json = JSONObject.fromObject(m);
			log.info("over!");
			return json;
		} finally {
//			SystemGlobals.getEmsHttpclient().close();
		}

	}
	
}
