package com.orangehrm.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

	private static Connection conn;

	public static void setMysqlDbConnection() throws SQLException {
		try {
			Class.forName(TestConfig.mysqldriver);
			conn = DriverManager.getConnection(TestConfig.mysqlurl, TestConfig.mysqlusername, TestConfig.mysqlpassword);
			if(!conn.isClosed())
				System.out.println("Successfully connected to MySQL");
		} catch (Exception e) {
			System.err.println("Cannot connect to database");
		}
	}
	
	public static List<String> getMysqlQuery(String query) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		List<String> values1 = new ArrayList<String>();
		while(rs.next()) {
			values1.add(rs.getString(1));
		}
		return values1;
	}
	
	public static void main(String[] args) throws SQLException {
		DBManager.setMysqlDbConnection();
		List<String> empId = DBManager.getMysqlQuery("SELECT count(*) FROM hs_hr_employee");
		for (String emp : empId)
		System.out.println(emp);
	}
}
