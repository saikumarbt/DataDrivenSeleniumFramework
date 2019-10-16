package com.orangehrm.utilities;

import java.util.TimeZone;

public class TestConfig {

	//MY SQL DATABASE DETAILS
	public static String mysqldriver = "com.mysql.cj.jdbc.Driver";
	public static String mysqlusername = "root";
	public static String mysqlpassword = "";
	public static String mysqlurl="jdbc:mysql://localhost:3306/bitnami_orangehrm?serverTimezone=" + TimeZone.getDefault().getID();
	
}
