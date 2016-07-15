package com.ly.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.ly.domain.Employee;
import com.ly.rxtx.SimpleRW;
import com.ly.service.EmployeeService;
import com.ly.service.impl.EmployeeServiceImpl;

public class UpdateEmployeeFrame extends JFrame {

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
	Box baseBox;
	Box boxV1, boxV2, boxV3, boxV4, boxV5, boxV6, boxV7;
	JButton jbmc_update;

	public UpdateEmployeeFrame(Employee em) {
		this.em = em;
		setTitle("查询修改");
		setLayout(new FlowLayout());

		// jpnew_box.setLayout(new BoxLayout(jpnew_box, BoxLayout.Y_AXIS));

		JButton bReadID = new JButton("读卡号");
		JButton bDelID = new JButton("销卡");
		JLabel lbReadID = new JLabel("卡号:");
		tReadID = new JTextField(16);
		tReadID.setText(em.getRfid_id());

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

		jbmc_update = new JButton("修改");
		boxV6.add(jbmc_update);
		boxV6.add(Box.createHorizontalStrut(8));
		boxV6.add(bReadID);
		boxV6.add(Box.createHorizontalStrut(8));
		boxV6.add(bDelID);

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

		bDelID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = jtnew_box_1.getText();
				String rfid_id = tReadID.getText();
				if(rfid_id.trim().equals("")){
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "该员工没有卡", "提示", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				tReadID.setText("");
				EmployeeService ems = new EmployeeServiceImpl();
				Employee em = ems.find(id);
				em.setRfid_id(null);
				ems.update(em);
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "销卡成功", "提示", JOptionPane.INFORMATION_MESSAGE);

			}
		});
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
