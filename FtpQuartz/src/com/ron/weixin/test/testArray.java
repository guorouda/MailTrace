package com.ron.weixin.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class testArray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ArrayList list = new ArrayList();
		    list.add("928");
		    list.add("689");
		    list.add("16861");
		    list.add("D242");
		    list.add("317");
		    list.add("A105");
		    // 字符串排序
//		    Collections.sort(list);
//		    System.out.println(list.toString()); // [105, 168.61, 242, 317, 68.9, 92.8]
		    
		    System.out.println(list.size());
		    String[] sdf = new String[list.size()];
		    list.toArray(sdf);
		    Arrays.sort(sdf);
		    int t = Arrays.binarySearch(sdf, "689");
		    System.out.println(t);
/*		    Collections.sort(list, new Comparator() {
		      @Override
		      public int compare(Object o1, Object o2) {
		        return new Double((String) o1).compareTo(new Double((String) o2));
		      }
		    });
		    System.out.println(list.toString()); // [68.9, 92.8, 105, 168.61, 242, 317]
*/		  }

	

}
