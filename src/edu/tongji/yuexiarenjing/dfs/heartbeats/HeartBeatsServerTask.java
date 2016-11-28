package edu.tongji.yuexiarenjing.dfs.heartbeats;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class HeartBeatsServerTask implements Runnable {
	String IP;
	int PORT;

	public HeartBeatsServerTask(String ip, int port) {
		// TODO Auto-generated constructor stub
		this.IP = ip;
		this.PORT = port;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			Socket s = null;
			OutputStream os = null;
			try {
				s = new Socket(InetAddress.getByName(IP), PORT);
				os = s.getOutputStream();
				os.write(1);
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
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
			}
		}
	}
}