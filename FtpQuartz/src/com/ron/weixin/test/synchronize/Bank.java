package com.ron.weixin.test.synchronize;

public class Bank {
	 public synchronized void getRMB(){  
	     for(int i=0; i<20; i++){  
	         try {  
	             Thread.sleep((long)(Math.random()*1000));  
	         } catch (InterruptedException e) {  
	             e.printStackTrace();  
	         }  
	         System.out.println(Thread.currentThread().getName() + ":" + i);  
	     }  
	 }  
	      
	 public synchronized void getDollar(){  
	     for(int i=0; i<20; i++){  
	         try {  
	             Thread.sleep((long)(Math.random()*1000));  
	         } catch (InterruptedException e) {  
	             e.printStackTrace();  
	         }  
	         System.out.println(Thread.currentThread().getName() + ":" + i);  
	     }  
	 }
}
