package com.ly.service;

import java.util.List;

import com.ly.domain.Attence;

public interface SignService {

	void signIn(String id);

	void signOut(String id);
	
	void sign(String id);

	List<Attence> getTodayAll();
	
	List<Attence> getAll();

}