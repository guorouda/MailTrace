package com.ron.weixin.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.ron.weixin.LoginWeixinWEB;
import com.ron.weixin.ftp.FtpDownloadClienter;
import com.ron.weixin.pereference.SystemGlobals;
import com.ron.weixin.utility.json.JSONException;

public class TestCase {
	private static Logger log = Logger.getLogger(TestCase.class);
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFtp() throws ClientProtocolException, URISyntaxException, IOException, JSONException{
//		LoginWeixinWEB lww = new LoginWeixinWEB();
//		lww.Login("suansuan08@126.com", "njau1918121");
//		lww.SendMessage("aaaaaabbbbbb", "2386949502");
		//lww.md5("aaaa1111");
	}
	
	@Test
	public void testZ() {
		System.out.println("**************");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");   
        System.out.println(df.format(new Date().getTime() - 24 * 60 * 60 * 1000));
	}
    
	@Test
	public void TestPrintHZ(){
	    int i = 1;
	    String remoteFileName = "03004_32_20131231.dat";
	    File file = new File("d:\\temp\\" + remoteFileName);
	    BufferedReader reader = null; 
	    
	    try { 
	    	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");   
	    	reader = new BufferedReader(isr);	    	 
	    	String tempString = null; 
	    	String keyword = null; 
	        while ((tempString = reader.readLine()) != null){ 
	        	String[] tempStrings = tempString.split("\t");
	        	if(i==3){
	        		keyword = tempStrings[10];
	        	}else{
	        		keyword = tempStrings[1];
	        	}
	        	log.info(tempString);
	        	if(SystemGlobals.searchKeywords(keyword)>0){
	        		log.info("find: " + tempString);
	      //  		return tempString;
	        	};
	        } 
	        reader.close(); 
	    } catch (IOException e) { 
	    	e.printStackTrace(); 
	    } finally { 
	    	if (reader != null){ 
		        try { 
		        	reader.close(); 
		        } catch (IOException e) { 
		        } 
	    	} 
	    } 
	}
	
	@Test
	public void testA() {
		List userlist=new ArrayList();
		  userlist.add(new User("dd","4"));
		  userlist.add(new User("aa","1"));
		  userlist.add(new User("ee","5"));
		  userlist.add(new User("bb","2"));  
		  userlist.add(new User("ff","5"));
		  userlist.add(new User("cc","3"));
		  userlist.add(new User("gg","6"));
		  
		  ComparatorUser comparator=new ComparatorUser();
		  Collections.sort(userlist, comparator);
		   
		  for (int i=0;i<userlist.size();i++){
		   User user_temp=(User)userlist.get(i);
		      System.out.println(user_temp.getAge()+","+user_temp.getName()); 
		  }
		  
	}
	
	
	@Test
	public void testB(){
		List userlist = new ArrayList();
		userlist.add("aaaaa");
		userlist.add("1aaaa");
		userlist.add("saaaaa");
		userlist.add("ffaaaaa");
		Collections.sort(userlist);
		
		for(int i=0; i < userlist.size(); i++){
			System.out.println(userlist.get(i));
		}
		
		userlist.add("01aaaa");
		Collections.sort(userlist);
		
		for(int i=0; i < userlist.size(); i++){
			System.out.println(userlist.get(i));
		}
		
		System.out.println(Collections.binarySearch(userlist, "1aaaa"));
		System.out.println(Collections.binarySearch(userlist, "aa"));
		
	}

}

class User {
	 String name;
	 String age;
	 
	 public User(String name,String age){
	  this.name=name;
	  this.age=age;
	 }
	 public String getAge() {
	  return age;
	 }
	 public void setAge(String age) {
	  this.age = age;
	 }
	 public String getName() {
	  return name;
	 }
	 public void setName(String name) {
	  this.name = name;
	 } 
	}

class ComparatorUser implements Comparator{

	 public int compare(Object arg0, Object arg1) {
	  User user0=(User)arg0;
	  User user1=(User)arg1;

	   //首先比较年龄，如果年龄相同，则比较名字

	  int flag=user0.getAge().compareTo(user1.getAge());
	  if(flag==0){
	   return user0.getName().compareTo(user1.getName());
	  }else{
	   return flag;
	  }  
	 }
	 
	}