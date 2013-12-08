package com.ron.weixin.test.synchronize;

public class ThreadDollar implements Runnable {
	private Bank bank;  
	public ThreadDollar(Bank bank){  
	    this.bank = bank;  
	} 

	@Override
	public void run() {
		// TODO Auto-generated method stub
        bank.getDollar();  
	}

}
