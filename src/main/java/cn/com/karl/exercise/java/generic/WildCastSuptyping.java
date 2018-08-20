/**
 * 
 */
package cn.com.karl.exercise.java.generic;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
public class WildCastSuptyping {
	public static void main(String[] args) {
		pick("A", new ArrayList<String>());
		List<Cat> cats = Lists.newArrayList();
		List<? extends Cat> exCats = cats;
		List<? extends Animal> animals = cats;
	}

	static <T> T pick(T a, T b) {
		return b;
	}
}
