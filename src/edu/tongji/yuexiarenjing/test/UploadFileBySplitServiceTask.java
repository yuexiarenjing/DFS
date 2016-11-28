package edu.tongji.yuexiarenjing.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UploadFileBySplitServiceTask implements Runnable {
	
	int port;
	String fileName;
	
	public UploadFileBySplitServiceTask(int port, String fileName) {
		// TODO Auto-generated constructor stub
		this.port = port;
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		ServerSocket ss = null;
		Socket socket = null;
		InputStream is = null;
		FileOutputStream fos = null;
		
		try {
			ss = new ServerSocket(port);
			socket = ss.accept();
			is = socket.getInputStream();
			
			File file = new File(fileName);
			fos = new FileOutputStream(file);
			
			byte[] b = new byte[1024];
			int len;
			while((len = is.read()) != -1){
				fos.write(b, 0, len);
				fos.flush();
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fos != null){
				try {
					fos.close();
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
			if(socket != null){
				try {
					socket.close();
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
}