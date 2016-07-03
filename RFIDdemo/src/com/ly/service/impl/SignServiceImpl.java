package com.ly.service.impl;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.ly.dao.AttenceDao;
import com.ly.dao.EmployeeDao;
import com.ly.dao.Employee_AttenceDao;
import com.ly.dao.impl.AttenceDaoImpl;
import com.ly.dao.impl.Employee_AttenceDaoImpl;
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

	
	public void sign(String id){
		date = new Date();
		if(TimeUtils.isInDate(date, "07:30:00", "08:00:00")){
			signIn(id);
			System.out.println("qiandao");
		}
		
		else if(TimeUtils.isInDate(date, "11:00:00", "17:30:00")){
			signOut(id);
			System.out.println("qiantui");
		}
		else System.out.println("sfasfafa");
			
		
		
	}
	/* (non-Javadoc)
	 * @see com.ly.service.impl.SignService#signIn(java.lang.String)
	 */
	@Override
	public void signIn(String id) {
		Connection conn = null;
		date = new Date();
		Attence attence = new Attence();
		time = System.currentTimeMillis();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(date);
		String day_id = today + "_" + id;
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
			System.out.println("ÎªÇ©µ½");
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
	
	

	
}
