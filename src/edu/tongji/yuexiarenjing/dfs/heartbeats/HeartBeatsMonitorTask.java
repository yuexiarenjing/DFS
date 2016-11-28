package edu.tongji.yuexiarenjing.dfs.heartbeats;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HeartBeatsMonitorTask implements Runnable {

	int PORT;
	String[][] serverList;
	int SERVER;

	public HeartBeatsMonitorTask(int port, String[][] serverList, int server) {
		// TODO Auto-generated constructor stub
		this.PORT = port;
		this.serverList = serverList;
		this.SERVER = server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			ServerSocket ss = null;
			Socket s = null;
			InputStream is = null;
			try {
				ss = new ServerSocket(PORT);
				s = ss.accept();
				is = s.getInputStream();
				if (is.read() == 1) {
					if (serverList[SERVER - 1][0] == null) {
						serverList[SERVER - 1][0] = s.getInetAddress().toString();
						serverList[SERVER - 1][1] =  "running";
						serverList[SERVER - 1][2] = System.currentTimeMillis() + "";
					} else {
						serverList[SERVER - 1][1] =  "running";
						serverList[SERVER - 1][2] = System.currentTimeMillis() + "";
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (s != null) {
					try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (ss != null) {
					try {
						ss.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}