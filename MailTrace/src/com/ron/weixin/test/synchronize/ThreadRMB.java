package com.ron.weixin.test.synchronize;

public class ThreadRMB implements Runnable {
	private Bank bank;  
    public ThreadRMB(Bank bank){  
        this.bank = bank;  
    } 

	@Override
	public void run() {
		// TODO Auto-generated method stub
        bank.getRMB(); 
	}

}
