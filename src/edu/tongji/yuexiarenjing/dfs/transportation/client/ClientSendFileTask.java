package edu.tongji.yuexiarenjing.dfs.transportation.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import edu.tongji.yuexiarenjing.dfs.entity.time;

public class ClientSendFileTask implements Runnable {

	String[] serverList = new String[4]; // 存放服务器在线列表（IP）
	String FILEPATH;
	int BlockNum;
	String serverListStr;

	public ClientSendFileTask(String filePath, String[] serverList, int blockNum, String serverListStr) {
		// TODO Auto-generated constructor stub
		this.FILEPATH = filePath;
		this.serverList = serverList;
		this.BlockNum = blockNum;
		this.serverListStr = serverListStr;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s = null;
		File file = new File(FILEPATH);
		FileInputStream fis = null;
		OutputStream os = null;
		int count = (int) Math.ceil(file.length() / (double) (64 * 1024 * 1024));

		try {
			System.out.println("Uploading...");
			fis = new FileInputStream(file);
			int pos;
			long start = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				pos = i % serverList.length;
				if (serverList[pos] == null)
					pos++;
				SendFile sendFile = new SendFile(serverList[pos % serverList.length], 9011 + (pos % serverList.length),
						FILEPATH, i * 64 * 1024 * 1024, (BlockNum + i + 1) + "", serverListStr);
				Thread t = new Thread(sendFile);
				t.start();
			}
			 while(time.ThreadCount < count )
			 {
				Thread.currentThread().sleep(1000);
			 }
			 System.out.println("Finish, cost："+(time.RunTime-start));
			 time.ThreadCount =0;
			 time.RunTime =0;
		} catch (Exception e) {
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
			if (fis != null) {
				try {
					fis.close();
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
