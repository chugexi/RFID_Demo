package com.ly.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil2 {
	
	public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String strDate = sdf.format(date);
		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
//		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
//		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
//		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
		
		if (strDateH==strDateBeginH && strDateH==strDateEndH){
			if(strDateM>=strDateBeginM && strDateM<=strDateEndM){
				return true;
			}
			return false;
		}	
		
		if (strDateH==strDateBeginH && strDateH<strDateEndH){
			if(strDateM>=strDateBeginM){
				return true;
			}
			return false;
		}
		
        if (strDateH>strDateBeginH && strDateH==strDateEndH){
        	if(strDateM<=strDateBeginM){
				return true;
			}
			return false;
		}
        
        if (strDateH>strDateBeginH && strDateH<strDateEndH){
			return true;
		}	
		
		
		return false;
	}
	}

