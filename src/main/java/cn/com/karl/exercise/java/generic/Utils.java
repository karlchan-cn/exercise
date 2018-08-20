/**
 * Copyright (c) 2018 OPPO, All Rights Reserved.
 * 
 * Project Name:exercise 
 * File Name:Utils.java 
 * Package Name:cn.com.karl.exercise.java.generic 
*/

package cn.com.karl.exercise.java.generic;

/**
 * @author 80166776 2018年8月7日 下午1:31:10
 */
public class Utils {
	public static <T> boolean compare(Box<T> p1, Box<T> p2) {
		return p1.get().equals(p2.get());
	}
}
