package edu.tongji.yuexiarenjing.dfs.end;

import edu.tongji.yuexiarenjing.dfs.heartbeats.HeartBeatsServerTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerReceiveFileTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerSendFileTask;

public class Server3 {
	
	// 根据实际环境设置为Monitor的IP地址
	public static String MonitorIP = "127.0.0.1";

	public static void main(String[] args) {
		Server3 server3 = new Server3();
		server3.intiHeartBeat();
		server3.initReceiveFile();
		server3.initSendFile();
	}

	public void intiHeartBeat() {
		HeartBeatsServerTask task = new HeartBeatsServerTask(MonitorIP, 10003);
		Thread t = new Thread(task);
		t.start();
	}
	
	public void initReceiveFile(){
		ServerReceiveFileTask task = new ServerReceiveFileTask(9013, 3, MonitorIP, 10006);
		Thread t = new Thread(task);
		t.start();
	}
	
	public void initSendFile(){
		ServerSendFileTask task = new ServerSendFileTask(9093,"3");
		Thread t = new Thread(task);
		t.start();
	}
}
