package edu.tongji.yuexiarenjing.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client1 {
	public static void main(String[] args) {
		
		Client1 client1  = new Client1();		
		
		File file = new File("test.txt");
		int count = (int) Math.ceil(file.length() / (double) (64*1024*1024));
		for (int i = 0; i < count; i++) {
			client1.sendSplitFile(file);
		}
	}
	
	public void sendSplitFile(File splitFile){
		Socket socket = null;
		OutputStream os = null;
		FileInputStream fis = null;
		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 9000);
			os = socket.getOutputStream();
			fis = new FileInputStream(splitFile);
			
			byte[]	b = new byte[1024];
			int len;
			while((len = fis.read(b)) != -1){
				os.write(b, 0, len);
				os.flush();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fis != null){
				try {
					fis.close();
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
		}
	}
}
