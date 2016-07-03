package com.ly.dao.impl;

import org.apache.commons.dbutils.QueryRunner;

import com.ly.dao.Employee_AttenceDao;
import com.ly.utils.JdbcUtils;

public class Employee_AttenceDaoImpl implements Employee_AttenceDao {
	
	/* (non-Javadoc)
	 * @see com.ly.dao.impl.Employee_AttenceDao#add(java.lang.String, java.lang.String)
	 */
	@Override
	public void add(String employee_id,String attence_day_id){
		
		try {
			QueryRunner runner = new QueryRunner();
			String sql = "insert into employee_attence(employee_id,attence_day_id) values(?,?)";
			Object params[] = {employee_id,attence_day_id};
			runner.update(JdbcUtils.getConnection(), sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
/* (non-Javadoc)
 * @see com.ly.dao.impl.Employee_AttenceDao#delete(java.lang.String)
 */
@Override
public void delete(String employee_id){
		
		try {
			QueryRunner runner = new QueryRunner();
			String sql = "delete from employee_attence where employee_id=?";			
			runner.update(JdbcUtils.getConnection(),sql,employee_id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
