package com.ly.dao;

import java.util.List;

import com.ly.domain.Employee;

public interface EmployeeDao {

	void add(Employee employee);

	void update(Employee employee);

	Employee find(String id);

	Employee findByRfid(String rfid_id);

	List<Employee> findByName(String name);

	void delete(String id);

	List<Employee> getAll();

}