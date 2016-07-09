/*

 * using polling for reading
 */
package com.ly.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.*;

import java.util.*;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.ly.domain.Employee;
import com.ly.service.EmployeeService;
import com.ly.service.SignService;
import com.ly.service.impl.EmployeeServiceImpl;
import com.ly.service.impl.SignServiceImpl;
import com.ly.utils.UIUtils;

import gnu.io.*;

public class SimpleRW {
	static Enumeration portList;
	static CommPortIdentifier portId;
	static String messageString = "Hello, world!\n";
	static byte[] cmdSeek = { 0x43, (byte) 0xBC, 0x09, 0x02, 0x02, 0x02, 0x26, (byte) 0xBA, (byte) 0xB0 };
	static byte[] cmdAC = { 0x43, (byte) 0xBC, 0x08, 0x02, 0x01, 0x03, (byte) 0xAB, (byte) 0xB4 };
	static byte[] cmdNoCard = { 0x43, (byte) 0xBC, 0x08, 0x02, 0x01, 0x01, (byte) 0x88, (byte) 0xA6, 0x00, 0x00, 0x00,
			0x00 };
	static SerialPort serialPort;
	static OutputStream outputStream;
	static InputStream inputStream;

	static Data_processing sHex = new Data_processing();
	static byte[] readBuffer = new byte[12];
	static String ID = new String();

	private static SimpleRW instance;
	int[] portSet =  { 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE };
	
	private static MyFrame1 my;
	
	private static boolean run=true;
	
	

	public static synchronized SimpleRW getInstance() {
		if (instance == null) {
			instance = new SimpleRW();
		}
		return instance;
	}
	

	public void setRun(boolean run) {
		SimpleRW.run = run;
	}


	public void openPort(String portName,MyFrame1 my) {
		if(portName==null){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "未选择串口", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		SimpleRW.my = my;
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(portName)) {

					try {
						serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
						if (serialPort==null){
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(null, "串口打开失败", "提示", JOptionPane.INFORMATION_MESSAGE);	
							return;
						}
						System.out.println("打开成功");
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "串口打开成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					} catch (PortInUseException e) {

					}
					try {
						outputStream = serialPort.getOutputStream();
					} catch (IOException e) {
					}
					try {
						inputStream = serialPort.getInputStream();
					} catch (IOException e) {
					}
					try {
						serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {
					}
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "串口打开失败", "提示", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("打开失败");
				}
			}
		}
		// System.out.println("打开成功");
	}

	public void portSet(int[] portset) {
		this.portSet = portset;
	}

	public void alwaysFindCard() {
		try {
			// outputStream.write(messageString.getBytes());
			System.out.println("无限寻卡");
			try {
				if(serialPort==null){
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "未打开串口", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				serialPort.setSerialPortParams(portSet[0], portSet[1], portSet[2], portSet[3]);
			} catch (UnsupportedCommOperationException e) {
			}
			// ID = "00000000";
			while (run) {
			byte[] readBuffer={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
//				try {
//					inputStream = serialPort.getInputStream();
//				} catch (IOException e) {
//				}
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}				
					outputStream.write(cmdSeek);				
				// byte[] readBuffer = new byte[5];
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				try {
					while (inputStream.available() > 0) {
						// int numBytes = inputStream.read(readBuffer);
						// System.out.println("get "+numBytes+" bytes");
						int numBytes = inputStream.read(readBuffer);
						// System.out.println("get byte:"+
						// Integer.toHexString(readBuffer));
						// if (bbb==0){
						// serialPort.close();
						// }
					}
					sHex.printHexString(readBuffer);
					if (Arrays.equals(readBuffer, cmdNoCard)) {
						System.out.println("无卡");

					}
					// System.out.println(sHex);
					// if(sHex.indexOf()){
					// System.out.println("无卡");
					// }
					// System.out.println(new String(readBuffer));
				} catch (IOException e) {
				}
				for(int i=0;i<readBuffer.length;i++){
					readBuffer[i] = 0x00;
				}
//				readBuffer={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
				System.out.println("防冲突");
				outputStream.write(cmdAC);
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
				try {
					while (inputStream.available() > 0) {
						// int numBytes = inputStream.read(readBuffer);
						// System.out.println("get "+numBytes+" bytes");
						int numBytes = inputStream.read(readBuffer);
						sHex.printHexString(readBuffer);
						// System.out.println("get byte:"+
						// Integer.toHexString(readBuffer));
						// if (bbb==0){
						// serialPort.close();
						// }
					}
					if (Arrays.equals(readBuffer, cmdNoCard)) {
						System.out.println("无卡");
						continue;
					} 
					
						ID = sHex.SelectID(readBuffer);
						EmployeeService es = new EmployeeServiceImpl();
						SignService ss = new SignServiceImpl();

						Employee em = es.findByRfid(ID);
						if (em == null) {
//							Toolkit.getDefaultToolkit().beep();
//							JOptionPane.showMessageDialog(null, "信息未匹配", "提示", JOptionPane.INFORMATION_MESSAGE);
							
//							readBuffer = cmdNoCard;
							
							JOptionPane op = new JOptionPane("信息未匹配",JOptionPane.INFORMATION_MESSAGE);
					        final JDialog dialog = op.createDialog("本对话框将在3秒后关闭");
					        
					        // 创建一个新计时器
					        Timer timer = new Timer();

					        // 3秒 后执行该任务
					        timer.schedule(new TimerTask() {
					            public void run() {
					                dialog.setVisible(false);
					                dialog.dispose();
					            }
					        }, 3000);

					        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					        dialog.setAlwaysOnTop(true);
					        dialog.setModal(false);
					        dialog.setVisible(true);
							continue;
						}
						
						ss.sign(em.getId());

						my.jpsign.remove(my.scrollPane_sign);
						my.scrollPane_sign = new JScrollPane(UIUtils.refresh2(my.jtasign));
						my.jpsign.add(my.scrollPane_sign, BorderLayout.CENTER);
						my.jpsign.repaint();
						my.jpsign.validate();
					
					// System.out.println(sHex);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String findCard() {

		try {
			// outputStream.write(messageString.getBytes());
			System.out.println("寻卡");
			// ID = "00000000";
			try {
				if(serialPort==null){
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "未打开串口", "提示", JOptionPane.INFORMATION_MESSAGE);
					return "";
				}
				serialPort.setSerialPortParams(portSet[0], portSet[1], portSet[2], portSet[3]);
			} catch (UnsupportedCommOperationException e) {
			}

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}			
				outputStream.write(cmdSeek);			
			// byte[] readBuffer = new byte[5];
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
			try {
				while (inputStream.available() > 0) {
					// int numBytes = inputStream.read(readBuffer);
					// System.out.println("get "+numBytes+" bytes");
					int numBytes = inputStream.read(readBuffer);
					// System.out.println("get byte:"+
					// Integer.toHexString(readBuffer));
					// if (bbb==0){
					// serialPort.close();
					// }
				}
				sHex.printHexString(readBuffer);
				if (Arrays.equals(readBuffer, cmdNoCard)) {
					System.out.println("无卡");
				}
				// System.out.println(sHex);
				// if(sHex.indexOf()){
				// System.out.println("无卡");
				// }
				// System.out.println(new String(readBuffer));
			} catch (IOException e) {
			}
			System.out.println("防冲突");
			outputStream.write(cmdAC);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
			try {
				while (inputStream.available() > 0) {
					// int numBytes = inputStream.read(readBuffer);
					// System.out.println("get "+numBytes+" bytes");
					int numBytes = inputStream.read(readBuffer);
					sHex.printHexString(readBuffer);
					// System.out.println("get byte:"+
					// Integer.toHexString(readBuffer));
					// if (bbb==0){
					// serialPort.close();
					// }
				}
				if (Arrays.equals(readBuffer, cmdNoCard)) {
					System.out.println("无卡");
				} else {
					ID = sHex.SelectID(readBuffer);
					return ID;
				}
				// System.out.println(sHex);
			} catch (IOException e) {
			}

		} catch (IOException e) {
		}
		
		return "";

	}
	
public void closePort(){
	if(serialPort==null){
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "未打开串口", "提示", JOptionPane.INFORMATION_MESSAGE);
	}
	serialPort.close();
}
}
