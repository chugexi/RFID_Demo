package com.ly.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.SwingConstants;


import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.ly.domain.Attence;
import com.ly.domain.Employee;
import com.ly.service.EmployeeService;
import com.ly.service.SignService;
import com.ly.service.impl.EmployeeServiceImpl;
import com.ly.service.impl.SignServiceImpl;


public class RFID_UI {
	static String[] Names = { "����", "ǩ��ʱ��", "ǩ��ʱ��", "����ʱ��" };
	static String[] Names_mr = { "����", "�Ա�", "ְλ", "����", "����" };
	static JTable jtasr_re;
	static JTable jtamr_re;
	static JScrollPane scrollPane;
	static JScrollPane scrollPane_mr;
	static JTextField jtmr_query;
	static JPanel jpsr;
	static JPanel jpmr;
	static JPanel jpmc;
	static JPanel jpcr;
	

	
	static JTextField jtcr;
	
//	public RFID_UI(String id){
//		this.rfid_id = id ;
//	}
//	
//	public RFID_UI(){
//		super();
//	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		JTabbedPane p;
		MyPaneUI myui = new MyPaneUI();

		

		/**************************************************
		 * 
		 ************************ ��Ϣ��ѯ����**************
		 * 
		 ***************************************************/

		jpmr = new JPanel(new BorderLayout()); // ��Ϣ��ѯ

		JPanel jpmr_1 = new JPanel();
		jtmr_query = new JTextField(16);
		FlowLayout jpmr_flow = new FlowLayout();
		jpmr_1.setLayout(jpmr_flow);

		Object[][] playerInfo = new Object[1][5];
		jtamr_re = new JTable(playerInfo, Names_mr);
		scrollPane_mr = new JScrollPane(jtamr_re);
		jtamr_re.setRowHeight(50);
		jtamr_re.setEnabled(false);
		DefaultTableCellRenderer tcr_mr = new DefaultTableCellRenderer();
		tcr_mr.setHorizontalAlignment(SwingConstants.CENTER);
		jtamr_re.setDefaultRenderer(Object.class, tcr_mr);
		jtamr_re.setFont(new Font("����", 1, 15));

		JButton jpmr_query = new JButton("��ѯ");
		JButton jpmr_queryall = new JButton("��ѯ������Ϣ");

		jpmr_query.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String name = jtmr_query.getText();
				EmployeeService service_e = new EmployeeServiceImpl();

				List<Employee> list = service_e.findByName(name);
				Object[][] xiba = new Object[list.size()][5];
				for (int i = 0; i < list.size(); i++) {
					Employee employee = list.get(i);
					String[] str = { employee.getName(), employee.getGender(), employee.getPosition(),
							employee.getDepartment(), employee.getRfid_id() };
					for (int j = 0; j < 4; j++) {
						xiba[i][j] = str[j];
					}
				}
				jpmr.remove(scrollPane_mr);
				jtamr_re = new JTable(xiba, Names_mr);
				jtamr_re.setRowHeight(50);
				jtamr_re.setEnabled(false);
				DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
				tcr.setHorizontalAlignment(SwingConstants.CENTER);
				jtamr_re.setDefaultRenderer(Object.class, tcr);
				jtamr_re.setFont(new Font("����", 1, 15));

				TableColumn name_col = jtamr_re.getColumnModel().getColumn(0);
				TableColumn sex_col = jtamr_re.getColumnModel().getColumn(1);
				TableColumn pos_col = jtamr_re.getColumnModel().getColumn(2);
				TableColumn dep_col = jtamr_re.getColumnModel().getColumn(3);

				name_col.setPreferredWidth(100);
				name_col.setMaxWidth(100);
				name_col.setMinWidth(100);

				sex_col.setPreferredWidth(100);
				sex_col.setMaxWidth(100);
				sex_col.setMinWidth(100);

				pos_col.setPreferredWidth(150);
				pos_col.setMaxWidth(150);
				pos_col.setMinWidth(150);

				dep_col.setPreferredWidth(200);
				dep_col.setMaxWidth(200);
				dep_col.setMinWidth(200);

				scrollPane_mr = new JScrollPane(jtamr_re);
				jpmr.add(scrollPane_mr, BorderLayout.CENTER);
				jpmr.repaint();
				jpmr.validate();
			}
		});

		jpmr_queryall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EmployeeService service_e = new EmployeeServiceImpl();
				List<Employee> list = service_e.getAll();
				Object[][] xiba = new Object[list.size()][5];
				for (int i = 0; i < list.size(); i++) {
					Employee employee = list.get(i);
					String[] str = { employee.getName(), employee.getGender(), employee.getPosition(),
							employee.getDepartment(), employee.getRfid_id() };
					for (int j = 0; j < 4; j++) {
						xiba[i][j] = str[j];
					}
				}
				jpmr.remove(scrollPane_mr);
				jtamr_re = new JTable(xiba, Names_mr);
				jtamr_re.setRowHeight(50);
				jtamr_re.setEnabled(false);
				DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
				tcr.setHorizontalAlignment(SwingConstants.CENTER);
				jtamr_re.setDefaultRenderer(Object.class, tcr);
				jtamr_re.setFont(new Font("����", 1, 15));

				TableColumn name_col = jtamr_re.getColumnModel().getColumn(0);
				TableColumn sex_col = jtamr_re.getColumnModel().getColumn(1);
				TableColumn pos_col = jtamr_re.getColumnModel().getColumn(2);
				TableColumn dep_col = jtamr_re.getColumnModel().getColumn(3);

				name_col.setPreferredWidth(100);
				name_col.setMaxWidth(100);
				name_col.setMinWidth(100);

				sex_col.setPreferredWidth(100);
				sex_col.setMaxWidth(100);
				sex_col.setMinWidth(100);

				pos_col.setPreferredWidth(150);
				pos_col.setMaxWidth(150);
				pos_col.setMinWidth(150);

				dep_col.setPreferredWidth(200);
				dep_col.setMaxWidth(200);
				dep_col.setMinWidth(200);

				scrollPane_mr = new JScrollPane(jtamr_re);
				jpmr.add(scrollPane_mr, BorderLayout.CENTER);
				jpmr.repaint();
				jpmr.validate();

			}
		});

		jpmr_1.add(jpmr_queryall, FlowLayout.LEFT);
		jpmr_1.add(jpmr_query, FlowLayout.LEFT);
		jpmr_1.add(jtmr_query, FlowLayout.LEFT);

		jpmr.add(scrollPane_mr, BorderLayout.CENTER);

		jpmr.add(jpmr_1, BorderLayout.SOUTH);

		/**************************************************
		 * 
		 ************************ ��Ϣ�����޸Ľ���**************
		 * 
		 ***************************************************/

		jpmc = new JPanel(new BorderLayout()); // ��Ϣ�����޸�

		JPanel jpmc_box = new JPanel();
		JPanel jpmc_up = new JPanel();
		JPanel jpmc_lf = new JPanel();
		JPanel jpmc_rf = new JPanel();
		JPanel jpmc_dw = new JPanel();

		jpmc_up.setPreferredSize(new Dimension(300, 250));// �ؼ�����,����JPanel�Ĵ�С
		jpmc_lf.setPreferredSize(new Dimension(250, 300));
		jpmc_rf.setPreferredSize(new Dimension(250, 300));
		jpmc_dw.setPreferredSize(new Dimension(300, 250));

		jpmc_box.setLayout(new BoxLayout(jpmc_box, BoxLayout.Y_AXIS));
		// jpmc_box.setBounds(100, 100, 500, 200);
		// jpmc_box.setPreferredSize(new Dimension(500, 500));

		JPanel jpmc_box_1 = new JPanel();
		final JTextField jtmc_box_1 = new JTextField(16);
		JLabel jlmc_box_1 = new JLabel("����");
		jpmc_box_1.add(jtmc_box_1);
		jpmc_box_1.add(jlmc_box_1);
		// jpmc_box_1.setPreferredSize(new Dimension(8, 20));

		JPanel jpmc_box_2 = new JPanel();
		final JTextField jtmc_box_2 = new JTextField(16);
		JLabel jlmc_box_2 = new JLabel("�Ա�");
		jpmc_box_2.add(jtmc_box_2);
		jpmc_box_2.add(jlmc_box_2);
		// jpmc_box_2.setPreferredSize(new Dimension(8, 20));

		JPanel jpmc_box_3 = new JPanel();
		final JTextField jtmc_box_3 = new JTextField(16);
		JLabel jlmc_box_3 = new JLabel("ְλ");
		jpmc_box_3.add(jtmc_box_3);
		jpmc_box_3.add(jlmc_box_3);
		// jpmc_box_3.setPreferredSize(new Dimension(8, 20));

		JPanel jpmc_box_4 = new JPanel();
		final JTextField jtmc_box_4 = new JTextField(16);
		JLabel jlmc_box_4 = new JLabel("����");
		jpmc_box_4.add(jtmc_box_4);
		jpmc_box_4.add(jlmc_box_4);

		jpmc_box.add(jpmc_box_1);
		jpmc_box.add(jpmc_box_2);
		jpmc_box.add(jpmc_box_3);
		jpmc_box.add(jpmc_box_4);

		JButton jbmc_add = new JButton("���");

		jbmc_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Employee employee = new Employee();
				employee.setName(jtmc_box_1.getText());
				employee.setGender(jtmc_box_2.getText());
				employee.setPosition(jtmc_box_3.getText());
				EmployeeService service = new EmployeeServiceImpl();
				try {
					service.add(employee);
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "��ӳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "���ʧ��", "����", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		JButton jbmc_update = new JButton("�޸�");
		jpmc_dw.add(jbmc_add);
		jpmc_dw.add(jbmc_update);

		jpmc.add(jpmc_box, BorderLayout.CENTER);
		jpmc.add(jpmc_dw, BorderLayout.SOUTH);
		jpmc.add(jpmc_up, BorderLayout.NORTH);
		jpmc.add(jpmc_lf, BorderLayout.WEST);
		jpmc.add(jpmc_rf, BorderLayout.EAST);

		// jpmc.setBorder(

		/**************************************************
		 * 
		 ************************ ������������**************
		 * 
		 ***************************************************/
		
		jpcr = new JPanel(); // ������������
		
		jtcr = new JTextField(16);

		JButton jpcr_bt = new JButton("JJKBJ");
			
		jpcr.add(jtcr);
		jpcr.add(jpcr_bt);
		
		
		

		/**************************************************
		 * 
		 ************************ ���ڽ���**************
		 * 
		 ***************************************************/

		jpsr = new JPanel(new BorderLayout()); // ���ڲ�ѯ
		JPanel jpsr_1 = new JPanel();
		JPanel jpsr_2 = new JPanel();
		JLabel jbsr = new JLabel("�����ǲ���Ӧ���е㶫����");
		JButton jpsr_gettoday = new JButton("��ѯ���տ�����Ϣ");
		JButton jpsr_getall = new JButton("��ѯ���п�����Ϣ");

		FlowLayout jpsr_flow = new FlowLayout();
		playerInfo = new Object[1][4];

		jtasr_re = new JTable(playerInfo, Names);

		jpsr_flow.setAlignment(FlowLayout.CENTER);
		jpsr_flow.setHgap(20);
		jpsr_flow.setVgap(20);
		jpsr_1.setLayout(jpsr_flow);

		jpsr_gettoday.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SignService service = new SignServiceImpl();
				List<Attence> list = service.getTodayAll();
				Object[][] xiba = new Object[list.size()][4];
				for (int i = 0; i < list.size(); i++) {
					Attence attence = list.get(i);
					String[] str = { attence.getName(), new Date(attence.getSignintime()).toLocaleString(),
							new Date(attence.getSignouttime()).toLocaleString(),
							new Date(attence.getHandletime()).toLocaleString() };
					if (str[2].equals(new Date(0).toLocaleString())) {
						str[2] = "��";
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
				jtasr_re.setFont(new Font("����", 1, 15));
				scrollPane = new JScrollPane(jtasr_re);
				jpsr.add(scrollPane, BorderLayout.CENTER);
				jpsr.repaint();
				jpsr.validate();
			}
		});

		jpsr_getall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SignService service = new SignServiceImpl();
				List<Attence> list = service.getAll();
				Object[][] xiba = new Object[list.size()][4];
				for (int i = 0; i < list.size(); i++) {
					Attence attence = list.get(i);
					String[] str = { attence.getName(), new Date(attence.getSignintime()).toLocaleString(),
							new Date(attence.getSignouttime()).toLocaleString(),
							new Date(attence.getHandletime()).toLocaleString() };
					if (str[2].equals(new Date(0).toLocaleString())) {
						str[2] = "��";
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
				jtasr_re.setFont(new Font("����", 1, 15));
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

		p = new JTabbedPane(JTabbedPane.LEFT);

		p.add("��Ϣ��ѯ", jpmr);
		p.add("���ڲ�ѯ", jpsr);
		p.add("��Ϣ�����޸�", jpmc);
		p.add("������������", jpcr);
		p.setUI(myui);

		p.validate();

		frame.setSize(1080, 720);
		frame.setVisible(true);
		frame.add(p, BorderLayout.CENTER);

		frame.validate();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	

	
	

}


class MyPaneUI extends BasicTabbedPaneUI {
	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 60;
	}

	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 60;
	}
}
