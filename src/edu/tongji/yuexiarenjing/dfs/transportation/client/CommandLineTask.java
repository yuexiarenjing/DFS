package edu.tongji.yuexiarenjing.dfs.transportation.client;

import java.io.File;
import java.util.Scanner;


public class CommandLineTask implements Runnable {

	String Command;
	String MonitorIP;
	
	public CommandLineTask(String MonitorIP) {
		// TODO Auto-generated constructor stub
		this.MonitorIP = MonitorIP;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Scanner scanner = null;
		while (true) {
			scanner = new Scanner(System.in);
			String command = scanner.next();
			if (command.equals("exit")) {
				scanner.close();
				break;
			} else if(command.contains("upload")){
				System.out.println(command);
				String filePath = command.substring(7);
				File file = new File(filePath);
				if (file.exists() && file.isFile()) {
					//获取服务器列表和fsimage
					ClentSendFileRequestTask task = new ClentSendFileRequestTask(MonitorIP, 10005, filePath);
					Thread t = new Thread(task);
					t.start();
				}else{
					System.out.println("输入文件路径！");
				}
			}else if(command.equals("getfilelist")){
				//获取文件列表
				GetFileList task =  new GetFileList(MonitorIP, 10006);
				Thread t = new Thread(task);
				t.start();
			}else if(command.contains("download")){
				String filePath = command.substring(9);
				
				ClientDownloadTask task = new ClientDownloadTask(MonitorIP, 10007, filePath);
				Thread t = new Thread(task);
				t.start();
			}
		}
	}
}
