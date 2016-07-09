package com.ly.service.impl;

import java.awt.Toolkit;
import java.io.IOException;
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
import com.ly.utils.JdbcUtils;
import com.ly.utils.TimeUtils;

public class SignServiceImpl implements SignService {

	private EmployeeDao edao = DaoFactory.getInstance().creatDao(EmployeeDao.class);
	private Employee_AttenceDao eadao = DaoFactory.getInstance().creatDao(Employee_AttenceDao.class);
	private AttenceDao adao = DaoFactory.getInstance().creatDao(AttenceDao.class);
	private long time;
	private Date date;
	private static Properties timeconfig = new Properties();
	

	public void sign(String id){
		try {
			timeconfig.load(SignServiceImpl.class.getClassLoader().getResourceAsStream("time.properties"));
		} catch (IOException e) {
			throw new RuntimeException();
		}
		System.out.println(timeconfig.getProperty("signinMin"));
		System.out.println(timeconfig.getProperty("signinMax"));
		System.out.println( timeconfig.getProperty("signoutMin"));
		System.out.println(timeconfig.getProperty("signoutMax"));
		
		date = new Date();
		if(TimeUtils.isInDate(date, timeconfig.getProperty("signinMin"), timeconfig.getProperty("signinMax"))){			
			signIn(id);
			System.out.println("qiandao");
			return;
		}
		
		if(TimeUtils.isInDate(date, timeconfig.getProperty("signoutMin"),timeconfig.getProperty("signoutMax"))){
			signOut(id);
			System.out.println("qiantui");
			return;
		}
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, "不在签到时间", "提示", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("不在签到时间");
		
		
	}
	/* (non-Javadoc)
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
		if(attence==null){
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
		if(attence.getSignintime()!=0){
			System.out.println("已签到");
			return;
		}
		
		

	}

	/* (non-Javadoc)
	 * @see com.ly.service.impl.SignService#signOut(java.lang.String)
	 */
	@Override
	public void signOut(String id) {
		
		
		date = new Date();
		time = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(date);
		String day_id = today + "_" + id;
		Attence attence = adao.find(day_id);
		if(attence == null){
			System.out.println("为签到");
			return;
		}
		if(attence.getSignouttime()!=0){
			System.out.println("已签退");
			return;
		}
//		attence.setDay_id(day_id);
		attence.setSignouttime(time);
		attence.setHandletime(time);
		adao.signOut(attence);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.ly.service.impl.SignService#getTodayAll()
	 */
	@Override
	public List<Attence> getTodayAll(){		
		return adao.getTodayAll();		
	}
	
	public List<Attence> getAll(){		
		return adao.getAll();		
	}
	
	public List<Attence> getOneDayAll(String day){		
		return adao.getOneDayAll(day);		
	}
	
	

	
}
