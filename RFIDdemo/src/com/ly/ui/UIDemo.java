package com.ly.ui;

import java.awt.Component;
import java.awt.Container;
import java.util.Date;

import javax.swing.JFrame;

public class UIDemo {

	private static UIDemo instance;
    private static MyFrame1 mya;
	private UIDemo() {
	};

	public static synchronized UIDemo getInstance(MyFrame1 my) {  
		    if (instance == null) {  
		        instance = new UIDemo();  
		    }
		    mya = my;
	return instance;
	}
	public void run() {

		
			mya.jtst.setText(new Date().toLocaleString());
			mya.jtst.repaint();
		
	}

}
