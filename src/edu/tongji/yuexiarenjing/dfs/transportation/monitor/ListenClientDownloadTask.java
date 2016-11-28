package edu.tongji.yuexiarenjing.dfs.transportation.monitor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import edu.tongji.yuexiarenjing.dfs.entity.fimage;
import edu.tongji.yuexiarenjing.dfs.entity.simage;

public class ListenClientDownloadTask implements Runnable {

	String[][] serverList = new String[4][3]; // 存放服务器在线列表（IP，端口，时间戳）
	LinkedList<fimage> fimageList = new LinkedList<>();// 存放file
														// image（文件名->Blocks）
	LinkedList<simage> simageList = new LinkedList<>();// 存放server
														// image（服务器名->Blocks）
	int PORT;
	String fileName;

	public ListenClientDownloadTask(String[][] serverList, LinkedList<fimage> fimageList, LinkedList<simage> simageList,
			int port) {
		// TODO Auto-generated constructor stub
		this.serverList = serverList;
		this.fimageList = fimageList;
		this.simageList = simageList;
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
				s = ss.accept();
				is = s.getInputStream();
				dis = new DataInputStream(is);

				fileName = dis.readUTF();
				String downloadInfo = GetDownloadInfo();
				os = s.getOutputStream();
				dos = new DataOutputStream(os);
				dos.writeUTF(downloadInfo);
				
				s.shutdownOutput();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(dos != null){
				try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(dis != null){
				try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(s != null){
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ss != null){
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public String GetDownloadInfo() {
		int pos = 1;
		String downLoadInfo = "";
		for (fimage image : fimageList) {
			if (image.getFileName().equals(fileName)) {
				String flag = "";
				for (String str : image.getblockList()) {
					if (flag.equals(str)) {
						// 已经获取到了block的序列
						int isFined = 0;
						for (simage i : simageList) {
							if (isFined == 0) {
								for (String s : i.getBlockList()) {
									if (s.equals(str)) {
										downLoadInfo += str + serverList[Integer.parseInt(i.getServerName()) - 1][0]
												+ "," + pos + "," + i.getServerName() + "\r\n";
										pos++;
										isFined = 1;
										break;

									}
								}
							}
						}
					}
					flag = str;
				} 
			}
		}
		return downLoadInfo;
	}
}
