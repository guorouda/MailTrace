package com.ron.weixin.test.student;

import java.util.Random;

import org.apache.log4j.Logger;

public class ThreadDemo1 implements Runnable {
	private static Logger log = Logger.getLogger(ThreadDemo1.class);
	
	private int count=0;
	
	private Object studentLock = new Object();   
	private Object countLock = new Object();   

	private Student student = new Student();   
	public static void main(String[] agrs) {   
	   ThreadDemo1 td = new ThreadDemo1();   
	   Thread t1 = new Thread(td,"a");   
	   Thread t2 = new Thread(td,"b");   
	   t1.start();   
	   t2.start();   
	
	} 
	   
	@Override
	public void run() {
		// TODO Auto-generated method stub
		accessStudent();

	}
	
	
	
	public void accessStudent() {   
       String currentThreadName = Thread.currentThread().getName();   
       log.info(currentThreadName+" is running!");   
      // System.out.println("first   read age is:"+this.student.getAge());   
       
       synchronized (countLock) {
	       try {   
	    	   this.count++;   
	    	   Thread.sleep(5000);   
	       }catch(InterruptedException ex) {   
	           ex.printStackTrace();   
	       }   
       log.info("thread "+currentThreadName+" read count:"+this.count);   
	   }
       
       synchronized (studentLock) {
	       Random random = new Random();   
	       int age = random.nextInt(100);   
	       log.info("thread "+currentThreadName +" set age to:"+age);   
	         
	       this.student.setAge(age);   
	       log.info("thread "+currentThreadName+" first   read age is:"+this.student.getAge());   
	       try {   
	    	   Thread.sleep(5000);   
	       }catch(InterruptedException ex) {   
	           ex.printStackTrace();   
	       }   
       }
       
       log.info("thread "+currentThreadName +" second read age is:"+this.student.getAge());   
           
	} 
       
	 

}
