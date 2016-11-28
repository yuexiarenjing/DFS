package edu.tongji.yuexiarenjing.dfs.end;

import edu.tongji.yuexiarenjing.dfs.heartbeats.HeartBeatsServerTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerReceiveFileTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerSendFileTask;

public class Server2 {
	
	// 根据实际环境设置为Monitor的IP地址
	public static String MonitorIP = "127.0.0.1";

	public static void main(String[] args) {
		Server2 server2 = new Server2();
		server2.intiHeartBeat();
		server2.initReceiveFile();
		server2.initSendFile();
	}

	public void intiHeartBeat() {
		HeartBeatsServerTask task = new HeartBeatsServerTask(MonitorIP, 10002);
		Thread t = new Thread(task);
		t.start();
	}
	
	public void initReceiveFile(){
		ServerReceiveFileTask task = new ServerReceiveFileTask(9012, 2, MonitorIP, 10006);
		Thread t = new Thread(task);
		t.start();
	}
	
	public void initSendFile(){
		ServerSendFileTask task = new ServerSendFileTask(9092,"2");
		Thread t = new Thread(task);
		t.start();
	}
}
