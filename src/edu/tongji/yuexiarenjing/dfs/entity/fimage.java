package edu.tongji.yuexiarenjing.dfs.entity;

import java.util.LinkedList;

public class fimage {

	private String fileName;
	private LinkedList<String> blockList;
	
	public fimage() {
		// TODO Auto-generated constructor stub
		this.blockList = new LinkedList<>();
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public LinkedList<String> getblockList() {
		return blockList;
	}

	public void setBlockNum(LinkedList<String> blockList) {
		this.blockList = blockList;
	}
	
	//添加一个Block，若已经存在则返回false，否则返回true
	public boolean addBlock(String blockNum){
		for(String str : this.blockList){
			if(blockNum == str)
				return false;
		}
		this.blockList.add(blockNum);
		return true;
	}
}
