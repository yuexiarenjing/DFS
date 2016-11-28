package edu.tongji.yuexiarenjing.dfs.transportation.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class GetFileList implements Runnable {

	String IP;
	int PORT;

	public GetFileList(String IP, int PORT) {
		// TODO Auto-generated constructor stub
		this.IP = IP;
		this.PORT = PORT;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Socket s = null;
		InputStream is = null;
		DataInputStream dis = null;

		try {
			s = new Socket(IP, PORT);
			is = s.getInputStream();
			dis = new DataInputStream(is);
			String fileList = dis.readUTF();
			System.out.println(fileList);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(dis != null){
				try {
					dis.close();
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
		}
	}
}
