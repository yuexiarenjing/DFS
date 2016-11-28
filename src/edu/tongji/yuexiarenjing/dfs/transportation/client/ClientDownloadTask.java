package edu.tongji.yuexiarenjing.dfs.transportation.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.tongji.yuexiarenjing.dfs.entity.time;

public class ClientDownloadTask implements Runnable {

	String IP;
	int PORT;
	String FILEPATH;

	public ClientDownloadTask(String ip, int port, String filePath) {
		// TODO Auto-generated constructor stub
		this.IP = ip;
		this.PORT = port;
		this.FILEPATH = filePath;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		InputStream is = null;
		DataInputStream dis = null;

		File fold = new File("D:/download");
		if (!fold.exists()) {
			fold.mkdir();
		}

		try {
			System.out.println("Downloading...");
			s = new Socket(IP, PORT);
			os = s.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeUTF(FILEPATH);

			is = s.getInputStream();
			dis = new DataInputStream(is);
			String downloadInfo = dis.readUTF();

			String[] infoList = downloadInfo.split("\r\n");
			String downloadIP;
			String blockNum;
			String position;
			String pOrt;
			int Tcount = 0;
			long start = System.currentTimeMillis();
			for (String str : infoList) {
				Tcount++;
				String[] splitList = str.split("/");
				blockNum = splitList[0];
				String[] splitList2 = splitList[1].split(",");
				downloadIP = splitList2[0];
				position = splitList2[1];
				pOrt = "909" + splitList2[2];
				new downloadThread(blockNum, downloadIP, "D:/download/" + FILEPATH, Integer.parseInt(position),
						Integer.parseInt(pOrt)).start();
				;

			}
			while (time.ThreadCount < Tcount) {
				Thread.currentThread().sleep(1000);
			}
			System.out.println("Finish, cost：" + (time.RunTime - start));
			time.RunTime = 0;
			time.ThreadCount = 0;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class downloadThread extends Thread {

		String ip;
		File file = null;
		RandomAccessFile raf = null;
		OutputStream os = null;

		DataOutputStream dos = null;
		InputStream is = null;
		String number;
		Socket socket;
		int pos;
		int port;

		public downloadThread(String num, String IP, String fp, int posi, int por) {
			number = num; // 这就是 向目标服务器 获取 那个文件名
			ip = IP;
			file = new File(fp);
			pos = posi;
			port = por;

		}

		public void run() {
			try {
				socket = new Socket(ip, port);
				os = socket.getOutputStream();
				dos = new DataOutputStream(os);
				dos.writeUTF(number);

				is = socket.getInputStream();
				raf = new RandomAccessFile(file, "rw");

				raf.seek((pos - 1) * 67108864);
				byte[] b = new byte[1024];
				int len;
				while ((len = is.read(b)) != -1) {
					raf.write(b, 0, len);
				}
				time.RunTime = System.currentTimeMillis();
				time.ThreadCount++;

				// System.out.println(pos +"接受完毕");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (raf != null) {
					try {
						raf.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}

	}

}
