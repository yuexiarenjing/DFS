package edu.tongji.yuexiarenjing.dfs.transportation.monitor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import edu.tongji.yuexiarenjing.dfs.entity.fimage;

public class ListenClientGetFileListTask implements Runnable {

	LinkedList<fimage> fimageList;
	int PORT;

	public ListenClientGetFileListTask(int PORT, LinkedList<fimage> fimageList) {
		// TODO Auto-generated constructor stub
		this.PORT = PORT;
		this.fimageList = fimageList;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		ServerSocket ss = null;
		Socket s = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		try {
			ss = new ServerSocket(PORT);
			while (true) {
				s = ss.accept();
				os = s.getOutputStream();
				dos = new DataOutputStream(os);

				// 构造文件列表字符串
				String fileList = "";
				for (fimage image : fimageList) {
					fileList += image.getFileName() + "\r\n";
				}
				dos.writeUTF(fileList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dos != null){
				try {
					dos.close();
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

}
