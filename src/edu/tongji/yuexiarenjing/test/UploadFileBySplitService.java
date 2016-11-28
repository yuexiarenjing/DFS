package edu.tongji.yuexiarenjing.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UploadFileBySplitService {
	// 将文件拆分成若干个64MB大小的Block上传的客户端
	public static void main(String[] args) {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 32, 60, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(16));
		int count = 0;
		for (int i = 0; i < 16; i++) {
			executor.execute(new UploadFileBySplitServiceTask(9090 + i, "part" + ++count));
		}
		executor.shutdown();
	}
}
