package com.nsu.MyChatRoom.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTool {
	private static String userName="root";
	private static String password="xuing233";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/chatroom?characterEncoding=utf8&useSSL=true";
	private static Connection conn = null;
	public DBTool(){
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动加载失败");
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConn(){
		try {
			new DBTool();
			conn = DriverManager.getConnection(url,userName,password);
		} catch (SQLException e) {
			System.out.println("链接失败");
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeDB(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("关闭出错");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getConn();
		closeDB();
	}
}
