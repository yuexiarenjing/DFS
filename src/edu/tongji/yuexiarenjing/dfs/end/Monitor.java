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

	public static String[][] serverList = new String[4][3]; //��ŷ����������б�IP���˿ڣ�ʱ�����
	public static LinkedList<fimage> fimageList = new LinkedList<>();//���file image���ļ���->Blocks��
	public static LinkedList<simage> simageList = new LinkedList<>();//���server image����������->Blocks��

	public static void main(String[] args) {
		Monitor monitor = new Monitor();
		monitor.intiHeartBeats();
		monitor.initMonitor();
		monitor.initListenClientUpload();
		monitor.intiListenClientGetFileList();
		monitor.intiListenClientDownLoad();
	}

	// ʵ����������4��������
	public void intiHeartBeats() {
		for (int i = 1; i <= 4; i++) {
			HeartBeatsMonitorTask task =
					new HeartBeatsMonitorTask(10000 + i, serverList, i);
			Thread t = new Thread(task);
			t.start();
		}
	}

	// ʵ�ּ��������������б�
	public void initMonitor() {
		MonitorTask task = new MonitorTask(serverList, simageList);
		Thread t = new Thread(task);
		t.start();
	}
	
	//ʵ�ּ����ͻ��˷��ļ�������
	public void initListenClientUpload(){
		ListenClientUploadTask task = new ListenClientUploadTask(serverList, fimageList, simageList, 10005);
		Thread t = new Thread(task);
		t.start();
	}
	
	//ʵ�ּ����ͻ��˻�ȡ�ļ��б������
	public void intiListenClientGetFileList(){
		ListenClientGetFileListTask task = new ListenClientGetFileListTask(10006, fimageList);
		Thread t = new Thread(task);
		t.start();
	}
	
	//ʵ�ּ����ͻ��˽�
	public void intiListenClientDownLoad(){
		ListenClientDownloadTask task = new ListenClientDownloadTask(serverList, fimageList, simageList, 10007);
		Thread t = new Thread(task);
		t.start();
	}
}
