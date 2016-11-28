package edu.tongji.yuexiarenjing.dfs.transportation.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSendFileTask implements Runnable {

	int PORT;
	String SERVERNUM;

	public ServerSendFileTask(int PORT, String servernum) {
		// TODO Auto-generated constructor stub
		this.PORT = PORT;
		SERVERNUM = servernum;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		Socket s = null;

		try {
			ss = new ServerSocket(PORT);
			while (true) {
				s = ss.accept();
				new outputServerThread(s, SERVERNUM).start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
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

class outputServerThread extends Thread {
	Socket sock;
	OutputStream os = null;
	FileInputStream fis = null;
	InputStream is = null;
	DataInputStream dis = null;
	String SERVERNUM;

	public outputServerThread(Socket s, String num) {
		sock = s;
		SERVERNUM = num;
	}

	public void run()
	{
		try {
			is = sock.getInputStream();
			dis = new DataInputStream(is);
			os = sock.getOutputStream();

			String path = "D:/Server" + SERVERNUM + "/" + "Block" + dis.readUTF(); // 先读要给你的块文件名字
			System.out.println("开始传输 " + path);
			File file = new File(path);
			fis = new FileInputStream(file); // 将文件读到自己身边
			// fos = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int len;
			while ((len = fis.read(b)) != -1) {
				os.write(b, 0, len);
			}
			System.out.println(path + "传输完毕 ");
			logg log = new logg();
			log.downloadlog(path + " 服务器端传送完毕 " + "\r\n", SERVERNUM);
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
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (sock != null) {
				try {
					sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
