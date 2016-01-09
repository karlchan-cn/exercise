package cn.com.karl.exercise.java.ecoding;

import java.util.Arrays;

public class UnicodeTest {


	public static void main(String[] args) throws Exception{
		String aString = "你";
	
		System.out.println(aString.getBytes("UTF8").length);
		System.out.println(aString.getBytes("GBK").length);
		System.out.println(Arrays.toString(aString.getBytes()));;
		System.out.println(Arrays.toString(aString.getBytes("UTF8")));;
		System.out.println(new String(aString.getBytes("GBK")));
	}
}
