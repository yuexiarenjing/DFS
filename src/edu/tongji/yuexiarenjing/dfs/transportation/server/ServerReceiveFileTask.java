package edu.tongji.yuexiarenjing.dfs.transportation.server;

public class ServerReceiveFileTask implements Runnable {

	int PORT;
	int SERVERNO;
	String MonitorIP;
	int MonitorPORT;

	public ServerReceiveFileTask(int port, int serverNo, String monitorIP, int monitorPORT) {
		// TODO Auto-generated constructor stub
		this.PORT = port;
		this.SERVERNO = serverNo;
		this.MonitorIP = monitorIP;
		this.MonitorPORT = monitorPORT;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// 循环监听文件接收端口，如果有文件则接受
		ReceiveFile receiveFile = new ReceiveFile(PORT, SERVERNO, MonitorIP, MonitorPORT);
		Thread t = new Thread(receiveFile);
		t.start();

		ReceiveBackUpFile receiveBackUpFile = new ReceiveBackUpFile((PORT + 10), SERVERNO);
		Thread t1 = new Thread(receiveBackUpFile);
		t1.start();
	}
}
