package edu.tongji.yuexiarenjing.dfs.end;

import edu.tongji.yuexiarenjing.dfs.heartbeats.HeartBeatsServerTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerReceiveFileTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerSendFileTask;

public class Server4 {
	
	// 根据实际环境设置为Monitor的IP地址
	public static String MonitorIP = "127.0.0.1";

	public static void main(String[] args) {
		Server4 server4 = new Server4();
		server4.intiHeartBeat();
		server4.initReceiveFile();
		server4.initSendFile();
	}

	public void intiHeartBeat() {
		HeartBeatsServerTask task = new HeartBeatsServerTask(MonitorIP, 10004);
		Thread t = new Thread(task);
		t.start();
	}
	
	public void initReceiveFile(){
		ServerReceiveFileTask task = new ServerReceiveFileTask(9014, 4, MonitorIP, 10006);
		Thread t = new Thread(task);
		t.start();
	}
	
	public void initSendFile(){
		ServerSendFileTask task = new ServerSendFileTask(9094,"4");
		Thread t = new Thread(task);
		t.start();
	}
}
