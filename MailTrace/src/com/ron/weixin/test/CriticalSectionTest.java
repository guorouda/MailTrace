package com.ron.weixin.test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
class Testee {
    private Integer i = new Integer(1);
 
    public void f() {
        synchronized (i) {
            System.out.println(Thread.currentThread().getName() + " get i = " + i);
            System.out.println(Thread.currentThread().getName() + " change i = new Integer(2)");
            i = new Integer(2);
            try {
                System.out.println(Thread.currentThread().getName() + " Sleep 2 sec");
                TimeUnit.SECONDS.sleep(2); // 保证Thread#2获得新的i的锁
                System.out.println(Thread.currentThread().getName() + " Sleep finished");
                System.out.println(Thread.currentThread().getName() + " get i = " + i + " again");
            } catch (InterruptedException e) {
            }
        }
    }
 
    public void g() {
        // 先睡1秒，保证Thread#1完成对i的重新赋值工作
        try {
            TimeUnit.SECONDS.sleep(1);
            synchronized (i) {
                System.out.println(Thread.currentThread().getName() + " get i = " + i);
                System.out.println(Thread.currentThread().getName() + " Sleep 2 sec");
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " Sleep finished");
            }
        } catch (InterruptedException e) {
        }
    }
}
 
public class CriticalSectionTest {
 
    public static void main(String[] args) {
        final Testee testee = new Testee();
        Thread t1 = new Thread("Thread#1") {
            public void run() {
                testee.f();
            }
        };
        Thread t2 = new Thread("Thread#2") {
            public void run() {
                testee.g();
            }
        };
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(t1);
        exec.execute(t2);
        exec.shutdown();
    }
 
}