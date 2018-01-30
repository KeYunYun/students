package com.kcy.mobilesafe.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public static String encoder(String psd) {
		// TODO Auto-generated method stub
		//1.指定加密算法类型
		try {
			psd=psd+"mobilesafe";
			MessageDigest digest=MessageDigest.getInstance("MD5");
		//2.将要转换的字符串装换为byte数值
			byte[] bs=digest.digest(psd.getBytes());
		//3.循环遍历bs,让其生成32位字符
		//4.拼接字符串
			StringBuffer stringbuffer=new StringBuffer();
			for(byte b:bs){
				int i=b&0xff;
				String hexString=Integer.toHexString(i);
				if(hexString.length()<2){
					hexString="0"+hexString;
				}
				stringbuffer.append(hexString);
			}
			System.out.println(stringbuffer);
			return stringbuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
