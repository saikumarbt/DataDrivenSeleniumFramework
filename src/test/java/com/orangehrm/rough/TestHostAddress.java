package com.orangehrm.rough;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestHostAddress {
	
	public static String getCurrentDateAndTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy_HHmmss");  
	    String currentdate =  formatter.format(date).toString(); 
	    
	    return currentdate; 
	 
	}

	public static void main(String[] args) throws UnknownHostException {
		String messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()+ ":8080/jenkins/job/OrangeHRMWeb-DataDriven/Extent_20Reports/";
		System.out.println(messageBody);
		System.setProperty("current.date", TestHostAddress.getCurrentDateAndTime());
		
		System.out.println(System.getProperty("current.date"));
		
	}
}
