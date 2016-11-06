/**
 * 
 */
package cn.com.karl.exercise.guava;

import java.util.Collections;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;

/**
 * @author karl
 *
 */
public class OptionalTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = null;
		System.out.println(Ordering.natural().min(12, 2, 3, 2));
		
		Preconditions.checkState(false);
		Preconditions.checkNotNull(a);

	}

}
