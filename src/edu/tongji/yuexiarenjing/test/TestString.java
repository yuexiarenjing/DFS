package edu.tongji.yuexiarenjing.test;

import org.junit.Test;

public class TestString {
	
	@Test
	public void searchKeyString(){
		String str = "IP1=/127.0.0.1 IP2=/127.0.0.1 IP3=/127.0.0.1 IP4=/127.0.0.1";
		String[] slist = str.split(" ");

		for(String s : slist){
			String[] sp = s.split("/");
			if(sp[0].contains("1")){
				System.out.println(1 + sp[1]);
			}else if(sp[0].contains("2")){
				System.out.println(2 + sp[1]);
			}else if (sp[0].contains("3")){
				System.out.println(3 + sp[1]);
			}else if (sp[0].contains("4")){
				System.out.println(4 + sp[1]);
			}
		}
	}
}
