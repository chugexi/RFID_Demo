package com.ly.thread;

import com.ly.ui.SimpleRW;

public class MyThread extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		SimpleRW simp = SimpleRW.getInstance();
		simp.alwaysFindCard();
	}

}
