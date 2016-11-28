package edu.tongji.yuexiarenjing.dfs.end;

import edu.tongji.yuexiarenjing.dfs.heartbeats.HeartBeatsServerTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerReceiveFileTask;
import edu.tongji.yuexiarenjing.dfs.transportation.server.ServerSendFileTask;

public class Server1 {

	// 根据实际环境设置为Monitor的IP地址
	public static String MonitorIP = "127.0.0.1";

	public static void main(String[] args) {
		Server1 server1 = new Server1();
		server1.intiHeartBeat();
		server1.initReceiveFile();
		server1.initSendFile();
	}

	public void intiHeartBeat() {
		HeartBeatsServerTask task = new HeartBeatsServerTask(MonitorIP, 10001);
		Thread t = new Thread(task);
		t.start();
	}

	public void initReceiveFile() {
		ServerReceiveFileTask task = new ServerReceiveFileTask(9011, 1, MonitorIP, 10006);
		Thread t = new Thread(task);
		t.start();
	}

	public void initSendFile() {
		ServerSendFileTask task = new ServerSendFileTask(9091, "1");
		Thread t = new Thread(task);
		t.start();
	}
}
