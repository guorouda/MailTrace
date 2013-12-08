package com.ron.weixin.test;

import java.io.IOException;
import java.io.File;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TestProperties {
	private static Logger log = Logger.getLogger(TestProperties.class);

	public static void main(String[] args) {
		Properties p = new Properties();
		p.setProperty("id","dean");
		p.setProperty("password","123456");
		String id = p.getProperty("id");
		log.debug(id);
		String key = p.getProperty("key");
		log.debug(key);
		try{
			PrintStream fW = new PrintStream(new File("e:\\test1.xml"));
			p.storeToXML(fW,"test");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}