package com.inspur.mysql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnection {
	 Connection conn = null; 
	 Statement stmt = null;
	 ResultSet rs = null;
	
 public Statement initSQLLink(Connection c , Statement s , ResultSet r){
		conn = c;
		stmt = s;
		rs = r;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://10.110.13.21:3306/renmin_info", "root", "123456");		
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Class not found");
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("initSQLLink Wrong");
		}
		return null;
	}
	public Statement getStmt() {
		return stmt;
	}
	public ResultSet getRs() {
		return rs;
	}
	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	public Connection getConnection(){
		return conn;
	}
}