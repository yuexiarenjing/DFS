package edu.tongji.yuexiarenjing.dfs.transportation.monitor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import edu.tongji.yuexiarenjing.dfs.entity.fimage;
import edu.tongji.yuexiarenjing.dfs.entity.simage;

public class ListenClientUploadTask implements Runnable {

	String[][] serverList = new String[4][3]; // 存放服务器在线列表（IP，端口，时间戳）
	LinkedList<fimage> fimageList = new LinkedList<>();// 存放file image（文件名->Blocks）
	LinkedList<simage> simageList = new LinkedList<>();// 存放server image（服务器名->Blocks）
	int PORT;

	public ListenClientUploadTask(String[][] serverList, LinkedList<fimage> fimageList, LinkedList<simage> simageList, int port) {
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
		FileWriter fw1 = null;
		FileWriter fw2 = null;
		try {
			ss = new ServerSocket(PORT);
			while (true) {
				s = ss.accept();// 等待客户端发送请求
				is = s.getInputStream();// 客户端发送请求，要发送文件，请求服务器列表
				dis = new DataInputStream(is);
				os = s.getOutputStream();
				dos = new DataOutputStream(os);
				if (dis.readUTF().equals("RequestServerList")) {
					String str = "";
					for (int i = 0; i < 4; i++) {
						// 判断服务器在线列表。发送给客户端
						if (serverList[i][2] != null
								&& (System.currentTimeMillis() - Long.parseLong(serverList[i][2])) < 3000) {
							str += "IP" + (i+1) + "=" + serverList[i][0] + " ";
						}
					}
					dos.writeUTF(str);
				}
				if (dis.readUTF().equals("RequestFimage")) {
					if(fimageList.isEmpty()){
						dos.writeUTF("0");
					}else{
						int MAX = -1;
						for(fimage image : fimageList){
							LinkedList<String> list = image.getblockList();
							for(String str : list){
								if(Integer.parseInt(str) > MAX){
									MAX = Integer.parseInt(str);
								}
							}
						}
						dos.writeUTF(MAX+"");
					}
					String fileArrange = dis.readUTF();
					System.out.println(fileArrange);
					//处理文件存放列表，更新fsimage
					String fileName;
					String[] sfileArrange = fileArrange.split("#");
					fileName = sfileArrange[0];
					fimage fi = new fimage();
					fi.setFileName(fileName);
					fimageList.add(fi);

					String[] serverBlockList = sfileArrange[1].split("@");
					for(String serverBlock : serverBlockList){
						String[] sblock  = serverBlock.split(" ");
						if(simageList.isEmpty()){
							simage i1 = new simage();
							simage i2 = new simage();
							simage i3 = new simage();
							simage i4 = new simage();
							i1.setServerName(1+"");
							i2.setServerName(2+"");
							i3.setServerName(3+"");
							i4.setServerName(4+"");
							simageList.add(i1);
							simageList.add(i2);
							simageList.add(i3);
							simageList.add(i4);
						}
						for(simage image : simageList){
							if(image.getServerName().equals(sblock[0])){
								image.addBlock(sblock[1]);
							}
						}
						for(fimage image : fimageList){
							if(image.getFileName().equals(fileName)){
								image.addBlock(sblock[1]);
							}
						}
					}
					File file1 = new File("fimage");
					File file2 = new File("simage");
					fw1 = new FileWriter(file1);
					fw2 = new FileWriter(file2);
					for(fimage image : fimageList){
						LinkedList<String> l = image.getblockList();
						String s1 = "file:" + image.getFileName() +  " Block:";
						for(String s2 : l){
							s1 += s2 + " ";
						}
						System.out.println(s1);
						fw1.write(s1+"\r\n");
						fw1.flush();
					}
					for(simage image : simageList){
						LinkedList<String> l = image.getBlockList();
						String s1 = "server:" + image.getServerName() +  " Block:";
						for(String s2 : l){
							s1 += s2 + " ";
						}
						System.out.println(s1);
						fw2.write(s1+"\r\n");
						fw2.flush();
					}
				}
				
				s.shutdownOutput();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fw1 != null){
				try {
					fw1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fw2 != null){
				try {
					fw2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
}
