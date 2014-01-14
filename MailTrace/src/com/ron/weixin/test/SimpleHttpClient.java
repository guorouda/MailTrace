package com.ron.weixin.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.sun.istack.internal.logging.Logger;

public class SimpleHttpClient {
	private static Logger log = Logger.getLogger(SimpleHttpClient.class);
	

	
	private void ConsumingEntityContent() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://192.168.202.13/ems");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			log.info(response.getProtocolVersion().toString());
			log.info(response.getStatusLine().getStatusCode()+"");
			log.info(response.getStatusLine().getReasonPhrase().toString());
			log.info(response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
			System.out.println(EntityUtils.toString(entity));
			
			if (entity != null) {
				long len = entity.getContentLength();
				System.out.println("len: " + len);
				if (len != -1) {
					System.out.println("....................");
					System.out.println(EntityUtils.toString(entity));
				} else {
				// Stream content out
				}
			}
		} finally {
			response.close();
		}

	}
	
	public void Basic() throws ClientProtocolException, IOException, URISyntaxException{
		CookieStore cookieStore = new BasicCookieStore();
		RequestConfig requestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
				.build();
		
		CloseableHttpClient httpclient = HttpClients
				.custom()
				.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0")				
				.setDefaultRequestConfig(requestConfig)
				.setDefaultCookieStore(cookieStore)
				.build();
				
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
		
		CloseableHttpResponse response = httpclient.execute(httpget);
				
		
//		try {
			HttpEntity entity = response.getEntity();
			Pattern pattern = Pattern.compile("<input type=\"hidden\" name=\"myEmsbarCode\" value=\"(.*)\"/>");
			Matcher matcher = pattern.matcher(EntityUtils.toString(entity));
			if(matcher.find()){
				System.out.println(matcher.group(1));
			}
			
			URI uri2 = new  URIBuilder()
					.setScheme("http")
					.setHost("192.168.202.13")
					.setPath("/ems/qcgzBaseQueryAction.do")
					.setParameter("select", "1")
					.setParameter("mailNum", "5066980369701")
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
			
			CloseableHttpResponse response2 = httpclient.execute(httpget2);
			HttpEntity entity2 = response2.getEntity();
			
			pattern = Pattern.compile("<iframe src=\"../../../([^\"]*)\"");
			matcher = pattern.matcher(EntityUtils.toString(entity2));
			String[] p2 = null;
			String[] p3 = null;
			if(matcher.find()){
				System.out.println(matcher.group(1));
				String[] p = matcher.group(1).split("\\&");
				p2 = p[2].split("=");
				p3 = p[3].split("=");
			}
			
			URI uri3 = new  URIBuilder()
				.setScheme("http")
				.setHost("192.168.202.13")
				.setPath("/ems/qcgzBaseQueryAction.do")
				.setParameter("reqCode", "browseBASE")
				.setParameter("select", "1")
				.setParameter("md5Mail", p2[1])
				.setParameter("mailNum", p3[1])
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
			
			CloseableHttpResponse response3 = httpclient.execute(httpget3);
			HttpEntity entity3 = response3.getEntity();
			
			pattern = Pattern.compile("<table[^>]*+>([^<]*+(?:(?!</?+table)<[^<]*+)*+)</table>");
			matcher = pattern.matcher(EntityUtils.toString(entity3));
			int i = 0;
			while(matcher.find()){
				if(i == 2){
					pattern = Pattern.compile("<tr[^>]*+>([^<]*+(?:(?!</?+tr)<[^<]*+)*+)</tr>");
					matcher = pattern.matcher(matcher.group(1));
					while(matcher.find()){
						Pattern pattern1 = Pattern.compile("<td[^>]*+>([^<]*+(?:(?!</?+td)<[^<]*+)*+)</td>");
						Matcher matcher1 = pattern1.matcher(matcher.group(1));
						int j = 0;
						while(matcher1.find()){
							if(j <= 3){
								if(j == 2){
									if(Pattern.matches("收寄", matcher1.group(1)) || Pattern.matches("揽收", matcher1.group(1))){
										System.out.println(matcher1.group(1));
										break;
									}
									if(Pattern.matches(".*封发.*", matcher1.group(1))){
										System.out.println(matcher1.group(1) +"－＞离开处理中心");
										continue;
									}
									if(Pattern.matches(".*开拆.*", matcher1.group(1))){
										System.out.println(matcher1.group(1) +"->进入处理中心");
										continue;
									}
								}
								System.out.println(matcher1.group(1));
							}
							j++;
						}
						System.out.println("##################################");
					}
					
				}
				i++;
			}
			System.out.println("Over");
			
//		} finally {
//			httpclient.close();
//		}

	}
	
	
	
	private void CloseableHttpClient() throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://www.google.com/");

            System.out.println("executing request " + httpget.getURI());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse( final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");

        } finally {
            httpclient.close();
        }
	}
	
	
	
    public final static void main(String[] args) throws Exception {
    	SimpleHttpClient s = new SimpleHttpClient();
    	s.Basic();
    }
}
