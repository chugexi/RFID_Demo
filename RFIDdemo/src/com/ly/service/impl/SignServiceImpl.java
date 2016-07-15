package com.ly.service.impl;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.ly.dao.AttenceDao;
import com.ly.dao.EmployeeDao;
import com.ly.dao.Employee_AttenceDao;

import com.ly.domain.Attence;
import com.ly.domain.Employee;
import com.ly.factory.DaoFactory;
import com.ly.service.SignService;
import com.ly.test.JarTool;
import com.ly.utils.JdbcUtils;
import com.ly.utils.TimeUtil2;
import com.ly.utils.TimeUtils;

public class SignServiceImpl implements SignService {

	private EmployeeDao edao = DaoFactory.getInstance().creatDao(EmployeeDao.class);
	private Employee_AttenceDao eadao = DaoFactory.getInstance().creatDao(Employee_AttenceDao.class);
	private AttenceDao adao = DaoFactory.getInstance().creatDao(AttenceDao.class);
	private long time;
	private Date date = new Date();
	private static Properties timeconfig = new Properties();
	static String realpath;
	public void sign(String id) {
		try {
			realpath = JarTool.getJarPath();
			realpath = realpath.replace("\\", "/");			
			InputStream in = new FileInputStream(realpath+"/time.properties");
			timeconfig.load(in);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		System.out.println(timeconfig.getProperty("signinMin"));
		System.out.println(timeconfig.getProperty("signinMax"));
		System.out.println(timeconfig.getProperty("signoutMin"));
		System.out.println(timeconfig.getProperty("signoutMax"));
		String signinMax = timeconfig.getProperty("signinMax");

		String inMax[] = signinMax.split(":");
		String delay = timeconfig.getProperty("lateDelay");
		int i;
		switch (delay) {
		case "1":
			i = (Integer.parseInt(inMax[0])) + 1;
			inMax[0] = i + "";
			break;
		case "2":
			i = (Integer.parseInt(inMax[0])) + 2;
			inMax[0] = i + "";
			break;
		case "3":
			i = (Integer.parseInt(inMax[0])) + 3;
			inMax[0] = i + "";
			break;
		default:
			break;
		}

		String latetime = inMax[0] + ":" + inMax[1];
		System.out.println(latetime);
		if (TimeUtil2.isInDate(date, timeconfig.getProperty("signinMin"), timeconfig.getProperty("signinMax"))) {

			signIn(id);

			return;
		} 
		
		if (TimeUtil2.isInDate(date,timeconfig.getProperty("signinMax"), latetime)) {
			delaySignIn(id);
			System.out.println("chidao");
			return;
		} 
		
		if (TimeUtil2.isInDate(date, timeconfig.getProperty("signoutMin"), timeconfig.getProperty("signoutMax"))) {
			signOut(id);
			 System.out.println("qiantui");
			return;
		} 
			
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "不在签到时间", "提示", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("不在签到时间");
		
	}

	public void delaySignIn(String id) {
		date = new Date();

		time = System.currentTimeMillis();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(date);
		String day_id = today + "_" + id;
		Attence attence = new Attence();
		attence = adao.find(day_id);
		if (attence == null) {
			attence = new Attence();
			System.out.println("签到");
			Connection conn = null;

			Employee employee = edao.find(id);
			attence.setDay_id(day_id);
			attence.setSignintime(time);
			attence.setHandletime(time);
			attence.setName(employee.getName());
			attence.setResult("迟到");
			try {
				JdbcUtils.startTransaction();
				adao.signIn(attence);
				eadao.add(id, day_id);
				JdbcUtils.commitTransaction();
			} finally {
				JdbcUtils.closeConnection();
			}
		}
		else if (attence.getSignintime() != 0) {
			System.out.println("已签到");
			return;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ly.service.impl.SignService#signIn(java.lang.String)
	 */
	@Override
	public void signIn(String id) {
		date = new Date();

		time = System.currentTimeMillis();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(date);
		String day_id = today + "_" + id;
		Attence attence = new Attence();
		attence = adao.find(day_id);
		if (attence == null) {
			attence = new Attence();
			System.out.println("签到");
			Connection conn = null;

			Employee employee = edao.find(id);
			attence.setDay_id(day_id);
			attence.setSignintime(time);
			attence.setHandletime(time);
			attence.setName(employee.getName());

			try {
				JdbcUtils.startTransaction();
				adao.signIn(attence);
				eadao.add(id, day_id);
				JdbcUtils.commitTransaction();
			} finally {
				JdbcUtils.closeConnection();
			}
		}
		else if (attence.getSignintime() != 0) {
			System.out.println("已签到");
			return;
		}

	}

	public void signOut(String id) {

		date = new Date();
		time = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(date);
		String day_id = today + "_" + id;
		Attence attence = adao.find(day_id);
		if (attence == null) {
			System.out.println("为签到");
			return;
		}
		if (attence.getSignouttime() != 0) {
			System.out.println("已签退");
			return;
		}
		if(attence.getResult().trim().equals("")){
			attence.setResult("正常考核");
		}
		if(attence.getResult().trim().equals("迟到")){
			attence.setResult("迟到");
		}
		// attence.setDay_id(day_id);
		attence.setSignouttime(time);
		attence.setHandletime(time);

		adao.signOut(attence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ly.service.impl.SignService#getTodayAll()
	 */
	@Override
	public List<Attence> getTodayAll() {
		return adao.getTodayAll();
	}

	public List<Attence> getAll() {
		return adao.getAll();
	}

	public List<Attence> getOneDayAll(String day) {
		return adao.getOneDayAll(day);
	}
	
	public List<Attence> findById(String id) {
		return adao.findById(id);
	}
	

}
