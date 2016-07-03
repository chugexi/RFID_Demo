/*

 * using polling for reading
 */
package com.ly.ui;

import java.awt.BorderLayout;
import java.io.*;

import java.util.*;

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
	private static String portname;
	private static MyFrame1 my;

	public static synchronized SimpleRW getInstance(String portName, MyFrame1 myframe) {
		if (instance == null) {
			instance = new SimpleRW();
		}
		portname = portName;
		my = myframe;
		return instance;
	}

	public static synchronized SimpleRW getInstance() {
		if (instance == null) {
			instance = new SimpleRW();
		}
		return instance;
	}

	public void openPort() {
		portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(portname)) {

					try {
						serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
						System.out.println("打开成功");
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
					System.out.println("打开失败");
				}
			}
		}
		// System.out.println("打开成功");
	}

	public void alwaysFindCard() {
		try {
			// outputStream.write(messageString.getBytes());
			System.out.println("无限寻卡");
			// ID = "00000000";
			while (!(ID.equals("00000000"))) {
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
						EmployeeService es = new EmployeeServiceImpl();
						SignService ss = new SignServiceImpl();

						Employee em = es.findByRfid(ID);

						ss.sign(em.getId());

						my.jpsign.remove(my.scrollPane_sign);
						my.scrollPane_sign = new JScrollPane(UIUtils.refresh(my.jtasign));
						my.jpsign.add(my.scrollPane_sign, BorderLayout.CENTER);
						my.jpsign.repaint();
						my.jpsign.validate();
					}
					// System.out.println(sHex);
				} catch (IOException e) {
				}
			}
		} catch (IOException e) {
		}

	}

	public String findCard() {

		try {
			// outputStream.write(messageString.getBytes());
			System.out.println("寻卡");
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
					byte bbb = (byte) inputStream.read();
					System.out.println("get byte:" + Integer.toHexString(bbb & 0xff));
					// if (bbb==0){
					// serialPort.close();
					// }
				}
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
					byte bbb = (byte) inputStream.read();
					System.out.println("get byte:" + Integer.toHexString(bbb & 0xff));
					return Integer.toHexString(bbb & 0xff);
					// if (bbb==0){
					// serialPort.close();
					// }
				}
				// System.out.println(new String(readBuffer));
			} catch (IOException e) {
			}
		} catch (IOException e) {
		}
		serialPort.close();
		return null;

	}

	public void haha() {
		int i = 0;
		while (true) {
			i++;
			if (i == 2) {

				my.jpsign.remove(my.scrollPane_sign);
				my.scrollPane_sign = new JScrollPane(UIUtils.refresh(my.jtasign));
				my.jpsign.add(my.scrollPane_sign, BorderLayout.CENTER);
				my.jpsign.repaint();
				my.repaint();
				System.out.println("22222");
				i = 0;

			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// public static void main(String[] args) {
	// my.jpsign.remove(my.scrollPane_sign);
	// my.scrollPane_sign = new JScrollPane(UIUtils.refresh( my.jtasign));
	// my.jpsign.add(my.scrollPane_sign,BorderLayout.CENTER);
	// my.jpsign.repaint();
	// my.repaint();
	//
	//
	//
	//
	// }
	// // SerialPort serialPort;
	// portList = CommPortIdentifier.getPortIdentifiers();
	//
	// while (portList.hasMoreElements()) {
	// portId = (CommPortIdentifier) portList.nextElement();
	// if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	// if (portId.getName().equals("COM8")) {
	// // if (portId.getName().equals("/dev/term/a")) {
	// try {
	// serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
	// } catch (PortInUseException e) {
	// }
	// try {
	// outputStream = serialPort.getOutputStream();
	// } catch (IOException e) {
	// }
	// try {
	// inputStream = serialPort.getInputStream();
	// } catch (IOException e) {
	// }
	//
	// // serialPort.notifyOnDataAvailable(true);
	//
	// try {
	// serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
	// SerialPort.STOPBITS_1,
	// SerialPort.PARITY_NONE);
	// } catch (UnsupportedCommOperationException e) {
	// }
	// try {
	// // outputStream.write(messageString.getBytes());
	// System.out.println("寻卡");
	// outputStream.write(cmdSeek);
	// // byte[] readBuffer = new byte[5];
	// try {
	// Thread.sleep(500);
	// } catch (Exception e) {
	// }
	// try {
	// while (inputStream.available() > 0) {
	// // int numBytes = inputStream.read(readBuffer);
	// // System.out.println("get "+numBytes+" bytes");
	// byte bbb = (byte) inputStream.read();
	// System.out.println("get byte:" + Integer.toHexString(bbb & 0xff));
	// // if (bbb==0){
	// // serialPort.close();
	// // }
	// }
	// // System.out.println(new String(readBuffer));
	// } catch (IOException e) {
	// }
	// System.out.println("防冲突");
	// outputStream.write(cmdAC);
	// try {
	// Thread.sleep(500);
	// } catch (Exception e) {
	// }
	// try {
	// while (inputStream.available() > 0) {
	// // int numBytes = inputStream.read(readBuffer);
	// // System.out.println("get "+numBytes+" bytes");
	// byte bbb = (byte) inputStream.read();
	// System.out.println("get byte:" + Integer.toHexString(bbb & 0xff));
	// // if (bbb==0){
	// // serialPort.close();
	// // }
	// }
	// // System.out.println(new String(readBuffer));
	// } catch (IOException e) {
	// }
	// } catch (IOException e) {
	// }
	// serialPort.close();
	// }
	// }
	// }
	// }

}
