//package edu.tongji.yuexiarenjing.test;
//
//import java.io.*;
//import java.net.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//
//import javax.swing.*;
//
//
//
// 
//
//public class clientgui {
//	
//	
//	
//	
//	public static void main(String[] args)
//	{
//		myframe f = new myframe();
//		
//		
//		
//	}
//
//}
//
//class myframe extends Frame 
//{   
//	JButton b2 = new JButton("上传");
//	JButton b0 = new JButton("浏览");
//	JButton b1 = new JButton("下载");
//    Panel p1 = new Panel(new GridBagLayout());	
//    TextArea t1 = new TextArea();
//    
//    TextField t2 = new TextField();
//	
// myframe()
// {
//	super("myframe");
//	//setLayout(new BorderLayout());
//	setSize(600,300);
//    setLayout(new GridBagLayout());
//    
//   // t1.setBounds(0, 0, 200, 200);
//    //t1.setColumns(100); 
//    t2.setColumns(40);
//    
//    
//	
//	//add(p3);
//	//add(p4);
//	add(p1,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(1,1,1,1),0,0));
//	p1.add(t2,new GridBagConstraints(0,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(1,1,1,1),0,0));
//	
//	p1.add(b0,new GridBagConstraints(1,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(1,1,1,1),0,0));
//	setVisible(true);
//	
//	p1.add(b2,new GridBagConstraints(3,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(1,1,1,1),0,0));
//	setVisible(true);
//	
//	p1.add(b1,new GridBagConstraints(2,0,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(1,1,1,1),0,0));
//	
//	p1.add(t1,new GridBagConstraints(0,1,4,1,0,0,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets(1,1,1,1),0,0));
//	this.setVisible(true);
//	Monitor bh = new Monitor();
//	FileChooser bh2 = new FileChooser();
//	choosedFile cf = new choosedFile();
//	b1.addActionListener(bh);
//	b0.addActionListener(bh2);
//	b2.addActionListener(cf);
//	 
//	 
//	 
// }
//class Monitor implements ActionListener
//{
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		System.out.print("hahaahah");
//		JButton b = (JButton)e.getSource();
//		b.setText("我被点了");;// TODO Auto-generated method stub
//		
//	}
//	
//
//
//}
//
//
//
//
//
//class choosedFile implements ActionListener 
//{
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if(t2.getText()== null)
//		{
//			System.out.print("没有选择文件");
//		}
//		else 
//			
//		{
//			
//			System.out.print(t2.getText());
//			clie c= new clie(t2.getText());
//			try {
//				c.run();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//		}
//		
//		
//		
//		
//		
//		
//	}
//	
//
//
//}
//
//
//
//
//
//public class FileChooser extends JFrame implements ActionListener{
//	JButton open=null;
//	
//	public FileChooser(){
//		
//	}
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		JFileChooser jfc=new JFileChooser();
//		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
//		jfc.showDialog(new JLabel(), "选择");
//		
//	   
//		
//		File file=jfc.getSelectedFile();
//		
//		if(file.isDirectory()){
//			System.out.println("文件夹:"+file.getAbsolutePath());
//		}else if(file.isFile()){
//			System.out.println("文件:"+file.getAbsolutePath());
//		}
//		
//		 
//			
//		
//		System.out.println(jfc.getSelectedFile().getName());
//		t2.setText(file.getAbsolutePath());
//		}
//		
//		
//			
//	
//
//}
//
//
//
//}
//
//
//
//
//
//
//
//
//
// class clie {
//	File file = null;
//	int size =0;
//	String filp;
//	
//	
//	 clie(String filepath)
//	{
//		
//		file = new File(filepath);
//		filp = filepath;
//		
//		
//	}
//	
//	public void run() throws Exception
//	//public static void main(String[] args) throws Exception
//	{
//		FileInputStream fis = null;
//		fis = new FileInputStream(file);
//		System.out.println(fis.available()+"大小");
//		
//		size =fis.available();
//		
//		System.out.println(calnum());
//		
//		for(int i =1;i<=calnum();i++){
//			String blocknumber = String.valueOf(i);
//		
//		new uploadThread(blocknumber,filp,"127.0.0.1",i).start();
//		}
//	/*	Socket socket = null;
//		OutputStream os = null;
//		FileInputStream fis = null;
//
//		try {
//			socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
//			os = socket.getOutputStream();
//			//File file = new File("E:\\file/k.mp3");
//			fis = new FileInputStream(file);
//			
//			System.out.println(fis.available()+"大小");
//			
//			size =fis.available();
//			
//			System.out.println(calnum());
//			
//			
//			byte[] b = new byte[1024];
//			int len;
//			long start = System.currentTimeMillis();
//			while ((len = fis.read(b)) != -1) {
//				os.write(b, 0, len);
//				os.flush();
//			}
//			long end = System.currentTimeMillis();
//			System.out.println((end - start));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (os != null) {
//				try {
//					os.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (fis != null) {
//				try {
//					fis.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (socket != null) {
//				try {
//					socket.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//	     
//	       
//			
//		
//		//System.out.print(b);
//		
//			
//	*/	
//		
//	}
//
//   public int calnum() 
//   {
//	   
//	   return size/67108864 +1;
//   }	
//	
//}
// 
// 
// 
// class uploadThread extends Thread{
//	String ip;
//	 File file = null;
//	OutputStream os = null;
//	FileInputStream fis = null;
//	DataOutputStream dos =null;
//	 String number;
//	 Socket socket;
//	 int position;
//	 public uploadThread(String num,String Filepath,String IP,int pos)
//	 {
//		 number = num;
//		 file = new File(Filepath);
//		 ip=IP;
//		 position = pos;
//		 
//	 }
//	 public void run()
//	 {
//		 try {
//				socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
//				os = socket.getOutputStream();
//				//File file = new File("E:\\file/k.mp3");
//				fis = new FileInputStream(file);
//				dos = new DataOutputStream(os);
//				int count =0;
//				
//				
//				dos.writeUTF(number);
//				fis.skip((position-1)*67108864);
//				
//				//System.out.println(fis.available()+"大小");
//				byte[] b = new byte[1024];
//				int len;
//				long start = System.currentTimeMillis();
//				while ((len = fis.read(b)) != -1) {
//					os.write(b, 0, len);
//					os.flush();
//					count ++;
//					
//					if(count==65536)
//					break;
//					
//					
//					
//			}
//				long end = System.currentTimeMillis();
//				System.out.println((end - start));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				if (os != null) {
//					try {
//						os.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				if (fis != null) {
//					try {
//						fis.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				if (socket != null) {
//					try {
//						socket.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			
//		 
//		 
//		 
//	 }
//	 
//	 
//	 
//	 
//	 
//	 
//	 }	 
//	 
// }
//
//
