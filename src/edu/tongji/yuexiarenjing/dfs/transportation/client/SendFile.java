package edu.tongji.yuexiarenjing.dfs.transportation.client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import edu.tongji.yuexiarenjing.dfs.entity.time;

public class SendFile implements Runnable {

	String IP;
	int PORT;
	String FILENAME;
	long POS;
	String SPLITNAME;
	String serverListStr;

	public SendFile(String ip, int port, String fileName, long pos, String splitName, String serverListStr) {
		// TODO Auto-generated constructor stub
		this.IP = ip;
		this.PORT = port;
		this.FILENAME = fileName;
		this.POS = pos;
		this.SPLITNAME = splitName;
		this.serverListStr = serverListStr;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		FileInputStream fis = null;
		Socket s = null;
		OutputStream os = null;
		DataOutputStream dos = null;

		try {
			File file = new File(FILENAME);
			fis = new FileInputStream(file);
			fis.skip(POS);
			s = new Socket(IP, PORT);
			os = s.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeUTF(SPLITNAME + "#" + serverListStr);
			byte[] b = new byte[1024];
			int len;
			int countLen = 0;
			while ((len = fis.read(b)) != -1 && countLen < 64 * 1024) {
				os.write(b, 0, len);
				countLen++;
				os.flush();
			}
			time.RunTime = System.currentTimeMillis();
			time.ThreadCount++;
			s.shutdownOutput();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
