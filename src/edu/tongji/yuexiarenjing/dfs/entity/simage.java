package edu.tongji.yuexiarenjing.dfs.entity;

import java.util.LinkedList;

public class simage {

	private String serverName;
	private LinkedList<String> blockList;
	
	public simage() {
		// TODO Auto-generated constructor stub
		this.blockList = new LinkedList<>();
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public LinkedList<String> getBlockList() {
		return blockList;
	}

	public void setBlockList(LinkedList<String> blockList) {
		this.blockList = blockList;
	}

	// ���һ��Block�����Ѿ������򷵻�false�����򷵻�true
	public boolean addBlock(String blockNum) {
		for (String str : this.blockList) {
			if (blockNum == str)
				return false;
		}
		this.blockList.add(blockNum);
		return true;
	}
}
