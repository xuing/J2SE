package com.nsu.MyChatRoom.Service;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.nsu.MyChatRoom.Bean.Account;
import com.nsu.MyChatRoom.Dao.AccountDao;
import com.nsu.MyChatRoom.Exce.MyChatException;
import com.nsu.MyChatRoom.Util.FrameUtil;

public class AccountService { //TODO ¸ÄÎªChatService
	private static ArrayList<Account> accounts = null;

	static{
		getAccounts();
	}
	
	public static void addAccount(String userName, String password, String imgPath) {
//		System.out.println(userName+" "+password+" "+imgPath);
		AccountDao.addAccount(new Account(userName,password,imgPath));
	}
	
	public static void getAccounts(){
		accounts = AccountDao.getAccounts();
	}

	public static Account loginVerify(String userName, String password) {
		Account login = new Account(userName, password, "");
		for(Account account : accounts){
			if(account.equals(login))return account;
		}
		return null;
	}
	
	public static int getUserCount(){
		return accounts.size();
	}
	
	public static final String  ServiceIp = "127.0.0.1";
	public static final Integer ServicePort = 2333;
	
}
