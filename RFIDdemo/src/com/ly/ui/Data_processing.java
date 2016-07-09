package com.ly.ui;

public class Data_processing { 
	static String s[] = new String[4];
	public static void printHexString( byte[] b) {
	   for (int i = 0; i < b.length; i++) {  
	     String hex = Integer.toHexString(b[i] & 0xFF)+" ";  
	     if (hex.length() == 2) {  
	       hex = '0' + hex;  
	     }  
	     System.out.print(hex.toUpperCase());    
	   }
	System.out.println("\n");
	}
	
	public String SelectID(byte[] a){
		for (int i = 0; i < a.length; i++) {  
		     String hex = Integer.toHexString(a[i] & 0xFF)+" ";  
		     if (hex.length() == 2) {  
		       hex = '0' + hex;  
		     }  
		     if(i==6){
		    	 s[0]=hex.toUpperCase();		    	 
		     }
		     if(i==7){
		    	 s[1]=hex.toUpperCase();		    	 
		     }
		     if(i==8){
		    	 s[2]=hex.toUpperCase();
		     }
		     if(i==9){
		    	 s[3]=hex.toUpperCase();		    	 
		     }
		   }
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length; i++){
		sb. append(s[i]);
		}

		String newStr = sb.toString();
		newStr = newStr.replace(" ", "");
		System.out.println(newStr);
		System.out.println("\n");
		return newStr;
	}
}  
