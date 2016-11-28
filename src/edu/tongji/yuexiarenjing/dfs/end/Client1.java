package edu.tongji.yuexiarenjing.dfs.end;

import edu.tongji.yuexiarenjing.dfs.transportation.client.CommandLineTask;

public class Client1 {
	
	//根据实际环境设置为Monitor的IP地址
	public static String MonitorIP = "127.0.0.1";
	
	public static void main(String[] args) {
		Client1 client1 = new Client1();
		client1.initCommandLine();
	}
	
	public void initCommandLine(){
		CommandLineTask task = new CommandLineTask(MonitorIP);
		Thread t = new Thread(task);
		t.start();
	}
}
