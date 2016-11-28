package edu.tongji.yuexiarenjing.dfs.transportation.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveFile implements Runnable {

	String[] serverList = new String[4]; // 存放服务器在线列表（IP）

	int PORT;
	int SERVERNO;
	String MonitorIP;
	int MonitorPORT;

	public ReceiveFile(int port, int serverNo, String monitorIP, int monitorPORT) {
		// TODO Auto-generated constructor stub
		this.PORT = port;
		this.SERVERNO = serverNo;
		this.MonitorIP = monitorIP;
		this.MonitorPORT = monitorPORT;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket ss = null;
		Socket s = null;
		Socket backUpSocket = null;
		OutputStream os = null;
		FileOutputStream fos = null;
		InputStream is = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			File fold = new File("D:\\Server" + SERVERNO);
			if (!fold.exists()) {
				fold.mkdir();
			}

			ss = new ServerSocket(PORT);
			while (true) {
				s = ss.accept();
				File file = null;
				// 接收客户端传来的文件
				is = s.getInputStream();
				dis = new DataInputStream(is);
				String blockServerList = dis.readUTF();
				String[] blockServerListSplit = blockServerList.split("#");
				String blockNum = blockServerListSplit[0];
				String str = blockServerListSplit[1];
				if (!blockNum.equals("blockNum")) {
					String[] slist = str.split(" ");
					// 获取服务器IP列表
					for (String st : slist) {
						String[] sp = st.split("/");
						if (sp[0].contains("1")) {
							serverList[0] = sp[1];
						} else if (sp[0].contains("2")) {
							serverList[1] = sp[1];
						} else if (sp[0].contains("3")) {
							serverList[2] = sp[1];
						} else if (sp[0].contains("4")) {
							serverList[3] = sp[1];
						}
					}
					for (int i = 0; i < 4; i++) {
						if (SERVERNO == (i + 1)) {
							if (serverList[SERVERNO % 4] != null) {
								backUpSocket = new Socket(serverList[SERVERNO % 4], (9021 + (SERVERNO % 4)));
							} else {
								System.out.println("有服务器down掉！");
								backUpSocket = new Socket(serverList[(SERVERNO + 1) % 4],
										(9021 + ((SERVERNO + 1) % 4)));
							}
						}
					}
					for (int i = 0; i < serverList.length; i++) {
						serverList[i] = null;
					}
					os = backUpSocket.getOutputStream();
					dos = new DataOutputStream(os);
					// dos.writeUTF("blockNum#" + blockNum);
					dos.writeUTF(blockNum);

					file = new File("D:\\Server" + SERVERNO + "\\Block" + blockNum);
					fos = new FileOutputStream(file);

					byte[] b = new byte[1024];
					int len;
					while ((len = is.read(b)) != -1) {
						fos.write(b, 0, len);
						fos.flush();
						os.write(b, 0, len);
						os.flush();
					}

					logg log = new logg();
					log.updatelog(blockNum + "接受完毕 " + "\r\n", SERVERNO + "");
					s.shutdownOutput();
					backUpSocket.shutdownOutput();
				} else {
					file = new File("D:\\Server" + SERVERNO + "\\Block" + str);
					fos = new FileOutputStream(file);

					byte[] b = new byte[1024];
					int len;
					while ((len = is.read(b)) != -1) {
						fos.write(b, 0, len);
						fos.flush();
					}

					s.shutdownOutput();
				}
				if (dos != null) {
					try {
						dos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (os != null) {
					try {
						os.close();
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
				if (backUpSocket != null) {
					try {
						backUpSocket.close();
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
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (backUpSocket != null) {
				try {
					backUpSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
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
			if (is != null) {
				try {
					is.close();
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
}
