/**
 * 
 */
package cn.com.karl.exercise.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author karl
 *
 */
public class TestOoMGenFile {
	public static final List<Object> list = new ArrayList<Object>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0;
		while (true) {
			list.add(new ArrayList<String>());
			System.out.println(i++);
		}
	}

}
