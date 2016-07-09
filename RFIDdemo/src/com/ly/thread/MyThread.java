package com.ly.thread;

import com.ly.ui.SimpleRW;

public class MyThread extends Thread {
	SimpleRW simp = SimpleRW.getInstance();
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
        super.run();		
		simp.setRun(true);
		simp.alwaysFindCard();
	}

	
	

	
}
