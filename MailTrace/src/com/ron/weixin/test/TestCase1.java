package com.ron.weixin.test;

import java.io.IOException;
import java.net.URISyntaxException;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.ron.ems.Ems83;

public class TestCase1 {
	private static Logger log = Logger.getLogger(TestCase1.class);
	
	@Test
	public void testY() throws ClientProtocolException, IOException, URISyntaxException{
		log.info("start!");
		Ems83 e = new Ems83();
		JSONObject jo = e.GetDetail("1084117162006");
	}
	
	

}