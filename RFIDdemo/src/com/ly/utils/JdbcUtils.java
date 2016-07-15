package com.ly.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.ly.test.JarTool;
import com.ly.ui.MyFrame1;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	

		private static ComboPooledDataSource ds = null;
		
		private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>(); 
		
		static{
			String realpath = JarTool.getJarPath();
			
			realpath = realpath.replace("\\", "/");
			System.out.println("jdbc:sqlite:"+realpath+"/rfid.db");
			try{
				ds = new ComboPooledDataSource();
				ds.setDriverClass("org.sqlite.JDBC");
				ds.setJdbcUrl("jdbc:sqlite:"+realpath+"/rfid.db");
				ds.setUser("");
				ds.setPassword("");
				ds.setMaxPoolSize(30);
				ds.setMinPoolSize(5);
				ds.setInitialPoolSize(10);
			}catch (Exception e) {				
				throw new ExceptionInInitializerError(e);
			}
			
		}
		
		public static DataSource getDataSource(){
			
			return ds;
		}
		
		public static Connection getConnection() throws SQLException {

			try {
				Connection conn = tl.get();
				if (conn == null) {
					conn = ds.getConnection();
					tl.set(conn);
				}
				return conn;
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		
		public static void startTransaction(){
			try{
				//得到当前线程上绑定连接开启事务
				Connection conn = tl.get();
				if(conn==null){  //代表线程上没有绑定连接
					conn = ds.getConnection();
					tl.set(conn);
				}
				conn.setAutoCommit(false);
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public static void commitTransaction(){
			try{
				Connection conn = tl.get();
				if(conn!=null){
					conn.commit();
				}
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		public static void closeConnection(){
			try{
				Connection conn = tl.get();
				if(conn!=null){
					conn.close();
				}
			}catch (Exception e) {
				throw new RuntimeException(e);
			}finally{
				tl.remove();   //千万注意，解除当前线程上绑定的链接（从threadlocal容器中移除对应当前线程的链接）
			}
		}
	}


