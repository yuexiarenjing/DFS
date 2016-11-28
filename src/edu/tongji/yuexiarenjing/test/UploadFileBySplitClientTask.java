package edu.tongji.yuexiarenjing.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UploadFileBySplitClientTask implements Runnable {

	int splitSize;
	String splitFileName;
	File src;
	int startPosition;
	String inetAddressName;
	int port;

	public UploadFileBySplitClientTask(int splitSize, String splitFileName, File src, int startPosition,
			String inetAddressName, int port) {
		// TODO Auto-generated constructor stub
		this.splitSize = splitSize;
		this.splitFileName = splitFileName;
		this.src = src;
		this.startPosition = startPosition;
		this.inetAddressName = inetAddressName;
		this.port = port;
	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
//		File file = null;
		Socket socket = null;
		FileInputStream fis = null;
		OutputStream os = null;

		try {
//			file = new RandomAccessFile(src, "r");
			fis = new FileInputStream(src);
			socket = new Socket(InetAddress.getByName(inetAddressName), port);
			os = socket.getOutputStream();

			byte[] b = new byte[1024];
			int len;
//			file.seek(startPosition);
			fis.skip(startPosition);
			int count = 0;// 记录写的次数，控制写64M
			while ((len = fis.read(b)) != -1 && count < (splitSize * 1024 / 7 * 64)) {
				count += len;
				System.out.println(count);
				os.write(b, 0, len);
				os.flush();
			}
			socket.shutdownOutput();

		} catch (IOException e) {
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
			if (socket != null) {
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