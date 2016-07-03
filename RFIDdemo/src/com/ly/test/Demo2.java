//package com.ly.test;
//
//
//import com.ly.ui.Even;
//import com.ly.ui.MyFrame2;
//import com.ly.ui.Person;
//import com.ly.ui.PersonListener;
//
//public class Demo2{
//	private static MyFrame2 myfr;
//	
//	public Demo2(MyFrame2 myfr){
//		this.myfr = myfr;
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//	
//		Person p = new Person();
//		p.registerListener(new MyListener1(myfr));
////		p.eat();
//		p.run();
//	}
//}
//
//class MyListener1 implements PersonListener{
//	private MyFrame2 myfr;
//
//	public MyListener1(MyFrame2 myfr){
//		this.myfr = myfr;
//	}
//	public void doeat(Even even) {
//		System.out.println(even.getPerson()+"你天天吃，你就知道吃，你猪啊！！");
//	}
//
//	public void dorun(Even even) {
//		
//		System.out.println(even.getPerson().getId());
//		
////		MyFrame2  frame = new MyFrame2();
//		
//		myfr.text.setText(even.getPerson().getId());
//		myfr.text.repaint();
//	}
//	
//}
