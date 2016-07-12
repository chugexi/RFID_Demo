package com.ly.domain;



public class Attence {
	private String day_id;
	private long signintime=0;
	private long signouttime=0;
	private long handletime=0;
	private String name;
	private String result = " ";
	public String getDay_id() {
		return day_id;
	}
	public void setDay_id(String day_id) {
		this.day_id = day_id;
	}
	public long getSignintime() {
		return signintime;
	}
	public void setSignintime(long signintime) {
		this.signintime = signintime;
	}
	public long getSignouttime() {
		return signouttime;
	}
	public void setSignouttime(long signouttime) {
		this.signouttime = signouttime;
	}
	public long getHandletime() {
		return handletime;
	}
	public void setHandletime(long handletime) {
		this.handletime = handletime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
