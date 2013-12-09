package com.ron.weixin.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class mySimpleDateFormat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        Date d=new Date();   
        SimpleDateFormat dfHm=new SimpleDateFormat("mm");   
        System.out.println(dfHm.format(d));

	}

}
