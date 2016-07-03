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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BoxLayout;
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
import javax.swing.table.TableColumn;

import com.ly.domain.Attence;
import com.ly.domain.Employee;
import com.ly.service.EmployeeService;
import com.ly.service.SignService;
import com.ly.service.impl.EmployeeServiceImpl;
import com.ly.service.impl.SignServiceImpl;
import com.ly.thread.MyThread;
import com.ly.utils.UIUtils;

import gnu.io.CommPortIdentifier;

public class RFID_UI2 {

	public static void main(String[] args) {
		MyFrame1 my1 = new MyFrame1();
	}
}

class newFrame extends JFrame {

//	JTextField id;
//	JTextField name;
//	JTextField gender;
	
	
	JTextField tReadID;

	public newFrame(Employee em) {
		setLayout(new BorderLayout());
		JPanel jpnew_box = new JPanel();
		jpnew_box.setLayout(new BoxLayout(jpnew_box, BoxLayout.Y_AXIS));

		JPanel jpnew_box_6 = new JPanel();
		JButton bReadID = new JButton("读卡号");
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
		
		tReadID.setEnabled(false);
		JLabel space = new JLabel("                 ");
		jpnew_box_6.add(space);
		jpnew_box_6.add(tReadID);
		jpnew_box_6.add(bReadID);

		JPanel jpnew_box_1 = new JPanel();
		final JTextField jtnew_box_1 = new JTextField(16);
		jtnew_box_1.setEnabled(false);
		jtnew_box_1.setText(em.getId());
		JLabel jlnew_box_1 = new JLabel("工号");		
		jpnew_box_1.add(jtnew_box_1);
		jpnew_box_1.add(jlnew_box_1);
		// jpmc_box_1.setPreferredSize(new Dimension(8, 20));

		JPanel jpnew_box_2 = new JPanel();
		final JTextField jtnew_box_2 = new JTextField(16);
		jtnew_box_2.setText(em.getName());
		JLabel jlnew_box_2 = new JLabel("姓名");
		jpnew_box_2.add(jtnew_box_2);
		jpnew_box_2.add(jlnew_box_2);
		// jpmc_box_2.setPreferredSize(new Dimension(8, 20));

		JPanel jpnew_box_3 = new JPanel();
		final JTextField jtnew_box_3 = new JTextField(16);
		jtnew_box_3.setText(em.getGender());
		JLabel jlnew_box_3 = new JLabel("性别");
		jpnew_box_3.add(jtnew_box_3);
		jpnew_box_3.add(jlnew_box_3);
		// jpmc_box_3.setPreferredSize(new Dimension(8, 20));

		JPanel jpnew_box_4 = new JPanel();
		final JTextField jtnew_box_4 = new JTextField(16);
		jtnew_box_4.setText(em.getPosition());
		JLabel jlnew_box_4 = new JLabel("职位");
		jpnew_box_4.add(jtnew_box_4);
		jpnew_box_4.add(jlnew_box_4);

		JPanel jpnew_box_5 = new JPanel();
		final JTextField jtnew_box_5 = new JTextField(16);
		jtnew_box_5.setText(em.getDepartment());
		JLabel jlnew_box_5 = new JLabel("部门");
		jpnew_box_5.add(jtnew_box_5);
		jpnew_box_5.add(jlnew_box_5);

		jpnew_box.add(jpnew_box_6);
		jpnew_box.add(jpnew_box_1);
		jpnew_box.add(jpnew_box_2);
		jpnew_box.add(jpnew_box_3);
		jpnew_box.add(jpnew_box_4);
		jpnew_box.add(jpnew_box_5);

		JButton jbmc_add = new JButton("修改");
		
		jbmc_add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Employee employee = new Employee();
				employee.setId(jtnew_box_1.getText());
				employee.setName(jtnew_box_2.getText());
				employee.setGender(jtnew_box_3.getText());
				employee.setPosition(jtnew_box_4.getText());
				employee.setDepartment(jtnew_box_5.getText());
				employee.setRfid_id(tReadID.getText());
				
				EmployeeService service = new EmployeeServiceImpl();
				
				
				if(employee.getId().trim().equals("")||employee.getName().trim().equals("")||employee.getGender().trim().equals("")||employee.getPosition().trim().equals("")||employee.getDepartment().trim().equals("")){
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
		add(jpnew_box,BorderLayout.CENTER);
		add(jbmc_add, BorderLayout.SOUTH);

		setSize(430, 330);
		setVisible(true);

		validate();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}

class addEmployee extends JFrame {

	JTextField id;
	JTextField name;
	JTextField gender;

	public addEmployee() {
		setLayout(new BorderLayout());
		JPanel jpnew_box = new JPanel();
		jpnew_box.setLayout(new BoxLayout(jpnew_box, BoxLayout.Y_AXIS));

		JPanel jpnew_box_6 = new JPanel();
		JButton bReadID = new JButton("读卡号");
		
		bReadID.setPreferredSize(new Dimension(80, 30));
		JTextField tReadID = new JTextField(16);
		tReadID.setEnabled(false);
		JLabel space = new JLabel("                 ");
		jpnew_box_6.add(space);
		jpnew_box_6.add(tReadID);
		jpnew_box_6.add(bReadID);

		JPanel jpnew_box_1 = new JPanel();
		final JTextField jtnew_box_1 = new JTextField(16);
		JLabel jlnew_box_1 = new JLabel("工号");
		jpnew_box_1.add(jtnew_box_1);
		jpnew_box_1.add(jlnew_box_1);
		// jpmc_box_1.setPreferredSize(new Dimension(8, 20));

		JPanel jpnew_box_2 = new JPanel();
		final JTextField jtnew_box_2 = new JTextField(16);
		JLabel jlnew_box_2 = new JLabel("姓名");
		jpnew_box_2.add(jtnew_box_2);
		jpnew_box_2.add(jlnew_box_2);
		// jpmc_box_2.setPreferredSize(new Dimension(8, 20));

		JPanel jpnew_box_3 = new JPanel();
		final JTextField jtnew_box_3 = new JTextField(16);
		JLabel jlnew_box_3 = new JLabel("性别");
		jpnew_box_3.add(jtnew_box_3);
		jpnew_box_3.add(jlnew_box_3);
		// jpmc_box_3.setPreferredSize(new Dimension(8, 20));

		JPanel jpnew_box_4 = new JPanel();
		final JTextField jtnew_box_4 = new JTextField(16);
		JLabel jlnew_box_4 = new JLabel("职位");
		jpnew_box_4.add(jtnew_box_4);
		jpnew_box_4.add(jlnew_box_4);

		JPanel jpnew_box_5 = new JPanel();
		final JTextField jtnew_box_5 = new JTextField(16);
		JLabel jlnew_box_5 = new JLabel("部门");
		jpnew_box_5.add(jtnew_box_5);
		jpnew_box_5.add(jlnew_box_5);

		jpnew_box.add(jpnew_box_6);
		jpnew_box.add(jpnew_box_1);
		jpnew_box.add(jpnew_box_2);
		jpnew_box.add(jpnew_box_3);
		jpnew_box.add(jpnew_box_4);
		jpnew_box.add(jpnew_box_5);

		JButton jbmc_add = new JButton("添加");
		
		jbmc_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Employee employee = new Employee();
				employee.setId(jtnew_box_1.getText());
				employee.setName(jtnew_box_2.getText());
				employee.setGender(jtnew_box_3.getText());
				employee.setPosition(jtnew_box_4.getText());
				employee.setDepartment(jtnew_box_5.getText());
				EmployeeService service = new EmployeeServiceImpl();
				if(employee.getId().trim().equals("")||employee.getName().trim().equals("")||employee.getGender().trim().equals("")||employee.getPosition().trim().equals("")||employee.getDepartment().trim().equals("")){
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "添加失败", "错误", JOptionPane.ERROR_MESSAGE);
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
		
		add(jpnew_box,BorderLayout.CENTER);
		add(jbmc_add, BorderLayout.SOUTH);

		setSize(430, 330);
		setVisible(true);

		validate();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}
class MyFrame1 extends JFrame {
	EmployeeService service_e = new EmployeeServiceImpl();
	SignService service = new SignServiceImpl();

	JTabbedPane p;


	//串口相关
	JComboBox<String> cbportList;
	String portname;
	JButton bOpenSPort;
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
	
	SimpleRW simple ;

	public MyFrame1() {
		final MyFrame1 my = this;
		
		

		getContentPane().setLayout(new BorderLayout());
		p = new JTabbedPane(JTabbedPane.BOTTOM);

		

		jpsr = new JPanel(new BorderLayout());
		jpmr = new JPanel(new BorderLayout());

		jpst = new JPanel(new BorderLayout());

		/**************************************************
		 * 
		 ************************ shezhi **************
		 * 
		 ***************************************************/

		jtst = new JTextField(16);
		JButton jbst = new JButton("sdfsdfs");

		jbst.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UIDemo uuuu = UIDemo.getInstance(my);
				uuuu.run();

			}
		});

		jpst.add(jtst, BorderLayout.NORTH);
		jpst.add(jbst, BorderLayout.CENTER);

		/**************************************************
		 * 
		 ************************ 串口选择**************
		 * 
		 ***************************************************/

		JPanel pNorth = new JPanel();
		cbportList = new JComboBox<String>();
		cbportList.addItem("COM9");
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
		bOpenSPort.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				simple = SimpleRW.getInstance((String)cbportList.getSelectedItem(),my);
				System.out.println((String)cbportList.getSelectedItem());
				simple.openPort();
	
			}
		});
		pNorth.add(bOpenSPort);
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
		JLabel jlsign1 = new JLabel("啊啊啊啊啊啊啊啊啊啊啊？");
		
		Object[][] sign = new Object[1][4];

		jtasign = new JTable(sign, Names);
		
		jtasign.setRowHeight(50);
		jtasign.setEnabled(false);
		scrollPane_sign = new JScrollPane(jtasign);
		
		
		jbsign.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Thread thread = new MyThread();
				thread.start();
		
			}
		});
		
		
		jpsign_1.add(jbsign,FlowLayout.LEFT);
		jpsign_1.add(jlsign1,FlowLayout.LEFT);
		jpsign.add(jpsign_1,BorderLayout.SOUTH);
		jpsign.add(scrollPane_sign,BorderLayout.CENTER);
		

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
