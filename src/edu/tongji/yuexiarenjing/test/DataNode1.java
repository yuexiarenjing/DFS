package edu.tongji.yuexiarenjing.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataNode1 {

	final static int port = 9000;
	public static void main(String[] args) {

		DataNode1 dataNode1 = new DataNode1();
		dataNode1.start();
	}

	public void start() {

		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			while (true) {
				Socket socket = ss.accept(); // 阻塞监听

				Task task = new Task(socket); // 监听到新事件，建立通信
				Thread thread = new Thread(task);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

class Task implements Runnable {

	private Socket socket;
	static int count = 1;
	public Task(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		InputStream is = null;
		File file  = null;
		FileOutputStream fos = null;
		try {
			is = socket.getInputStream();
			file = new File("received" + count++);
			fos = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) != -1) {
				fos.write(b, 0, len);
				fos.flush();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
