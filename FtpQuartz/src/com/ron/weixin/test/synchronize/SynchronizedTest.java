package com.ron.weixin.test.synchronize;

/** 
 * Synchronized Method Test 
 * @see =================================================================================================== 
 * @see 概述：Java中的每个对象都有一个锁(lock)或者叫做监视器(monitor) 
 * @see 说明：当synchronized关键字修饰一个方法时，则该方法为同步方法 
 * @see      当某个线程访问某个对象的synchronized方法时，则表示将该对象上锁 
 * @see      此时其它的任何线程，均无法访问该对象中的任何一个synchronized方法(但允许访问该对象中的非synchronized方法) 
 * @see      直到该线程所访问的synchronized方法执行完毕(或者抛出了异常)之后，该对象的锁才会被释放 
 * @see      此时其它的任何线程，才被允许访问该synchronized方法，或者是该对象中的其它synchronized方法 
 * @see =================================================================================================== 
 * @see 总结：如果一个对象有多个synchronized方法，某一时刻某个线程已经执行了该对象中的某一个synchronized方法 
 * @see      那么在该方法没有执行完毕之前，其它线程是无法访问该对象中的，包括该方法在内的，任何一个synchronized方法 
 * @see      重点在于判断Synchronized锁的是谁。如果该方法是静态的，则锁Class对象，否则锁的就是当前对象 
 * @see =================================================================================================== 
 * @see 补充：1)这只是针对多个线程操作同一个类的同一个对象的情况。www.linuxidc.com若多个线程操作同一个类的不同对象，则不存在这种情况 
 * @see      2)Java中的volatile变量也可以看作是一种"程度较轻的synchronized" 
 * @see        关于volatile的更多信息，请参考http://www.ibm.com/developerworks/cn/java/j-jtp06197.html 
 * @see 备注：实际项目中，用到的更多的还是JDK5.0开始推出的Java并发包，即java.util.concurrent包里面的工具类 
 * @see      java.util.concurrent可以非常细粒度的实现并发。比如线程访问到了一个已被锁的对象，它可以让这个线程等到10秒 
 * @see      10秒后如果该对象仍未被解锁，那么就可以返回给用户超时的提示等，而如果使用synchronized则是无法这么精确控制的 
 * @see =================================================================================================== 
 * @see 注意：1)当synchronized方法执行完或者发生异常时，会自动释放锁 
 * @see      2)被synchronized保护的数据应该是private的，否则也就没必要去通过方法来访问这个public的数据了 
 * @see =================================================================================================== 
 * @author  
 * @create  
 */ 

public class SynchronizedTest {
	  public static void main(String[] args) {  
	        Bank bank = new Bank();  
	          
	        Thread tt11 = new Thread(new ThreadRMB(bank));  
	        Thread tt12 = new Thread(new ThreadRMB(bank));  
	          
	        //new一个新的Bank对象。此时存在两个Bank对象，并且它们属于同一个类的不同的对象   
	        //如要验证多个线程操作同一个类的不同的对象的synchronized方法，只需取消注释该行代码即可   
	        bank = new Bank();   
	          
	        Thread tt21 = new Thread(new ThreadDollar(bank));  
	        Thread tt22 = new Thread(new ThreadDollar(bank));  
	          
	        tt11.start();  
	        tt12.start();  
	        tt21.start();  
	        tt22.start();  
	        System.out.println("mian thread over!");
	    }

}
