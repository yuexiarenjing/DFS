package edu.tongji.yuexiarenjing.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 
 * @author yuexiarenjing һЩ���ڷֲ�ʽ�ļ�ϵͳ���ܵĲ��Է�������
 *
 */

public class TestDFS {
	
	@Test
	public void testFileSplit(){
		UploadFileBySplitClient("test.txt", 64);
	}

	// ���ļ���ֳ����ɸ�64MB��С��Block�ϴ��Ŀͻ���
	@Test
	public void UploadFileBySplitClient(String fileName, int splitSize) {

		File file = new File(fileName);
		List<String> splitFiles = new ArrayList<String>();

		int count = (int) Math.ceil(file.length() / (double) splitSize);

		ThreadPoolExecutor executor = new ThreadPoolExecutor(count, count * 2, 60, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(count));

		for (int i = 0; i < count; i++) {
			String splitFileName = file.getName() + "." + (i + 1) + ".part";
			executor.execute(
					new UploadFileBySplitClientTask(splitSize, splitFileName, file, i * splitSize, "127.0.0.1", 9090+i));
			splitFiles.add(splitFileName);
		}
	}

	// ���ļ���ֳ����ɸ�64MB��С��Block�ϴ��Ŀͻ���
	@Test
	public void UploadFileBySplitService() {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 32, 60, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(16));
		int count = 0;
		while(!executor.isTerminated()){
			executor.execute(new UploadFileBySplitServiceTask(9090, "part" + count));
		}
		
		executor.shutdown();
	}

	// �����ϴ������ļ��Ŀͻ���
	@Test
	public void UpLoadFileClient() {

		Socket socket = null;
		OutputStream os = null;
		FileInputStream fis = null;

		try {
			socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
			os = socket.getOutputStream();
			File file = new File("test.txt");
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int len;
			long start = System.currentTimeMillis();
			while ((len = fis.read(b)) != -1) {
				os.write(b, 0, len);
				os.flush();
			}
			long end = System.currentTimeMillis();
			System.out.println((end - start));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// �����ϴ������ļ��ķ����
	@Test
	public static void UpLoadFileService() {

		ServerSocket ss = null;
		Socket s = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			File file = new File("receivedTest.txt");
			fos = new FileOutputStream(file);
			ss = new ServerSocket(9090);
			s = ss.accept();
			is = s.getInputStream();
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) != -1) {
				fos.write(b, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//ʵ�ָ����ļ��ķ���
		public static void copyFile(String src, String dest){
			File file1 = new File(src);
			File file2 = new File(dest);
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(file1);
				fos = new FileOutputStream(file2);
				byte[] b = new byte[1024];
				int len;
				while ((len = fis.read(b)) != -1) {
					fos.write(b, 0, len);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		@Test
		public void testCopyFile(){
			long start = System.currentTimeMillis();
			String src = "test.txt";
			String dest = "test1.txt";
			copyFile(src,dest);
			long end = System.currentTimeMillis();
			System.out.println("���ѵ�ʱ��Ϊ��" + (end - start));
		}

	// ���ɴ�СΪ1GB��"test.txt"�����ļ�
	@Test
	public static void generateTestTxtFile() {

		FileWriter fw = null;
		try {
			File file = new File("test.txt");
			fw = new FileWriter(file);
			for (int i = 0; i < 1024 * 1024 * 1024 / 4; i++) {
				fw.write("test");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		generateTestTxtFile();
	}

	// ģ��ͻ��˶�ȡ�����У�����ϴ������أ�����exit�˳��ͻ��ˣ�Ŀǰ�������Ļ���û�н����������ڣ�
	@Test
	public void readCommand() {

		Scanner scanner = new Scanner(System.in);
		String str;
		while (!(str = scanner.next()).equals("exit")) {
			System.out.println(str);
		}
		scanner.close();
	}
}
