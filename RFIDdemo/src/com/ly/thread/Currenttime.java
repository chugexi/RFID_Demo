package com.ly.thread;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import com.ly.ui.MyFrame1;

public class Currenttime extends Thread{
	
		Date datedisplay;
		GregorianCalendar gccalendar;
		String strtime;

		public Currenttime() {
		}

		public void run() {
			while (true) {
				displaytime();
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "线程中断");
				}
			}
		}

		public void displaytime() {
			datedisplay = new Date();
			gccalendar = new GregorianCalendar();
			gccalendar.setTime(datedisplay);
			strtime = "当前时间:    " + gccalendar.get(Calendar.YEAR) + "-" +(gccalendar.get(Calendar.MONTH)+1) + "-" +gccalendar.get(Calendar.DAY_OF_MONTH) + "   " +gccalendar.get(Calendar.HOUR_OF_DAY) + ":" + gccalendar.get(Calendar.MINUTE) + ":"
					+ gccalendar.get(Calendar.SECOND);
			MyFrame1.time.setText(strtime);
		}
	

}
