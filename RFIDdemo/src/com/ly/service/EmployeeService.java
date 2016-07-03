package com.ly.service;

import java.util.List;

import com.ly.domain.Employee;

public interface EmployeeService {

	void add(Employee employee);

	void update(Employee employee);

	Employee find(String id);
	
	Employee findByRfid(String rfid_id);
	
	List<Employee> findByName(String name);

	void delete(String employee_id);
	
	List<Employee> getAll();

}