package com.ron.weixin.test.student;

import java.util.Random;

import org.apache.log4j.Logger;

public class ThreadDemo_ThreadLocal implements Runnable {
	private static Logger log = Logger.getLogger(ThreadDemo_ThreadLocal.class);
	
	private final static   ThreadLocal<Student> studentLocal = new ThreadLocal<Student>();
	
	public static void main(String[] agrs) {   
	   ThreadDemo_ThreadLocal td = new ThreadDemo_ThreadLocal();   
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
       
	   Random random = new Random();   
	   int age = random.nextInt(100);   
	   log.info("thread "+currentThreadName +" set age to:"+age);   
	     
	   Student  student = getStudent();
	   student.setAge(age);
	   log.info("thread "+currentThreadName+" first   read age is:"+student.getAge());   
	   try {   
	   	   Thread.sleep(5000);   
	   }catch(InterruptedException ex) {   
	       ex.printStackTrace();   
	   }   
       
       log.info("thread "+currentThreadName +" second read age is:"+student.getAge());   
           
	} 
       
	protected Student getStudent(){
		Student student = (Student)studentLocal.get();
		if(student == null){
			student = new Student();
			studentLocal.set(student);
		}
		return student;
	}
	
	protected void setStudent(Student student){
		studentLocal.set(student);
	}
	 

}
