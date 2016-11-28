package edu.tongji.yuexiarenjing.dfs.transportation.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveBackUpFile implements Runnable {

	int PORT;
	int SERVERNO;

	public ReceiveBackUpFile(int port, int serverNo) {
		// TODO Auto-generated constructor stub
		this.PORT = port;
		this.SERVERNO = serverNo;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		Socket s = null;
		FileOutputStream fos = null;
		InputStream is = null;
		DataInputStream dis = null;
		try {
			File fold = new File("D:\\Server" + SERVERNO);
			if (!fold.exists()) {
				fold.mkdir();
			}

			ss = new ServerSocket(PORT);
			while (true) {
				s = ss.accept();
				File file = null;
				// 接收客户端传来的文件
				is = s.getInputStream();
				dis = new DataInputStream(is);
				String blockNum = dis.readUTF();
				file = new File("D:\\Server" + SERVERNO + "\\Block" + blockNum);
				fos = new FileOutputStream(file);

				byte[] b = new byte[1024];
				int len;
				while ((len = is.read(b)) != -1) {
					fos.write(b, 0, len);
					fos.flush();
				}
				logg log = new logg();
				log.updatelog(blockNum + "备份完毕 " + "\r\n", SERVERNO + "");
				s.shutdownOutput();

				if (fos != null) {
					try {
						fos.close();
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
		} catch (Exception e) {
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