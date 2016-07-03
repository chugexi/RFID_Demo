package com.ly.test;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.ly.dao.AttenceDao;
import com.ly.dao.EmployeeDao;
import com.ly.dao.Employee_AttenceDao;
import com.ly.dao.impl.AttenceDaoImpl;
import com.ly.dao.impl.EmployeeDaoImpl;
import com.ly.dao.impl.Employee_AttenceDaoImpl;
import com.ly.domain.Attence;
import com.ly.domain.Employee;
import com.ly.service.EmployeeService;
import com.ly.service.SignService;
import com.ly.service.impl.EmployeeServiceImpl;
import com.ly.service.impl.SignServiceImpl;
import com.ly.utils.JdbcUtils;

public class Test1 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

	
//		SignService service = new SignServiceImpl();
//		List list= service.getTodayAll();
//		Attence attence = (Attence) list.get(0);
//		
//		
//		String str = new Date(attence.getSignouttime()).toLocaleString();
//		if(str.equals(new Date(0).toLocaleString())){
//			System.out.println("wocao ");
//		}
//		
//		dfd();
//		dfds();
		System.out.println(sdss());
		
	}

	public static int sdss(){
		int i = 0;
		while(i<10){
			i++;
			if(i==9){
				return i;
				
				
			}
		}
		return i;
	}
	

	@Test
	public void tt() {
		String id = "455464gfdg";
		Date date = new Date();
		Attence attence = new Attence();
		long date2 = System.currentTimeMillis();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = df.format(date);
		String day_id = today + "_" + id;

		attence.setDay_id(day_id);
		attence.setSignintime(date2);
		attence.setHandletime(date2);

		AttenceDao adao = new AttenceDaoImpl();
		Employee_AttenceDao eadao = new Employee_AttenceDaoImpl();

		adao.signIn(attence);
		eadao.add(id, day_id);

		// System.out.println(date.toLocaleString());
		// if(date.getHours()<8 && date.getHours()>7){
		// System.out.println("打卡成功");
		// }else{
		// System.out.println("打卡失败");
	}

	@Test
	public void t() {
		Date date;
		//
		// SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		//// StringBuffer sb = new StringBuffer();
		//// sb.append();
		//// sb.append(date.getMonth()+"");
		//// sb.append(date.getDay()+"");
		// System.out.println(date);
		// System.out.println(df.format(date));
		//

		AttenceDao adao = new AttenceDaoImpl();
		List list = adao.getTodayAll();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Attence a = (Attence) it.next();
			date = new Date(a.getSignintime());
			System.out.println(date);
		}

	}

	@Test
	public void erer() {
		EmployeeDao edao = new EmployeeDaoImpl();
		List<Employee> list = edao.getAll();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Employee e = (Employee) it.next();
			System.out.println(e.getName());
		}
	}

	@Test
	public static void dfd() {
		SignService service = new SignServiceImpl();
		service.signIn("242343");
	}

	@Test
	public static void dfds() {
		SignService service = new SignServiceImpl();
		service.signOut("5413256456");
	}

	@Test
	public void ds() {
		SignService service = new SignServiceImpl();
		List<Attence> list = service.getTodayAll();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Attence a = (Attence) it.next();
			System.out.println("人员:" + a.getName());
			System.out.println("签到时间:" + new Date(a.getSignintime()).toLocaleString());
			System.out.println("签退时间:" + new Date(a.getSignouttime()).toLocaleString());
			System.out.println("最后操作时间:" + "" + new Date(a.getHandletime()).toLocaleString());
		}
	}

	@Test
	public void deleteemployee() {
		EmployeeService service = new EmployeeServiceImpl();
		service.delete("23123123sds");
	}

//	@Test
//	public void llt() {
//		
//		
//		List list = new ArrayList();
//		int[] a ={1,2,3};
//		list.add(a);
//		int[][] b = 
//		
//		}

	

}
