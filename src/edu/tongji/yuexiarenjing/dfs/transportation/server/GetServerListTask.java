package edu.tongji.yuexiarenjing.dfs.transportation.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class GetServerListTask implements Runnable {

	String[] serverList = new String[4]; // 存放服务器在线列表（IP）
	String IP;
	int PORT;

	public GetServerListTask(String ip, int port, String[] serverList) {
		// TODO Auto-generated constructor stub
		this.IP = ip;
		this.PORT = port;
		this.serverList = serverList;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;

		try {
			while (true) {
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
				Thread.currentThread().sleep(2000);
			}
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