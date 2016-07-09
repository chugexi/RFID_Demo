package com.ly.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.ly.domain.Attence;
import com.ly.domain.Employee;
import com.ly.service.EmployeeService;
import com.ly.service.SignService;
import com.ly.service.impl.EmployeeServiceImpl;
import com.ly.service.impl.SignServiceImpl;
import com.ly.test.Test2;
import com.ly.thread.MyThread;
import com.ly.utils.UIUtils;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class RFID_UI2 {

	public static void main(String[] args) {
		MyFrame1 my1 = new MyFrame1();
	}
}

class newFrame extends JFrame {
	Employee em;
	JTextField tReadID;

	String gender;

	JComboBox<String> position;
	JComboBox<String> department;

	String[] positions = { "经理", "秘书", "总监", "接线员", "跟班" };
	String[] departments = { "人力资源", "市场调查", "后勤管理", "公关", "市场营销" };
	
	 JTextField jtnew_box_1;
	 JTextField jtnew_box_2;
	 JRadioButton jtnew_box_3_male;
	 JRadioButton jtnew_box_3_female;

	public newFrame(Employee em) {
		this.em = em;
		setLayout(new FlowLayout());
		Box baseBox;
		Box boxV1, boxV2, boxV3, boxV4, boxV5, boxV6, boxV7;
		// jpnew_box.setLayout(new BoxLayout(jpnew_box, BoxLayout.Y_AXIS));

		JButton bReadID = new JButton("读卡号");
		JLabel lbReadID = new JLabel("卡号:");
		tReadID = new JTextField(16);
		bReadID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SimpleRW simple = SimpleRW.getInstance();
				String rfid_id = simple.findCard();
				tReadID.setText(rfid_id);
			}
		});

		bReadID.setPreferredSize(new Dimension(80, 30));

		tReadID.setEditable(false);
		JLabel space = new JLabel("                                ");
		// jpnew_box_6.add(space);
		// jpnew_box_6.add(tReadID);
		// jpnew_box_6.add(bReadID);

		jtnew_box_1 = new JTextField(12);
		jtnew_box_1.setText(em.getId());
		jtnew_box_1.setEditable(false);
		JLabel jlnew_box_1 = new JLabel("工号:");

		jtnew_box_2 = new JTextField(12);
		jtnew_box_2.setText(em.getName());
		JLabel jlnew_box_2 = new JLabel("姓名:");

		jtnew_box_3_male = new JRadioButton("男");
		jtnew_box_3_female = new JRadioButton("女");
		ButtonGroup gender_group = new ButtonGroup();
		jtnew_box_3_male.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gender = "男";
			}
		});
		jtnew_box_3_female.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gender = "女";
			}
		});
		gender_group.add(jtnew_box_3_male);
		gender_group.add(jtnew_box_3_female);
		JLabel jlnew_box_3 = new JLabel("性别:");
		gender = em.getGender();
		if (gender.equals("男")) {
			jtnew_box_3_male.setSelected(true);
		}
		if (gender.equals("女")) {
			jtnew_box_3_female.setSelected(true);
		}

		position = new JComboBox<String>();
		for (int i = 0; i < positions.length; i++) {
			position.addItem(positions[i]);
		}
		JLabel jlnew_box_4 = new JLabel("职位:");
		String pos = em.getPosition();
		for (int i = 0; i < positions.length; i++) {
			if (position.getItemAt(i).equals(pos)) {
				position.setSelectedIndex(i);
			}
		}

		department = new JComboBox<String>();
		for (int i = 0; i < departments.length; i++) {
			department.addItem(departments[i]);
		}
		JLabel jlnew_box_5 = new JLabel("部门:");
		String dep = em.getDepartment();
		for (int i = 0; i < departments.length; i++) {
			if (department.getItemAt(i).equals(dep)) {
				department.setSelectedIndex(i);

			}
		}

		boxV1 = Box.createHorizontalBox();
		boxV2 = Box.createHorizontalBox();
		boxV3 = Box.createHorizontalBox();
		boxV4 = Box.createHorizontalBox();
		boxV5 = Box.createHorizontalBox();
		boxV6 = Box.createHorizontalBox();
		boxV7 = Box.createHorizontalBox();

		boxV1.add(jlnew_box_1);
		boxV1.add(Box.createHorizontalStrut(8));
		boxV1.add(jtnew_box_1);

		boxV2.add(jlnew_box_2);
		boxV2.add(Box.createHorizontalStrut(8));
		boxV2.add(jtnew_box_2);

		boxV3.add(jlnew_box_3);
		boxV3.add(Box.createHorizontalStrut(8));
		boxV3.add(jtnew_box_3_male);
		boxV3.add(Box.createHorizontalStrut(8));
		boxV3.add(jtnew_box_3_female);
		boxV3.add(Box.createHorizontalStrut(8));
		boxV3.add(space);

		boxV4.add(jlnew_box_4);
		boxV4.add(Box.createHorizontalStrut(8));
		boxV4.add(position);

		boxV5.add(jlnew_box_5);
		boxV5.add(Box.createHorizontalStrut(8));
		boxV5.add(department);

		JButton jbmc_update = new JButton("修改");
		boxV6.add(jbmc_update);
		boxV6.add(Box.createHorizontalStrut(8));
		boxV6.add(bReadID);

		boxV7.add(lbReadID);
		boxV7.add(Box.createHorizontalStrut(8));
		boxV7.add(tReadID);

		baseBox = Box.createVerticalBox();
		baseBox.add(boxV1);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV2);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV3);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV4);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV5);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV7);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV6);

		jbmc_update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Employee employee = new Employee();
				employee.setId(jtnew_box_1.getText());
				employee.setName(jtnew_box_2.getText());
				employee.setGender(gender);
				employee.setPosition((String) position.getSelectedItem());
				employee.setDepartment((String) department.getSelectedItem());
				employee.setRfid_id(tReadID.getText());

				EmployeeService service = new EmployeeServiceImpl();

				if (employee.getId().trim().equals("") || employee.getName().trim().equals("")
						|| employee.getGender().trim().equals("") || employee.getPosition().trim().equals("")
						|| employee.getDepartment().trim().equals("")) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "修改失败", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					service.update(employee);
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "修改失败", "错误", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		add(baseBox);

		setBounds(500, 300, 430, 330);
		setVisible(true);

		validate();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}

class addEmployee extends JFrame {

	JTextField tReadID;

	String gender;

	JComboBox<String> position;
	JComboBox<String> department;

	String[] positions = { "经理", "秘书", "总监", "接线员", "跟班" };
	String[] departments = { "人力资源", "市场调查", "后勤管理", "公关", "市场营销" };

	public addEmployee() {
		setLayout(new FlowLayout());
		Box baseBox;
		Box boxV1, boxV2, boxV3, boxV4, boxV5, boxV6, boxV7;
		// jpnew_box.setLayout(new BoxLayout(jpnew_box, BoxLayout.Y_AXIS));

		JButton bReadID = new JButton("读卡号");
		JLabel lbReadID = new JLabel("卡号:");
		tReadID = new JTextField(16);
		bReadID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SimpleRW simple = SimpleRW.getInstance();
				String rfid_id = simple.findCard();
				tReadID.setText(rfid_id);
			}
		});

		bReadID.setPreferredSize(new Dimension(80, 30));

		tReadID.setEditable(false);
		JLabel space = new JLabel("                                ");
		// jpnew_box_6.add(space);
		// jpnew_box_6.add(tReadID);
		// jpnew_box_6.add(bReadID);

		final JTextField jtnew_box_1 = new JTextField(12);
		JLabel jlnew_box_1 = new JLabel("工号:");

		final JTextField jtnew_box_2 = new JTextField(12);
		JLabel jlnew_box_2 = new JLabel("姓名:");

		final JRadioButton jtnew_box_3_male = new JRadioButton("男");
		final JRadioButton jtnew_box_3_female = new JRadioButton("女");
		ButtonGroup gender_group = new ButtonGroup();
		jtnew_box_3_male.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gender = "男";
			}
		});
		jtnew_box_3_female.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gender = "女";
			}
		});
		gender_group.add(jtnew_box_3_male);
		gender_group.add(jtnew_box_3_female);
		JLabel jlnew_box_3 = new JLabel("性别:");

		position = new JComboBox<String>();
		for (int i = 0; i < positions.length; i++) {
			position.addItem(positions[i]);
		}
		JLabel jlnew_box_4 = new JLabel("职位:");

		department = new JComboBox<String>();
		for (int i = 0; i < departments.length; i++) {
			department.addItem(departments[i]);
		}
		JLabel jlnew_box_5 = new JLabel("部门:");

		boxV1 = Box.createHorizontalBox();
		boxV2 = Box.createHorizontalBox();
		boxV3 = Box.createHorizontalBox();
		boxV4 = Box.createHorizontalBox();
		boxV5 = Box.createHorizontalBox();
		boxV6 = Box.createHorizontalBox();
		boxV7 = Box.createHorizontalBox();

		boxV1.add(jlnew_box_1);
		boxV1.add(Box.createHorizontalStrut(8));
		boxV1.add(jtnew_box_1);

		boxV2.add(jlnew_box_2);
		boxV2.add(Box.createHorizontalStrut(8));
		boxV2.add(jtnew_box_2);

		boxV3.add(jlnew_box_3);
		boxV3.add(Box.createHorizontalStrut(8));
		boxV3.add(jtnew_box_3_male);
		boxV3.add(Box.createHorizontalStrut(8));
		boxV3.add(jtnew_box_3_female);
		boxV3.add(Box.createHorizontalStrut(8));
		boxV3.add(space);

		boxV4.add(jlnew_box_4);
		boxV4.add(Box.createHorizontalStrut(8));
		boxV4.add(position);

		boxV5.add(jlnew_box_5);
		boxV5.add(Box.createHorizontalStrut(8));
		boxV5.add(department);

		JButton jbmc_add = new JButton("添加");
		boxV6.add(jbmc_add);
		boxV6.add(Box.createHorizontalStrut(8));
		boxV6.add(bReadID);

		boxV7.add(lbReadID);
		boxV7.add(Box.createHorizontalStrut(8));
		boxV7.add(tReadID);

		baseBox = Box.createVerticalBox();
		baseBox.add(boxV1);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV2);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV3);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV4);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV5);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV7);
		baseBox.add(Box.createVerticalStrut(10));
		baseBox.add(boxV6);

		jbmc_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Employee employee = new Employee();
				employee.setId(jtnew_box_1.getText());
				employee.setName(jtnew_box_2.getText());
				employee.setGender(gender);
				employee.setPosition((String) position.getSelectedItem());
				employee.setDepartment((String) department.getSelectedItem());
				
				System.out.println(tReadID.getText());
				
				if (!tReadID.getText().trim().equals("")) {
					employee.setRfid_id(tReadID.getText());
				}
				EmployeeService service = new EmployeeServiceImpl();
				try {
					if (employee.getId().trim().equals("") || employee.getName().trim().equals("")
							|| employee.getGender().trim().equals("") || employee.getPosition().trim().equals("")
							|| employee.getDepartment().trim().equals("")) {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "添加失败", "错误", JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (Exception exx) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "请填写完整信息", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				try {
					service.add(employee);
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "添加失败", "错误", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		add(baseBox);

		setBounds(500, 300, 430, 330);
		setVisible(true);

		validate();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}

class MyFrame1 extends JFrame {
	EmployeeService service_e = new EmployeeServiceImpl();
	SignService service = new SignServiceImpl();

	private static Properties timeconfig = new Properties();
	String path = MyFrame1.class.getClassLoader().getResource("time.properties").toString();
	String path2 = path.substring(6, path.length());

	static {
		try {
			timeconfig.load(MyFrame1.class.getClassLoader().getResourceAsStream("time.properties"));
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
	JPanel jpsign;

	String[] Names = { "姓名", "签到时间", "签退时间", "操作时间" };
	static String[] Names_mr = { "工号", "姓名", "性别", "职位", "部门", "卡号", "修改", "删除" };
	JTable jtasr_re;
	JTable jtamr_re;
	JTable jtasign;

	JScrollPane scrollPane;
	JScrollPane scrollPane_sign;
	JScrollPane scrollPane_mr;
	JTextField jtmr_query;

	JTextField jtst;

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
	
	Thread thread;
	
	int[] portSet = { 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE };
	FileOutputStream file;

	SimpleRW simple = SimpleRW.getInstance();

	public MyFrame1() {
		
		String signinMin = timeconfig.getProperty("signinMin");
		String signinMax = timeconfig.getProperty("signinMax");
		String signoutMin = timeconfig.getProperty("signoutMin");
		String signoutMax = timeconfig.getProperty("signoutMax");

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
		String[] minute = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
				"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
				"32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48",
				"49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" };

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

		timeset.setBounds(620, 250, 120, 30);

		timeset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String signinMin = (String) time1.getSelectedItem() + ":" + (String) time2.getSelectedItem() + ":00";
				String signoutMin = (String) time3.getSelectedItem() + ":" + (String) time4.getSelectedItem() + ":00";
				String signinMax = (String) time5.getSelectedItem() + ":" + (String) time6.getSelectedItem() + ":00";
				String signoutMax = (String) time7.getSelectedItem() + ":" + (String) time8.getSelectedItem() + ":00";

				try {
					file = new FileOutputStream(path2);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				timeconfig.put("signinMin", signinMin);
				timeconfig.put("signoutMin", signoutMin);
				timeconfig.put("signinMax", signinMax);
				timeconfig.put("signoutMax", signoutMax);
				try {
					timeconfig.store(file, "时间配置修改");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
//				System.out.println(signinMin);
//				System.out.println(signinMax);
//				System.out.println(signoutMin);
//				System.out.println(signoutMax);

			}
		});
		jpst.add(timeset);

		jpst.add(time1);
		jpst.add(time2);
		jpst.add(time3);
		jpst.add(time4);
		jpst.add(time5);
		jpst.add(time6);
		jpst.add(time7);
		jpst.add(time8);

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
		jpst.add(t1);
		jpst.add(t2);
		jpst.add(t3);
		jpst.add(t4);
		jpst.add(t5);
		jpst.add(t6);

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
//				simple = SimpleRW.getInstance((String) cbportList.getSelectedItem(), my);
				System.out.println((String) cbportList.getSelectedItem());
				simple.openPort((String) cbportList.getSelectedItem(),my);

			}
		});
		pNorth.add(bOpenSPort);
		pNorth.add(bCloseSPort);
		lSportStatus = new JLabel("    ");
		pNorth.add(lSportStatus);
		add(pNorth, BorderLayout.NORTH);

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

		JButton jpmr_query = new JButton("查询");
		JButton jpmr_queryall = new JButton("查询所有信息");
		JLabel space2 = new JLabel("                 ");
		JButton jpmr_addem = new JButton("添加");

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
				new addEmployee();
			}
		});
		jpmr_1.add(jpmr_addem, FlowLayout.LEFT);
		jpmr_1.add(space2, FlowLayout.LEFT);
		jpmr_1.add(jpmr_queryall, FlowLayout.LEFT);
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
		JLabel jlsign1 = new JLabel(new Date().toLocaleString());

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
		jpsign_1.add(jlsign1, FlowLayout.LEFT);
		
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
		JLabel jbsr = new JLabel("这里是不是应该有点东西？");
		JButton jpsr_gettoday = new JButton("查询今日考勤信息");
		JButton jpsr_getall = new JButton("查询所有考勤信息");

		FlowLayout jpsr_flow = new FlowLayout();
		Object[][] signr = new Object[1][4];

		jtasr_re = new JTable(signr, Names);

		jpsr_flow.setAlignment(FlowLayout.CENTER);
		jpsr_flow.setHgap(20);
		jpsr_flow.setVgap(20);
		jpsr_1.setLayout(jpsr_flow);

		jpsr_gettoday.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jpsr.remove(scrollPane);

				scrollPane = new JScrollPane(UIUtils.refresh(jtamr_re));
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
							new Date(attence.getHandletime()).toLocaleString() };
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

		p.add("考勤", jpsign);
		p.add("信息查询", jpmr);
		p.add("考勤查询", jpsr);

		p.add("系统设置", jpst);
		p.setUI(myui);
		setSize(1080, 720);
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
						new newFrame(em);

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
