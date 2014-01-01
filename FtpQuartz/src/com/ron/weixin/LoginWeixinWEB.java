package com.ron.weixin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ron.weixin.utility.json.JSONException;
import com.ron.weixin.utility.json.JSONObject;
import com.ron.weixin.utility.json.jsonManager;

public class LoginWeixinWEB {
	private static Logger log = Logger.getLogger(LoginWeixinWEB.class);
	
	CloseableHttpClient httpclient = null;
	
	String token = null;
	
	
	public LoginWeixinWEB() {
		// TODO Auto-generated constructor stub
		CookieStore cookieStore = new BasicCookieStore();
		RequestConfig requestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				.build();
		
		this.httpclient = HttpClients
				.custom()
				.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0")				
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore)
				.build();
	}


	/**
	 * @param name
	 * @param pass
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException 
	 */
	public boolean Login(String name, String pass) throws URISyntaxException, ClientProtocolException, IOException, JSONException{
		boolean result = false;

		UriComponents components = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("mp.weixin.qq.com")
                .path("/cgi-bin/login")
                .queryParam("username", name)
                .queryParam("pwd", md5(pass).toUpperCase())
				.queryParam("imgcode", "")
				.queryParam("f", "json")
                .build();
		URI uri = components.toUri();
				
		//URI uri = new  URIBuilder()
		//		.setScheme("https")
		//		.setHost("mp.weixin.qq.com")
		//		.setPath("/cgi-bin/login")
		//		.setParameter("login", "zh_CN")
		//		.setParameter("username", name)
		//		.setParameter("pwd", md5(pass).toUpperCase())
		//		.setParameter("imgcode", "")
		//		.setParameter("f", "json")
		//		.build();
		
		HttpPost httppost = new HttpPost(uri);
		
		//httppost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		//httppost.setHeader("Accept-Encoding", "gzip, deflate");
		//httppost.setHeader("Accept-Language", "en-US,en;q=0.5");
		//httppost.setHeader("Connection", "keep-alive");
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded"); 
        httppost.setHeader("Referer", "https://mp.weixin.qq.com");
        httppost.setHeader("Host", "mp.weixin.qq.com");
		
		CloseableHttpResponse response = httpclient.execute(httppost);
		
		HttpEntity entity = response.getEntity();
		
		jsonManager jm = new jsonManager();
		String str = new String(EntityUtils.toString(entity).getBytes(), "utf-8");
		JSONObject obj = new JSONObject(str);
		this.token = jm.doParse(obj).sz;
	
		return result;
	}
	
	
	public boolean SendMessage(String message, String fakeid) throws URISyntaxException, ClientProtocolException, IOException{
		
		URI uri = new  URIBuilder()
				.setScheme("https")
				.setHost("mp.weixin.qq.com")
				.setPath("/cgi-bin/singlesend")
				.setParameter("type", "1")
				.setParameter("content", message)
				.setParameter("tofakeid", fakeid)
				.setParameter("imgcode", "")
				.setParameter("token", this.token)
				.setParameter("lang", "zh_CN")
				.setParameter("random", "")
				.setParameter("f", "json")
				.setParameter("ajax", "1")
				.setParameter("t", "ajax-response")
				.build();
		
		HttpPost httppost = new HttpPost(uri);
		
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded"); 
        httppost.setHeader("Referer", "https://mp.weixin.qq.com/cgi-bin/singlesendpage?t=message/send&action=index&tofakeid=" + fakeid + "&token=" + this.token + "&lang=zh_CN");
        httppost.setHeader("Host", "mp.weixin.qq.com");
        
		CloseableHttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		
		return false;
	}
	
	public String md5(String plainText) {
		// 返回字符串
		String md5Str = null;
		try {
			// 操作字符串
			StringBuffer buf = new StringBuffer();

			MessageDigest md = MessageDigest.getInstance("MD5");

			// 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
			md.update(plainText.getBytes());
			//计算出摘要,完成哈希计算。
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				//将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
				buf.append(Integer.toHexString(i));
			}
			//32位的加密
			md5Str = buf.toString();
			//16位的加密
			//md5Str = buf.toString().md5Strstring(8,24);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
		
	}

}
