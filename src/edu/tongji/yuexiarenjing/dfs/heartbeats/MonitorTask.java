package edu.tongji.yuexiarenjing.dfs.heartbeats;

import java.util.LinkedList;

import edu.tongji.yuexiarenjing.dfs.entity.simage;

public class MonitorTask implements Runnable {

	String[][] serverList;
	LinkedList<simage> simageList;

	public MonitorTask(String[][] serverList, LinkedList<simage> simageList) {
		// TODO Auto-generated constructor stub
		this.serverList = serverList;
		this.simageList = simageList;
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			for (int i = 0; i < 4; i++) {
				if (serverList[i][0] != null && (System.currentTimeMillis() - Long.parseLong(serverList[i][2])) > 3000
						&& serverList[i][1].equals("running")) {
					serverList[i][0] = null;
					simageList.remove(i);
				}
			}
//			for (int i = 0; i < serverList.length; i++) {
//				if(serverList[i][0] != null){
//					System.out.println(serverList[i][0]);
//				}
//			}
//			for(simage image:simageList){
//				System.out.println(image.getServerName());
//			}
//			System.out.println("===================HeartBeats!=========================");
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
