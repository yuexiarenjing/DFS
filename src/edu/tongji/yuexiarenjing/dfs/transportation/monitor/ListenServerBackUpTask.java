package edu.tongji.yuexiarenjing.dfs.transportation.monitor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenServerBackUpTask implements Runnable {

	String[][] serverList = new String[4][3]; // ��ŷ����������б�IP���˿ڣ�ʱ�����
	int PORT;

	public ListenServerBackUpTask(String[][] serverList, int port) {
		// TODO Auto-generated constructor stub
		this.serverList = serverList;
		this.PORT = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			ss = new ServerSocket(PORT);
			while (true) {
				s = ss.accept();// �ȴ��������˷�������
				is = s.getInputStream();// �������˷�����������������б�
				dis = new DataInputStream(is);
				os = s.getOutputStream();
				dos = new DataOutputStream(os);
				if (dis.readUTF().equals("RequestServerList")) {
					String str = "";
					for (int i = 0; i < 4; i++) {
						// �жϷ����������б����͸���������
						if (serverList[i][2] != null
								&& (System.currentTimeMillis() - Long.parseLong(serverList[i][2])) < 3000) {
							str += "IP" + (i + 1) + "=" + serverList[i][0] + " ";
						}
					}
					dos.writeUTF(str);
				}
				s.shutdownOutput();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dis != null) {
				try {
					dis.close();
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
