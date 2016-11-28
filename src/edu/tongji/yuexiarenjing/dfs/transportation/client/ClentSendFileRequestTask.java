package edu.tongji.yuexiarenjing.dfs.transportation.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClentSendFileRequestTask implements Runnable {

	String[] serverList = new String[4]; // 存放服务器在线列表（IP）
	String IP;
	int PORT;
	String FILEPATH;
	String FILENAME;

	public ClentSendFileRequestTask(String ip, int port, String filePath) {
		// TODO Auto-generated constructor stub
		this.IP = ip;
		this.PORT = port;
		FILEPATH = filePath;
		String[] filename = filePath.split("/");
		this.FILENAME = filename[filename.length-1];
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		try {
			s = new Socket(IP, PORT);
			os = s.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeUTF("RequestServerList");
			is = s.getInputStream();
			dis = new DataInputStream(is);

			String str = dis.readUTF();

			String[] slist = str.split(" ");
			// 获取服务器IP列表
			for (String st : slist) {
				String[] sp = st.split("/");
				if (sp[0].contains("1")) {
					serverList[0] = sp[1];
				} else if (sp[0].contains("2")) {
					serverList[1] = sp[1];
				} else if (sp[0].contains("3")) {
					serverList[2] = sp[1];
				} else if (sp[0].contains("4")) {
					serverList[3] = sp[1];
				}
			}
			
			File file = new File(FILEPATH);
			int count = (int) Math.ceil(file.length() / (double) (64 * 1024 * 1024));

			dos.writeUTF("RequestFimage");
			String max = dis.readUTF();
			int maxBlockNum = Integer.parseInt(max);
			int serverNo;
			String fileArrange = "";

			for (int i = 0; i < count; i++) {
				int pos = i % serverList.length;
				if (serverList[pos] == null)
					pos++;
				int finalPos = pos % serverList.length;
				serverNo = finalPos + 1;
				fileArrange += serverNo + " " + (i+1+maxBlockNum)+ "@";//服务器名 Block偏移量
				
				pos = (i+1) % serverList.length;
				if (serverList[pos] == null)
					pos++;
				finalPos = pos % serverList.length;
				serverNo = finalPos + 1;
				fileArrange += serverNo + " " + (i+1+maxBlockNum)+ "@";//服务器名 Block偏移量（备份）
			}
			fileArrange = FILENAME + "#" + fileArrange;
			dos.writeUTF(fileArrange);
			
			s.shutdownOutput();

			ClientSendFileTask task = new ClientSendFileTask(FILEPATH, serverList, maxBlockNum, str);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (dis != null) {
				try {
					dis.close();
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
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
