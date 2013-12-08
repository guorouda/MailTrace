package com.ron.weixin.test;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class quicksort {
	 int count=0;
	
	 int partition(ArrayList arr,int low,int high)
    {
    	int pivotkey;//中轴值，在这个中轴值左边的数都比它小，右边的都比它大
        pivotkey=(int)arr.get(low);
        while(low<high)
        {
        	//从高位开始寻找，将数组中比pivotkey小的移到低位
        	while(low<high&&((int)arr.get(high))>=pivotkey)
        	{
        		high--;
        		count++;
        	}
        	arr.set(low,arr.get(high));
           //从低位开始寻找，将数组中比pivotkey大的移到高位
        	while(low<high&&((int)arr.get(low))<=pivotkey)
        	{
        		low++;
        		count++;
        	}
        	arr.set(high,arr.get(low));
        }
        arr.set(low,pivotkey);
        return low;
    }
	 //排序算法
     void Quicksort(ArrayList arr,int low,int high)
    {
    	 if(low<high)
    	 {
    	   int pivotkey=partition(arr,low,high);
    	   Quicksort(arr,low,pivotkey-1);
    	   Quicksort(arr,pivotkey+1,high);
    	 }
    	
    }
    //创建初始数组
    void init(ArrayList arr,int num)
    {   
    	
    	Random r=new Random(47);
    	for(int i=0;i<num;i++)
    	{
    		arr.add(r.nextInt(100));
    		
    	}
    }
	//输出 结果
    void printoutput(ArrayList arr)
    {
       Iterator it=arr.iterator();
       while(it.hasNext())
    	   System.out.println((int)it.next());
    }
    //构造函数
    public quicksort()
    {   
    	Scanner input=new Scanner(System.in);
        int num=input.nextInt();
        ArrayList arr=new ArrayList(num);
    	init(arr,num);
        Quicksort(arr,0,num-1);
        printoutput(arr);
        System.out.println("value of count is "+count);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
              new quicksort();       
       }

}
