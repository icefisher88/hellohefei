package com.springmvc.common;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.security.MessageDigest;


public class MD5Util extends MessageDigestPasswordEncoder{
	 public MD5Util() {
		super(MD5("my-secret-key"));
	}
	public final static String MD5(String s) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
	        try {
	            byte[] btInput = s.getBytes();
	            // 获得MD5摘要算法的 MessageDigest 对象
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            // 使用指定的字节更新摘要
	            mdInst.update(btInput);
	            // 获得密文
	            byte[] md = mdInst.digest();
	            // 把密文转换成十六进制的字符串形式
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    public static void main(String[] args) {
	    	/*Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	    	System.out.println(passwordEncoder.encodePassword("123456", "MD5"));*/
	    	PasswordEncoder encoder = new StandardPasswordEncoder("cetc38");//秘钥值
	    	System.out.println(encoder.encode("123456"));
	    	
	    }
}
