/**
 * 
 */
package cn.com.karl.exercise.java.utils;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

/**
 * @author karl
 *
 */
public class HashMapLearner {
	@Test
	public void investigateHashMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "1");
		map.put("2", "1");
		System.out.println(map.keySet());
	}
}
