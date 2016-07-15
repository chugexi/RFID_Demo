package com.ly.dao;

import java.util.List;

import com.ly.domain.Attence;

public interface AttenceDao {

	void signIn(Attence attence);

	void signOut(Attence attence);

	List<Attence> getTodayAll();

	List<Attence> getAll();

	void delete(String employee_id);
	
	Attence find(String day_id);
	
	List<Attence> getOneDayAll(String day);
	
	List<Attence> findById(String id);
}