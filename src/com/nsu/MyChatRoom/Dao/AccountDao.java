package com.nsu.MyChatRoom.Dao;

import java.nio.channels.SelectableChannel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.nsu.MyChatRoom.Bean.Account;
import com.nsu.MyChatRoom.Exce.MyChatException;
import com.nsu.MyChatRoom.Util.DBTool;

public class AccountDao {
	private static java.sql.Connection conn = null;
	private static Statement st = null;
	private static String sql = "";
	private static Account account = null;
	private static ArrayList<Account> accounts;
	private static ResultSet rs = null;
	
	public static void getConn(){
		 conn = DBTool.getConn();
	}
	
	static{
		accounts = new ArrayList<>();
	}

//	public static Account getAccount(){
//		return account;
//	}

	public static ArrayList<Account> getAccounts(){
		try {
			conn = DBTool.getConn();
			st = conn.createStatement();
			sql = "select * from Account";
			rs = st.executeQuery(sql);
			
			while(rs.next()){
				account = new Account();
				account.setUserName(rs.getString("username"));
				account.setPassword(rs.getString("password"));
				account.setAvatar(rs.getString("avater"));
				accounts.add(account);
			}
			return accounts;
		} catch (SQLException e) {
			System.out.println("对象st创建失败");
			e.printStackTrace();
			return null;
		}finally {
			DBTool.closeDB();
		}
	}
	
	public static Account getAccountByName(String name){
	
		try {
			conn =  DBTool.getConn();
			st = conn.createStatement();
			sql = "select * from Account where user ='"+name+"'";
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				account = new Account();
				account.setUserName(rs.getString("user"));
				account.setPassword(rs.getString("password"));
				account.setAvatar(rs.getString("avater"));
				return account;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DBTool.closeDB();
		}
	
	}
	
	public static String getAvater(Account account) {
		getStatement();
		String sql = "select money from account where username = '"+account.getUserName()+"'";
		try {
			rs = st.executeQuery(sql);
			if(rs.next())
			return rs.getString("avater");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addAccount(Account account){
		conn =  DBTool.getConn();
		String sql = "insert into account (username,password,avater) values(?,?,?)";
		PreparedStatement pstmt;
		
		int r = -1;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account.getUserName());
			pstmt.setString(2, account.getPassword());
			pstmt.setString(3, account.getAvatar());
			r = pstmt.executeUpdate();
			accounts.add(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBTool.closeDB();
		}
//		System.out.println("r="+r);
	
	}
	
	public static void deleteAccount(Account account){
		PreparedStatement pstmt;
		try {
			getConn();
			sql = "delete from Account where username = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account.getUserName());
			pstmt.executeUpdate();
			accounts.remove(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void updateMoney(int money){
//		Common.account.setMoney(money);
//		updateAccount(Common.account,Common.account.getUserName());
//	}
//	
//	public static void updateMoney(Account account,int money){
//		account.setMoney(money);
//		updateAccount(account,account.getUserName());
//	}
	
	
//	public static void updateAccount(Account newAccount,String oldname){
//		getConn();
//		String sql = "update account set user=?,password=?,money=? where user = ?";
//		PreparedStatement pstmt;
//		
//		int r = -1;
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, newAccount.getUserName());
//			pstmt.setString(2, newAccount.getPassword());
//			pstmt.setInt(3, newAccount.getMoney());
//			pstmt.setString(4, oldname);
//			r = pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			DBTool.closeDB();
//		}
//
//	}
	
	
	public static void getStatement(){
		try {
			getConn();
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
////		getAccounts();
////		new AccountDao();
////		addAccount(new Account("bili2","dili",9999999));
////		getAccountByName("bili2");
////		Account account = new Account("bili", "dili", 500);
////		updateAccount(account);
////		deleteAccount("bili");
//	}
}

