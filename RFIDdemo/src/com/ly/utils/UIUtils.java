package com.ly.utils;

import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.TableColumn;

import com.ly.domain.Attence;
import com.ly.service.SignService;
import com.ly.service.impl.SignServiceImpl;


public class UIUtils {
	static JTable employeejtable;
	static JTable myjtable;
	static String[] Names = { "姓名", "签到时间", "签退时间", "考核情况" };
	static String[] Namess = { "姓名", "签到时间", "签退时间", "操作提示" };
	public static JTable refresh(JTable jtable,String day){
		
		myjtable = jtable;
		SignService service = new SignServiceImpl();
		List<Attence> list = service.getOneDayAll(day);
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
		myjtable = new JTable(xiba, Names);
		myjtable.setRowHeight(50);
		myjtable.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		myjtable.setDefaultRenderer(Object.class, tcr);
		myjtable.setFont(new Font("宋体", 1, 15));
		
		return myjtable;
		
	}
	
public static JTable refresh3(JTable jtable,String id){
		
		myjtable = jtable;
		SignService service = new SignServiceImpl();
		List<Attence> list = service.findById(id);
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
		myjtable = new JTable(xiba, Names);
		myjtable.setRowHeight(50);
		myjtable.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		myjtable.setDefaultRenderer(Object.class, tcr);
		myjtable.setFont(new Font("宋体", 1, 15));
		
		return myjtable;
		
	}
	
	public static JTable refresh2(JTable jtable){
		
		myjtable = jtable;
		SignService service = new SignServiceImpl();
		List<Attence> list = service.getTodayAll();
		Object[][] xiba = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			Attence attence = list.get(i);
			String[] str = { attence.getName(), new Date(attence.getSignintime()).toLocaleString(),
					new Date(attence.getSignouttime()).toLocaleString(),
					"操作成功  " + attence.getResult()};
			if (str[2].equals(new Date(0).toLocaleString())) {
				str[2] = "无";
			}
			for (int j = 0; j < 4; j++) {
				xiba[i][j] = str[j];
			}
		}
		myjtable = new JTable(xiba, Namess);
		myjtable.setRowHeight(50);
		myjtable.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		myjtable.setDefaultRenderer(Object.class, tcr);
		myjtable.setFont(new Font("宋体", 1, 15));
		
		return myjtable;
		
	}
	
	public static JTable queryEmployee(JTable jtable){
		employeejtable = jtable;
		
		

		employeejtable.setRowHeight(50);
		employeejtable.setEnabled(false);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		employeejtable.setDefaultRenderer(Object.class, tcr);
		employeejtable.setFont(new Font("宋体", 1, 15));

		TableColumn name_col = employeejtable.getColumnModel().getColumn(1);
		TableColumn sex_col = employeejtable.getColumnModel().getColumn(2);
		TableColumn pos_col = employeejtable.getColumnModel().getColumn(3);
		TableColumn dep_col = employeejtable.getColumnModel().getColumn(4);
		TableColumn upd_col = employeejtable.getColumnModel().getColumn(6);
		TableColumn del_col = employeejtable.getColumnModel().getColumn(7);

		name_col.setPreferredWidth(100);
		name_col.setMaxWidth(100);
		name_col.setMinWidth(100);

		sex_col.setPreferredWidth(100);
		sex_col.setMaxWidth(100);
		sex_col.setMinWidth(100);

		pos_col.setPreferredWidth(150);
		pos_col.setMaxWidth(150);
		pos_col.setMinWidth(150);

		dep_col.setPreferredWidth(100);
		dep_col.setMaxWidth(100);
		dep_col.setMinWidth(100);

		upd_col.setPreferredWidth(70);
		upd_col.setMaxWidth(70);
		upd_col.setMinWidth(70);

		del_col.setPreferredWidth(70);
		del_col.setMaxWidth(70);
		del_col.setMinWidth(70);
		
		
		return employeejtable;
		
		
	}
	
	
}

