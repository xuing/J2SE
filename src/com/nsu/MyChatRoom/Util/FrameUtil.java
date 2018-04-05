package com.nsu.MyChatRoom.Util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.mysql.jdbc.StatementInterceptor;
import com.nsu.MyChatRoom.Bean.Account;
import com.nsu.MyChatRoom.Exce.MyChatException;
import com.nsu.MyChatRoom.Service.AccountService;

public class FrameUtil {

	public static int regAvatarWeight = 180;
	public static int regAvatarHeight = 180;
	public static int listAvatarWeight = 80;
	public static int listAvatarHeight = 50;
	public static int FrameBound = 0;
	public static int imgNumber = 0; //TODO ???????????
	private static int bgnum = 0;
	
	public static String bgGetImage(String imgPath,int w,int h) throws Exception{
		return imgToAvatar(imgPath, w, h, bgnum++,"bg");
	}
	
	public static String listImgToAvatar(String imgPath) throws Exception{
		return imgToAvatar(imgPath, listAvatarWeight, listAvatarHeight, 0,"list");
	}
	
	public static String regImaToAvatar(String imgPath,int num) throws Exception{
		return imgToAvatar(imgPath, regAvatarWeight, regAvatarWeight, num,"reg");
	}
	
	private static String imgToAvatar(String imgPath,int w,int h,int imgNumber,String qianzhui) throws Exception{  
        
//		imgNumber = AccountService.getUserCount();
	
        String srcImageFile = imgPath;  
        //??????  
        BufferedImage bi = ImageIO.read(new File(srcImageFile));  
        //??????????????·Ú  
        Image prevImage = bi.getScaledInstance(w, h, Image.SCALE_DEFAULT);  
        //?????image  
        BufferedImage tag = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);  
        //???????????  
        Graphics graphics = tag.createGraphics();      
        graphics.drawImage(prevImage, 0, 0, w, h, null);  
        graphics.dispose();  
        //?????  
        String newName = "img/temp_"+qianzhui+"_"+(AccountService.getUserCount()+imgNumber++)+".jpg"; 

        OutputStream outs = new FileOutputStream(newName);
        //????  
        ImageIO.write(tag, "JPEG", outs);  
        outs.close();  
        return newName;
    }  
	
	public static void showErrorMessage(String string) {
		showMessageDialog(string,"????",JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showInfoMessage(String string) {
		showMessageDialog(string, "ATM", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static void showMessageDialog(String message, String title, int messageType){
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

	public static int InputInt(String string) throws MyChatException {
		try {
			return Integer.parseInt(JOptionPane.showInputDialog(null, string, "??????"));
		} catch (NumberFormatException e) {
			throw new MyChatException("??????????");
		}
		
	}
	
	public static int InputUnsignedInt(String string) throws MyChatException {
		int res = InputInt(string);
		if(res >= 0) return res;
		else throw new MyChatException("???????????????0");
	}

	public static String InputString(String string) {
		return JOptionPane.showInputDialog(null, string, "??????");
	}

	public static String getServiceAddress(){
		return "127.0.0.1";  //TODO ??????
	}
	
	public static String getServicePort(){
		return "2333";
	}
	
}
