package com.ly.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ly.dao.EmployeeDao;
import com.ly.domain.Employee;
import com.ly.utils.JdbcUtils;

public class EmployeeDaoImpl implements EmployeeDao {

	
	/* (non-Javadoc)
	 * @see com.ly.dao.impl.EmployeeDao#add(com.ly.domain.Employee)
	 */
	@Override
	public void add(Employee employee){
		try{
		QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
		String sql="insert into employee(id,name,gender,position,department) values(?,?,?,?,?)";
		Object params[] = {employee.getId(),employee.getName(),employee.getGender(),employee.getPosition(),employee.getDepartment()};
		runner.update(sql, params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.ly.dao.impl.EmployeeDao#update(com.ly.domain.Employee)
	 */
	@Override
	public void update(Employee employee){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql="update employee set name=?,gender=?,position=?,department=?,rfid_id=? where id=?";
			Object params[] = {employee.getName(),employee.getGender(),employee.getPosition(),employee.getDepartment(),employee.getRfid_id(),employee.getId()};
			runner.update(sql, params);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
	}
	
	/* (non-Javadoc)
	 * @see com.ly.dao.impl.EmployeeDao#find(java.lang.String)
	 */
	@Override
	public Employee find(String id){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from employee where id=?";			
			return (Employee) runner.query(sql, id, new BeanHandler(Employee.class));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
	}
	
	public Employee findByRfid(String rfid_id){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from employee where rfid_id=?";			
			return (Employee) runner.query(sql, rfid_id, new BeanHandler(Employee.class));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
	}
	
	public List<Employee> findByName(String name){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from employee where name=?";			
			return (List<Employee>)  runner.query(sql, name, new BeanListHandler(Employee.class));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
	}
	
	/* (non-Javadoc)
	 * @see com.ly.dao.impl.EmployeeDao#delete(java.lang.String)
	 */
	@Override
	public void delete(String id){
		try{
			QueryRunner runner = new QueryRunner();
			String sql="delete from employee where id = ?";			
			runner.update(JdbcUtils.getConnection(),sql,id);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
	}
	
	/* (non-Javadoc)
	 * @see com.ly.dao.impl.EmployeeDao#getAll()
	 */
	@Override
	public List<Employee> getAll(){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from employee";
			return (List<Employee>) runner.query(sql, new BeanListHandler(Employee.class));	
			}catch(Exception e){
				throw new RuntimeException(e);
			}
	}
}
