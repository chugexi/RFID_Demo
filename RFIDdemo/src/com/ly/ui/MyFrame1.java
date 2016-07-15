package com.ly.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.ly.domain.Attence;
import com.ly.domain.Employee;
import com.ly.rxtx.SimpleRW;
import com.ly.service.EmployeeService;
import com.ly.service.SignService;
import com.ly.service.impl.EmployeeServiceImpl;
import com.ly.service.impl.SignServiceImpl;
import com.ly.test.JarTool;
import com.ly.thread.Currenttime;
import com.ly.thread.MyThread;
import com.ly.utils.UIUtils;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class MyFrame1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	EmployeeService service_e = new EmployeeServiceImpl();
	SignService service = new SignServiceImpl();

	private static Properties timeconfig = new Properties();
	String path = MyFrame1.class.getClassLoader().getResource("time.properties").toString();
	String path2 = path.substring(6, path.length());
	static String realpath;
	

	
	static {
		try {
			realpath = JarTool.getJarPath();
			realpath = realpath.replace("\\", "/");
			
			InputStream in = new FileInputStream(realpath+"/time.properties");
			timeconfig.load(in);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	JTabbedPane p;

	// 串口相关
	JComboBox<String> cbportList;
	String portname;
	JButton bOpenSPort;
	JButton bCloseSPort;
	JLabel lSportStatus;

	JPanel jpsr;
	JPanel jpmr;
	JPanel jpst;
	public JPanel jpsign;

	String[] Names = { "姓名", "签到时间", "签退时间", "考核情况" };
	static String[] Names_mr = { "工号", "姓名", "性别", "职位", "部门", "卡号", "修改", "删除" };
	JTable jtasr_re;
	JTable jtamr_re;
	public JTable jtasign;

	JScrollPane scrollPane;
	public JScrollPane scrollPane_sign;
	JScrollPane scrollPane_mr;
	JTextField jtmr_query;

	JTextField jtst;
	JTextField jtsr;

	MyPaneUI myui = new MyPaneUI();

	JComboBox<String> cbportList1;
	JComboBox<String> cbportList2;
	JComboBox<String> cbportList3;
	JComboBox<String> cbportList4;

	JComboBox<String> time1;
	JComboBox<String> time2;
	JComboBox<String> time3;
	JComboBox<String> time4;
	JComboBox<String> time5;
	JComboBox<String> time6;
	JComboBox<String> time7;
	JComboBox<String> time8;
	JComboBox<String> time9;

	JComboBox<String> year;
	JComboBox<String> mouth;
	JComboBox<String> day;
	Date date;
	GregorianCalendar gccalendar;

	public static JLabel time;
	Currenttime ct;

	Thread thread;

	int[] portSet = { 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE };
	FileOutputStream file;

	SimpleRW simple = SimpleRW.getInstance();

	public MyFrame1() {
		

		setTitle("考勤管理系统");
		String signinMin = timeconfig.getProperty("signinMin");
		String signinMax = timeconfig.getProperty("signinMax");
		String signoutMin = timeconfig.getProperty("signoutMin");
		String signoutMax = timeconfig.getProperty("signoutMax");
		String lateDelay = timeconfig.getProperty("lateDelay");

		String inMin[] = signinMin.split(":");
		String inMax[] = signinMax.split(":");
		String outMin[] = signoutMin.split(":");
		String outMax[] = signoutMax.split(":");

		final MyFrame1 my = this;

		getContentPane().setLayout(new BorderLayout());
		p = new JTabbedPane(JTabbedPane.BOTTOM);

		jpsr = new JPanel(new BorderLayout());
		jpmr = new JPanel(new BorderLayout());
		jpst = new JPanel(null);
		/**************************************************
		 * 
		 ************************ 串口选择**************
		 * 
		 ***************************************************/

		JPanel pNorth = new JPanel();
		cbportList = new JComboBox<String>();

		CommPortIdentifier portId;
		Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
		while (en.hasMoreElements()) {
			portId = en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				cbportList.addItem(portId.getName());
			}
		}

		simple.openPort(cbportList.getItemAt(0), my);
		pNorth.add(cbportList);
		bOpenSPort = new JButton("打开串口");
		bCloseSPort = new JButton("关闭串口");
		bCloseSPort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				simple.closePort();

			}
		});
		bOpenSPort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// simple = SimpleRW.getInstance((String)
				// cbportList.getSelectedItem(), my);
				System.out.println((String) cbportList.getSelectedItem());
				simple.openPort((String) cbportList.getSelectedItem(), my);

			}
		});
		pNorth.add(bOpenSPort);
		pNorth.add(bCloseSPort);
		lSportStatus = new JLabel("    ");
		pNorth.add(lSportStatus);
		// add(pNorth, BorderLayout.NORTH);

		/**************************************************
		 * 
		 ************************ shezhi **************
		 * 
		 ***************************************************/

		String[] baudrate = { "1382400", "921600", "460800", "256000", "230400", "128000", "115200", "76800", "57600",
				"43000", "38400", "19200", "14400", "9600", "4800", "2400", "1200" };
		String[] stopbit = { "1", "1.25", "2" };
		String[] databit = { "5", "6", "7", "8" };
		String[] checkbit = { "偶校验", "奇校验", "无" };

		String[] hour = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23" };
		String[] minute = { "00", "10", "20", "30", "40", "50" };
		String[] lateLimit = { "无", "1小时", "2小时", "3小时" };

		cbportList1 = new JComboBox<String>();
		cbportList2 = new JComboBox<String>();
		cbportList3 = new JComboBox<String>();
		cbportList4 = new JComboBox<String>();

		time1 = new JComboBox<String>();
		time2 = new JComboBox<String>();
		time3 = new JComboBox<String>();
		time4 = new JComboBox<String>();
		time5 = new JComboBox<String>();
		time6 = new JComboBox<String>();
		time7 = new JComboBox<String>();
		time8 = new JComboBox<String>();
		time9 = new JComboBox<String>();

		for (int i = 0; i < baudrate.length; i++) {
			cbportList1.addItem(baudrate[i]);
		}

		for (int i = 0; i < databit.length; i++) {
			cbportList2.addItem(databit[i]);
		}

		for (int i = 0; i < stopbit.length; i++) {
			cbportList3.addItem(stopbit[i]);
		}

		for (int i = 0; i < checkbit.length; i++) {
			cbportList4.addItem(checkbit[i]);
		}

		for (int i = 0; i < hour.length; i++) {
			time1.addItem(hour[i]);
		}

		for (int i = 0; i < minute.length; i++) {
			time2.addItem(minute[i]);
		}

		for (int i = 0; i < hour.length; i++) {
			time3.addItem(hour[i]);
		}

		for (int i = 0; i < minute.length; i++) {
			time4.addItem(minute[i]);
		}

		for (int i = 0; i < hour.length; i++) {
			time5.addItem(hour[i]);
		}

		for (int i = 0; i < minute.length; i++) {
			time6.addItem(minute[i]);
		}

		for (int i = 0; i < hour.length; i++) {
			time7.addItem(hour[i]);
		}

		for (int i = 0; i < minute.length; i++) {
			time8.addItem(minute[i]);
		}

		for (int i = 0; i < lateLimit.length; i++) {
			time9.addItem(lateLimit[i]);
		}

		for (int i = 0; i < hour.length; i++) {
			if (time1.getItemAt(i).equals(inMin[0])) {
				time1.setSelectedIndex(i);
			}
		}
		for (int i = 0; i < minute.length; i++) {
			if (time2.getItemAt(i).equals(inMin[1])) {
				time2.setSelectedIndex(i);
			}
		}

		for (int i = 0; i < hour.length; i++) {
			if (time3.getItemAt(i).equals(outMin[0])) {
				time3.setSelectedIndex(i);
			}
		}
		for (int i = 0; i < minute.length; i++) {
			if (time4.getItemAt(i).equals(outMin[1])) {
				time4.setSelectedIndex(i);
			}
		}

		for (int i = 0; i < hour.length; i++) {
			if (time5.getItemAt(i).equals(inMax[0])) {
				time5.setSelectedIndex(i);
			}
		}
		for (int i = 0; i < minute.length; i++) {
			if (time6.getItemAt(i).equals(inMax[1])) {
				time6.setSelectedIndex(i);
			}
		}

		for (int i = 0; i < hour.length; i++) {
			if (time7.getItemAt(i).equals(outMax[0])) {
				time7.setSelectedIndex(i);
			}
		}
		for (int i = 0; i < minute.length; i++) {
			if (time8.getItemAt(i).equals(outMax[1])) {
				time8.setSelectedIndex(i);
			}
		}
		switch (lateDelay) {
		case "0":
			time9.setSelectedIndex(0);
			break;
		case "1":
			time9.setSelectedIndex(1);
			break;
		case "2":
			time9.setSelectedIndex(2);
			break;
		case "3":
			time9.setSelectedIndex(3);
			break;

		default:
			break;
		}

		cbportList1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {

					String s = (String) cbportList1.getSelectedItem();
					switch (s) {
					case "1382400":
						portSet[0] = 1382400;
						break;
					case "921600":
						portSet[0] = 921600;
						break;
					case "460800":
						portSet[0] = 460800;
						break;
					case "256000":
						portSet[0] = 256000;
						break;
					case "230400":
						portSet[0] = 230400;
						break;
					case "128000":
						portSet[0] = 128000;
						break;
					case "115200":
						portSet[0] = 115200;
						break;
					case "76800":
						portSet[0] = 76800;
						break;
					case "57600":
						portSet[0] = 57600;
						break;
					case "43000":
						portSet[0] = 43000;
						break;
					case "38400":
						portSet[0] = 38400;
						break;
					case "19200":
						portSet[0] = 19200;
						break;
					case "14400":
						portSet[0] = 14400;
						break;
					case "9600":
						portSet[0] = 9600;
						break;
					case "4800":
						portSet[0] = 4800;
						break;
					case "2400":
						portSet[0] = 2400;
						break;
					case "1200":
						portSet[0] = 1200;
						break;
					default:
						break;
					}
					System.out.println(portSet[0]);

					simple.portSet(portSet);

				}
			}
		});

		cbportList2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) cbportList2.getSelectedItem();
					switch (s) {
					case "5":
						portSet[1] = SerialPort.DATABITS_5;
						break;
					case "6":
						portSet[1] = SerialPort.DATABITS_6;
						break;
					case "7":
						portSet[1] = SerialPort.DATABITS_7;
						break;
					case "8":
						portSet[1] = SerialPort.DATABITS_8;
						break;

					default:
						break;
					}
					System.out.println(portSet[1]);

					simple.portSet(portSet);
				}
			}
		});

		cbportList3.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) cbportList3.getSelectedItem();
					switch (s) {
					case "1":
						portSet[2] = SerialPort.STOPBITS_1;
						break;
					case "1.25":
						portSet[2] = SerialPort.STOPBITS_1_5;
						break;
					case "2":
						portSet[2] = SerialPort.STOPBITS_2;
						break;

					default:
						break;
					}
					System.out.println(portSet[2]);

					simple.portSet(portSet);
				}
			}
		});

		cbportList4.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String) cbportList4.getSelectedItem();
					switch (s) {
					case "奇校验":
						portSet[3] = SerialPort.PARITY_ODD;
						break;
					case "偶校验":
						portSet[3] = SerialPort.PARITY_EVEN;
						break;
					case "无":
						portSet[3] = SerialPort.PARITY_NONE;
						break;
					default:
						break;
					}
					System.out.println(portSet[3]);
					simple.portSet(portSet);
				}
			}
		});
		cbportList1.setSelectedIndex(13);
		cbportList2.setSelectedIndex(3);
		cbportList3.setSelectedIndex(0);
		cbportList4.setSelectedIndex(2);

		cbportList1.setBounds(200, 150, 80, 20);
		cbportList2.setBounds(200, 200, 80, 20);
		cbportList3.setBounds(200, 250, 80, 20);
		cbportList4.setBounds(200, 300, 80, 20);

		JLabel jbaudrate = new JLabel("波特率", JLabel.CENTER);
		JLabel jdatabit = new JLabel("数据位", JLabel.CENTER);
		JLabel jstopbit = new JLabel("停止位", JLabel.CENTER);
		JLabel jcheckbit = new JLabel("校验位", JLabel.CENTER);

		JLabel jsignin = new JLabel("签到时间段", JLabel.CENTER);
		JLabel jsignout = new JLabel("签退时间段", JLabel.CENTER);
		JLabel latelimite = new JLabel("迟到时限    ", JLabel.CENTER);

		JLabel t1 = new JLabel(":", JLabel.CENTER);
		JLabel t2 = new JLabel(":", JLabel.CENTER);
		JLabel t3 = new JLabel(":", JLabel.CENTER);
		JLabel t4 = new JLabel(":", JLabel.CENTER);
		JLabel t5 = new JLabel("~", JLabel.CENTER);
		JLabel t6 = new JLabel("~", JLabel.CENTER);

		JButton timeset = new JButton("修改考勤时间");

		jbaudrate.setBounds(100, 150, 80, 20);
		jdatabit.setBounds(100, 200, 80, 20);
		jstopbit.setBounds(100, 250, 80, 20);
		jcheckbit.setBounds(100, 300, 80, 20);

		jsignin.setBounds(500, 150, 80, 20);
		jsignout.setBounds(500, 200, 80, 20);
		latelimite.setBounds(500, 250, 80, 20);

		t1.setBounds(630, 150, 40, 20);
		t2.setBounds(630, 200, 40, 20);
		t3.setBounds(780, 150, 40, 20);
		t4.setBounds(780, 200, 40, 20);
		t5.setBounds(705, 150, 40, 20);
		t6.setBounds(705, 200, 40, 20);

		time1.setBounds(600, 150, 40, 20);
		time2.setBounds(660, 150, 40, 20);
		time3.setBounds(600, 200, 40, 20);
		time4.setBounds(660, 200, 40, 20);
		time5.setBounds(750, 150, 40, 20);
		time6.setBounds(810, 150, 40, 20);
		time7.setBounds(750, 200, 40, 20);
		time8.setBounds(810, 200, 40, 20);
		time9.setBounds(600, 250, 65, 20);

		timeset.setBounds(620, 290, 120, 30);

		timeset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String signinMin = (String) time1.getSelectedItem() + ":" + (String) time2.getSelectedItem(); 
				String signoutMin = (String) time3.getSelectedItem() + ":" + (String) time4.getSelectedItem();
				String signinMax = (String) time5.getSelectedItem() + ":" + (String) time6.getSelectedItem();
				String signoutMax = (String) time7.getSelectedItem() + ":" + (String) time8.getSelectedItem();
				String lateDelay = "";
				switch ((String) time9.getSelectedItem()) {
				case "无":
					lateDelay = "0";
					break;
				case "1小时":
					lateDelay = "1";
					break;
				case "2小时":
					lateDelay = "2";
					break;
				case "3小时":
					lateDelay = "3";

					break;

				default:
					break;
				}
				try {
					file = new FileOutputStream(realpath+"/time.properties");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				timeconfig.put("signinMin", signinMin);
				timeconfig.put("signoutMin", signoutMin);
				timeconfig.put("signinMax", signinMax);
				timeconfig.put("signoutMax", signoutMax);
				timeconfig.put("lateDelay", lateDelay);
				try {
					timeconfig.store(file, "时间配置修改");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				// System.out.println(signinMin);
				// System.out.println(signinMax);
				// System.out.println(signoutMin);
				// System.out.println(signoutMax);

			}
		});

		pNorth.setBounds(10, 60, 500, 50);
		;
		jpst.add(pNorth);
		jpst.add(timeset);

		jpst.add(time1);
		jpst.add(time2);
		jpst.add(time3);
		jpst.add(time4);
		jpst.add(time5);
		jpst.add(time6);
		jpst.add(time7);
		jpst.add(time8);
		jpst.add(time9);
		jpst.add(cbportList1);
		jpst.add(cbportList2);
		jpst.add(cbportList3);
		jpst.add(cbportList4);

		jpst.add(jbaudrate);
		jpst.add(jdatabit);
		jpst.add(jstopbit);
		jpst.add(jcheckbit);
		jpst.add(jsignin);
		jpst.add(jsignout);
		jpst.add(latelimite);

		jpst.add(t1);
		jpst.add(t2);
		jpst.add(t3);
		jpst.add(t4);
		jpst.add(t5);
		jpst.add(t6);

		/**************************************************
		 * 
		 ************************ 信息查询界面**************
		 * 
		 ***************************************************/

		jpmr = new JPanel(new BorderLayout()); // 信息查询

		JPanel jpmr_1 = new JPanel();
		jtmr_query = new JTextField(16);

		FlowLayout jpmr_flow = new FlowLayout();
		jpmr_1.setLayout(jpmr_flow);

		Object[][] msgr = new Object[1][8];
		jtamr_re = new JTable(msgr, Names_mr);

		scrollPane_mr = new JScrollPane(jtamr_re);
		jtamr_re.setRowHeight(50);
		jtamr_re.setEnabled(false);
		DefaultTableCellRenderer tcr_mr = new DefaultTableCellRenderer();
		tcr_mr.setHorizontalAlignment(SwingConstants.CENTER);
		jtamr_re.setDefaultRenderer(Object.class, tcr_mr);
		jtamr_re.setFont(new Font("宋体", 1, 15));

		JButton jpmr_query = new JButton("按姓名查询");
		JButton jpmr_queryall = new JButton("查询所有信息");
		JButton jpmr_queryid = new JButton("放卡查询");
		JLabel space2 = new JLabel("                 ");
		JButton jpmr_addem = new JButton("添加人员信息");

		jpmr_queryid.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				String rfid_id = simple.findCard();
				// String rfid_id ="4592181D";
				Employee em = service_e.findByRfid(rfid_id);
				if (em == null) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "查无此人", "提示", JOptionPane.INFORMATION_MESSAGE);

					return;
				}
				UpdateEmployeeFrame uef = new UpdateEmployeeFrame(em);
				uef.jtnew_box_2.setEditable(false);
				uef.jtnew_box_3_male.setEnabled(false);
				uef.jtnew_box_3_female.setEnabled(false);
				// uef.position.setEnabled(false);
				// uef.department.setEnabled(false);
				uef.baseBox.remove(uef.boxV6);
				
				// uef.baseBox.remove(uef.boxV7);
				uef.repaint();

			}

		});

		jpmr_query.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jpmr.remove(scrollPane_mr);

				String name = jtmr_query.getText();

				List<Employee> list = service_e.findByName(name);
				Object[][] xiba = new Object[list.size()][8];

				for (int i = 0; i < list.size(); i++) {
					Employee employee = list.get(i);
					Object[] str = { employee.getId(), employee.getName(), employee.getGender(), employee.getPosition(),
							employee.getDepartment(), employee.getRfid_id(), " ", " " };
					for (int j = 0; j < 8; j++) {
						xiba[i][j] = str[j];
					}
				}

				jtamr_re = new JTable(new JTableModel(xiba, my));
				jtamr_re.setFillsViewportHeight(true);
				TableCellRenderer buttonRenderer = new JTableButtonRenderer();
				jtamr_re.getColumn("修改").setCellRenderer(buttonRenderer);
				jtamr_re.getColumn("删除").setCellRenderer(buttonRenderer);
				jtamr_re.addMouseListener(new JTableButtonMouseListener(jtamr_re));

				scrollPane_mr = new JScrollPane(UIUtils.queryEmployee(jtamr_re));
				jpmr.add(scrollPane_mr, BorderLayout.CENTER);
				jpmr.repaint();
				jpmr.validate();
			}
		});

		jpmr_queryall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<Employee> list = service_e.getAll();
				Object[][] xiba = new Object[list.size()][8];
				for (int i = 0; i < list.size(); i++) {
					Employee employee = list.get(i);
					String[] str = { employee.getId(), employee.getName(), employee.getGender(), employee.getPosition(),
							employee.getDepartment(), employee.getRfid_id(), " ", " " };
					for (int j = 0; j < 8; j++) {
						xiba[i][j] = str[j];
					}
				}
				jpmr.remove(scrollPane_mr);

				jtamr_re = new JTable(new JTableModel(xiba, my));
				jtamr_re.setFillsViewportHeight(true);
				TableCellRenderer buttonRenderer = new JTableButtonRenderer();
				jtamr_re.getColumn("修改").setCellRenderer(buttonRenderer);
				jtamr_re.getColumn("删除").setCellRenderer(buttonRenderer);
				jtamr_re.addMouseListener(new JTableButtonMouseListener(jtamr_re));

				scrollPane_mr = new JScrollPane((UIUtils.queryEmployee(jtamr_re)));
				jpmr.add(scrollPane_mr, BorderLayout.CENTER);
				jpmr.repaint();
				jpmr.validate();

			}
		});

		jpmr_addem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new AddEmployeeFrame();
			}
		});
		jpmr_1.add(jpmr_addem, FlowLayout.LEFT);
		jpmr_1.add(space2, FlowLayout.LEFT);
		jpmr_1.add(jpmr_queryall, FlowLayout.LEFT);
		jpmr_1.add(jpmr_queryid, FlowLayout.LEFT);
		jpmr_1.add(jpmr_query, FlowLayout.LEFT);
		jpmr_1.add(jtmr_query, FlowLayout.LEFT);

		jpmr.add(scrollPane_mr, BorderLayout.CENTER);

		jpmr.add(jpmr_1, BorderLayout.SOUTH);

		/**************************************************
		 * 
		 ************************ 考勤界面**************
		 * 
		 ***************************************************/
		jpsign = new JPanel(new BorderLayout());

		JPanel jpsign_1 = new JPanel(new FlowLayout());
		JButton jbsign = new JButton("开始考勤");
		JButton jbStopSign = new JButton("停止考勤");
		time = new JLabel(new Date().toLocaleString());
		ct = new Currenttime();
		ct.start();

		Object[][] sign = new Object[1][4];
		String[] Namess = { "姓名", "签到时间", "签退时间", "操作提示" };
		jtasign = new JTable(sign, Namess);

		jtasign.setRowHeight(50);
		jtasign.setEnabled(false);
		scrollPane_sign = new JScrollPane(jtasign);

		jbsign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				thread = new MyThread();
				thread.start();

			}
		});

		jbStopSign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				simple.setRun(false);
			}
		});

		jpsign_1.add(jbStopSign, FlowLayout.LEFT);
		jpsign_1.add(jbsign, FlowLayout.LEFT);
		jpsign_1.add(time, FlowLayout.LEFT);

		jpsign.add(jpsign_1, BorderLayout.SOUTH);
		jpsign.add(scrollPane_sign, BorderLayout.CENTER);

		/**************************************************
		 * 
		 ************************ 考勤查询界面**************
		 * 
		 ***************************************************/

		jpsr = new JPanel(new BorderLayout()); // 考勤查询
		JPanel jpsr_1 = new JPanel();
		JPanel jpsr_2 = new JPanel();
		JLabel jbsr = new JLabel("好好学习天天向上！");

		JButton jpsr_id = new JButton("按工号查询");
		JButton jpsr_gettoday = new JButton("按日期查询");
		JButton jpsr_getall = new JButton("查询所有考勤信息");
		
		jtsr = new JTextField(12);

		year = new JComboBox<String>();
		mouth = new JComboBox<String>();
		day = new JComboBox<String>();

		date = new Date();
		gccalendar = new GregorianCalendar();
		gccalendar.setTime(date);
		int currentyear = gccalendar.get(Calendar.YEAR);

		for (int i = 2010; i < currentyear; i++) {
			year.addItem(i + 1 + "");
		}

		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				mouth.addItem("0" + (i + 1) + "");
			} else {
				mouth.addItem(i + 1 + "");
			}
		}

		for (int i = 0; i < 31; i++) {
			if (i < 9) {
				day.addItem("0" + (i + 1) + "");
			} else {
				day.addItem(i + 1 + "");
			}
		}

		FlowLayout jpsr_flow = new FlowLayout();
		Object[][] signr = new Object[1][4];

		jtasr_re = new JTable(signr, Names);

		jpsr_flow.setAlignment(FlowLayout.CENTER);
		jpsr_flow.setHgap(20);
		jpsr_flow.setVgap(20);
		jpsr_1.setLayout(jpsr_flow);
		
		jpsr_id.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = jtsr.getText();
				jpsr.remove(scrollPane);

				scrollPane = new JScrollPane(UIUtils.refresh3(jtamr_re, id));
				jpsr.add(scrollPane, BorderLayout.CENTER);
				jpsr.repaint();
				jpsr.validate();
			}
		});

		jpsr_gettoday.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String oneDay = (String) year.getSelectedItem() + (String) mouth.getSelectedItem()
						+ (String) day.getSelectedItem();

				jpsr.remove(scrollPane);

				scrollPane = new JScrollPane(UIUtils.refresh(jtamr_re, oneDay));
				jpsr.add(scrollPane, BorderLayout.CENTER);
				jpsr.repaint();
				jpsr.validate();
			}
		});

		jpsr_getall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				List<Attence> list = service.getAll();
				Object[][] xiba = new Object[list.size()][4];
				for (int i = 0; i < list.size(); i++) {
					Attence attence = list.get(i);
					String[] str = { attence.getName(), new Date(attence.getSignintime()).toLocaleString(),
							new Date(attence.getSignouttime()).toLocaleString(),
							attence.getResult() };
					if (str[2].equals(new Date(0).toLocaleString())) {
						str[2] = "无";
					}
					for (int j = 0; j < 4; j++) {
						xiba[i][j] = str[j];
					}
				}
				jpsr.remove(scrollPane);
				jtasr_re = new JTable(xiba, Names);
				jtasr_re.setRowHeight(50);
				jtasr_re.setEnabled(false);
				DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
				tcr.setHorizontalAlignment(SwingConstants.CENTER);
				jtasr_re.setDefaultRenderer(Object.class, tcr);
				jtasr_re.setFont(new Font("宋体", 1, 15));
				scrollPane = new JScrollPane(jtasr_re);
				jpsr.add(scrollPane, BorderLayout.CENTER);
				jpsr.repaint();
				jpsr.validate();

			}
		});
		
		jpsr_1.add(jtsr);
		jpsr_1.add(jpsr_id);
		
		jpsr_1.add(year);
		jpsr_1.add(mouth);
		jpsr_1.add(day);
		jpsr_1.add(jpsr_gettoday);
		jpsr_1.add(jpsr_getall);

		jpsr_2.add(jbsr);
		jtasr_re.setRowHeight(50);
		jtasr_re.setEnabled(false);
		scrollPane = new JScrollPane(jtasr_re);
		jpsr.add(jpsr_2, BorderLayout.NORTH);
		jpsr.add(jpsr_1, BorderLayout.SOUTH);
		jpsr.add(scrollPane, BorderLayout.CENTER);

		/********************************************************/

		p.add("    考勤    ", jpsign);
		p.add("信息查询修改", jpmr);
		p.add("考勤查询", jpsr);

		p.add("系统设置", jpst);
		p.setUI(myui);
		
		setBounds(350, 150, 1080, 720);
		setVisible(true);
		add(p, BorderLayout.CENTER);

		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static class JTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMN_NAMES = Names_mr;

		private static final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, String.class, String.class,
				String.class, String.class, String.class, JButton.class, JButton.class };

		Object[][] xiba;
		MyFrame1 my;

		public JTableModel(Object[][] xiba, MyFrame1 my) {
			this.xiba = xiba;
			this.my = my;

		}

		@Override
		public int getColumnCount() {

			return COLUMN_NAMES.length;

		}

		@Override
		public int getRowCount() {

			return xiba.length;

		}

		@Override
		public String getColumnName(int columnIndex) {

			return COLUMN_NAMES[columnIndex];

		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {

			return COLUMN_TYPES[columnIndex];

		}

		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex) {

			if (columnIndex == 6) {
				final JButton button = new JButton(COLUMN_NAMES[columnIndex]);

				button.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						EmployeeService service = new EmployeeServiceImpl();
						Employee em = service.find(xiba[rowIndex][0].toString());
						new UpdateEmployeeFrame(em);

					}

				});

				return button;
			}
			if (columnIndex == 7) {
				final JButton button = new JButton(COLUMN_NAMES[columnIndex]);

				button.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {

						EmployeeService service = new EmployeeServiceImpl();
						int n = JOptionPane.showConfirmDialog(null, "确定删除：" + xiba[rowIndex][1] + "  ?", "提醒",
								JOptionPane.YES_NO_OPTION);

						if (n == 0) {

							service.delete(xiba[rowIndex][0].toString());

							JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button),
									"删除成功,要想看最新列表，请重新查询- -", "提示", JOptionPane.INFORMATION_MESSAGE);
						}
					}

				});

				return button;
			}
			if (columnIndex != 6) {
				return xiba[rowIndex][columnIndex];
			} else
				return "";

		}

	}

	private static class JTableButtonRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JButton button = (JButton) value;

			if (isSelected) {

				button.setForeground(table.getSelectionForeground());

				button.setBackground(table.getSelectionBackground());

			} else {

				button.setForeground(table.getForeground());

				button.setBackground(UIManager.getColor("Button.background"));

			}

			return button;

		}

	}

	private static class JTableButtonMouseListener extends MouseAdapter {

		private final JTable table;

		public JTableButtonMouseListener(JTable table) {

			this.table = table;

		}

		public void mouseClicked(MouseEvent e) {

			int column = table.getColumnModel().getColumnIndexAtX(e.getX());

			int row = e.getY() / table.getRowHeight();

			if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {

				Object value = table.getValueAt(row, column);

				if (value instanceof JButton) {

					((JButton) value).doClick();

				}

			}

		}

	}

}
