package edu.tongji.yuexiarenjing.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UploadFileBySplitClient {
	
	public static void main(String[] args) {
		UploadFileBySplitClientMethod("test.txt", 64);
	}
	
	public static void UploadFileBySplitClientMethod(String fileName, int splitSize) {

		File file = new File(fileName);
		List<String> splitFiles = new ArrayList<String>();

		int count = (int) Math.ceil(file.length() / (double) (splitSize*1024*1024));

		ThreadPoolExecutor executor = new ThreadPoolExecutor(count, count * 2, 60, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(count));

		for (int i = 0; i < count; i++) {
			String splitFileName = file.getName() + "." + (i + 1) + ".part";
			executor.execute(
					new UploadFileBySplitClientTask(splitSize, splitFileName, file, i * splitSize * 1024 * 1024, "127.0.0.1", 9090+i));
			splitFiles.add(splitFileName);
		}
	}	
}

