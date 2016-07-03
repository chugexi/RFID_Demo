package com.ly.service.impl;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

import com.ly.dao.AttenceDao;
import com.ly.dao.EmployeeDao;
import com.ly.dao.Employee_AttenceDao;
import com.ly.domain.Employee;
import com.ly.factory.DaoFactory;
import com.ly.service.EmployeeService;
import com.ly.utils.JdbcUtils;

public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeDao edao = DaoFactory.getInstance().creatDao(EmployeeDao.class);
	private Employee_AttenceDao eadao = DaoFactory.getInstance().creatDao(Employee_AttenceDao.class);
	private AttenceDao adao = DaoFactory.getInstance().creatDao(AttenceDao.class);
	
	/* (non-Javadoc)
	 * @see com.ly.service.impl.EmployeeService#add(com.ly.domain.Employee)
	 */
	@Override
	public void add(Employee employee){
//		employee.setId(UUID.randomUUID().toString());
		edao.add(employee);
	}
	
	/* (non-Javadoc)
	 * @see com.ly.service.impl.EmployeeService#update(com.ly.domain.Employee)
	 */
	@Override
	public void update(Employee employee){
		edao.update(employee);
	}
	
	/* (non-Javadoc)
	 * @see com.ly.service.impl.EmployeeService#find(java.lang.String)
	 */
	@Override
	public Employee find(String id){
		return edao.find(id);
	}
	
	public Employee findByRfid(String rfid_id){
		return edao.findByRfid(rfid_id);
	}
	
	public List<Employee> findByName(String name){
		return edao.findByName(name);
	}
	
	/* (non-Javadoc)
	 * @see com.ly.service.impl.EmployeeService#delete(java.lang.String)
	 */
	@Override
	public void delete(String employee_id){
		Connection conn = null;
		try{		
		JdbcUtils.startTransaction();
		eadao.delete(employee_id);
		edao.delete(employee_id);
		adao.delete(employee_id);		
		JdbcUtils.commitTransaction();
		}finally{
			JdbcUtils.closeConnection();
		}
	}
	
	public List<Employee> getAll(){
		return edao.getAll();
	}
}
