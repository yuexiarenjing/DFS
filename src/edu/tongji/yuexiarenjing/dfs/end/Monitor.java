package edu.tongji.yuexiarenjing.dfs.end;

import java.util.LinkedList;

import edu.tongji.yuexiarenjing.dfs.entity.fimage;
import edu.tongji.yuexiarenjing.dfs.entity.simage;
import edu.tongji.yuexiarenjing.dfs.heartbeats.HeartBeatsMonitorTask;
import edu.tongji.yuexiarenjing.dfs.heartbeats.MonitorTask;
import edu.tongji.yuexiarenjing.dfs.transportation.monitor.ListenClientDownloadTask;
import edu.tongji.yuexiarenjing.dfs.transportation.monitor.ListenClientGetFileListTask;
import edu.tongji.yuexiarenjing.dfs.transportation.monitor.ListenClientUploadTask;

public class Monitor {

	public static String[][] serverList = new String[4][3]; //存放服务器在线列表（IP，端口，时间戳）
	public static LinkedList<fimage> fimageList = new LinkedList<>();//存放file image（文件名->Blocks）
	public static LinkedList<simage> simageList = new LinkedList<>();//存放server image（服务器名->Blocks）

	public static void main(String[] args) {
		Monitor monitor = new Monitor();
		monitor.intiHeartBeats();
		monitor.initMonitor();
		monitor.initListenClientUpload();
		monitor.intiListenClientGetFileList();
		monitor.intiListenClientDownLoad();
	}

	// 实现心跳监听4个服务器
	public void intiHeartBeats() {
		for (int i = 1; i <= 4; i++) {
			HeartBeatsMonitorTask task =
					new HeartBeatsMonitorTask(10000 + i, serverList, i);
			Thread t = new Thread(task);
			t.start();
		}
	}

	// 实现监听服务器在线列表
	public void initMonitor() {
		MonitorTask task = new MonitorTask(serverList, simageList);
		Thread t = new Thread(task);
		t.start();
	}
	
	//实现监听客户端发文件的请求
	public void initListenClientUpload(){
		ListenClientUploadTask task = new ListenClientUploadTask(serverList, fimageList, simageList, 10005);
		Thread t = new Thread(task);
		t.start();
	}
	
	//实现监听客户端获取文件列表的请求
	public void intiListenClientGetFileList(){
		ListenClientGetFileListTask task = new ListenClientGetFileListTask(10006, fimageList);
		Thread t = new Thread(task);
		t.start();
	}
	
	//实现监听客户端建
	public void intiListenClientDownLoad(){
		ListenClientDownloadTask task = new ListenClientDownloadTask(serverList, fimageList, simageList, 10007);
		Thread t = new Thread(task);
		t.start();
	}
}
