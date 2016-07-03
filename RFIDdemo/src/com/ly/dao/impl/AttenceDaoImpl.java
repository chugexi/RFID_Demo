package com.ly.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ly.dao.AttenceDao;
import com.ly.domain.Attence;
import com.ly.domain.Employee;
import com.ly.utils.JdbcUtils;

public class AttenceDaoImpl implements AttenceDao {
	private static String today;

	static {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		today = df.format(date);
	}

	/* (non-Javadoc)
	 * @see com.ly.dao.impl.AttenceDao#signIn(com.ly.domain.Attence)
	 */
	@Override
	public void signIn(Attence attence) {
		try {
			QueryRunner runner = new QueryRunner();
			String sql = "insert into attence(day_id,signintime,signouttime,handletime,name) values(?,?,?,?,?)";
			Object params[] = { attence.getDay_id(), attence.getSignintime(), attence.getSignouttime(),
					attence.getHandletime(),attence.getName()};
			runner.update(JdbcUtils.getConnection(),sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.ly.dao.impl.AttenceDao#signOut(com.ly.domain.Attence)
	 */
	@Override
	public void signOut(Attence attence) {
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "update attence set signouttime=?,handletime=? where day_id=?";
			Object params[] = { attence.getSignouttime(), attence.getHandletime(), attence.getDay_id() };
			runner.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(String employee_id){
		
		try {
			QueryRunner runner = new QueryRunner();
			String sql = "delete from attence where day_id like ?";			
			runner.update(JdbcUtils.getConnection(),sql , "%"+employee_id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.ly.dao.impl.AttenceDao#getTodayAll()
	 */
	@Override
	public List<Attence> getTodayAll() {

		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from attence where day_id like ? order by handletime desc";
			return (List<Attence>) runner.query(sql, today + "%", new BeanListHandler(Attence.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.ly.dao.impl.AttenceDao#getAll()
	 */
	@Override
	public List<Attence> getAll() {

		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from attence order by handletime desc";
			return (List<Attence>) runner.query(sql, new BeanListHandler(Attence.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Attence find(String day_id){
		try{
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql="select * from attence where day_id=?";			
			return (Attence) runner.query(sql, day_id, new BeanHandler(Employee.class));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
	}
}
