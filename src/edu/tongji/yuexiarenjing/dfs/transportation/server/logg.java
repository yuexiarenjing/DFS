package edu.tongji.yuexiarenjing.dfs.transportation.server;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class logg {

	public void updatelog(String info, String sernum) throws IOException {
		File file = new File("D:/Server" + sernum + "/" + "/upload.txt");

		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(raf.length());
		raf.writeUTF(time + " " + info);

		raf.close();

	}

	public void downloadlog(String info, String sernum) throws IOException {
		File file = new File("D:/Server" + sernum + "/" + "/download.txt");

		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(raf.length());
		raf.writeUTF(time + " " + info);

		raf.close();

	}

}
