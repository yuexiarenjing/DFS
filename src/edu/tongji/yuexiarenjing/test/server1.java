package edu.tongji.yuexiarenjing.test;

import java.io.*;
import java.net.*;
public class server1 {
	
	public static void main(String[] args) throws Exception {
	
		
	ServerSocket ss = null;
	Socket s = null;
	//InputStream is = null;
	//FileOutputStream fos = null;
	try {
		
		
		ss = new ServerSocket(9090);
		
		
		while(true){
		s = ss.accept();
		new ServerThread(s).start();
		//is = s.getInputStream();
		//File file = new File("E:\\store/block1/a");
		//fos = new FileOutputStream(file);
		//byte[] b = new byte[1024];
	//	int len;
		//while ((len = is.read(b)) != -1) {
		//	fos.write(b, 0, len);
		//}
		
		System.out.println("某一个开始了");
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	finally {
		/*if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		}*/
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




class ServerThread extends Thread{
	Socket sock;
	InputStream is = null;
	FileOutputStream fos = null;
	
	DataInputStream  dis = null;
	
	public ServerThread(Socket s)
	{
		sock =s;
		
	}
	
	public void run()
	
	
	
	{
		try{
		is = sock.getInputStream();
		dis = new DataInputStream (is);
		
		
		String path="E:\\store/"+dis.readUTF();
		
		System.out.println(path);
		File file = new File(path);
		fos = new FileOutputStream(file);
		byte[] b = new byte[1024];
		int len;
		while ((len = is.read(b)) != -1) {
			fos.write(b, 0, len);
		}
		
		System.out.println(path+" is success ");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sock != null) {
			try {
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		}
	
		
		
		
		
		
	}

}



