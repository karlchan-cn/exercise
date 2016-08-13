/**
 * 
 */
package cn.com.karl.exercise.algorithm;

import java.util.Arrays;

/**
 * @author karl
 *
 */
public class SortingAlgorithm {
	public static void main(String[] args) {
		Integer i = Integer.valueOf(1);
		Integer j = i;
		System.out.println(System.identityHashCode(i));
		System.out.println(System.identityHashCode(j));
		i += 1;
		System.out.println(System.identityHashCode(i));
		int[] array = { 5, 2, 1, 3, 6, 87, 5, 2, 4, 1 };
		System.out.println(Arrays.toString(insertDescendSorting(array)));
		System.out.println(Arrays.toString(insertAscendSorting(array)));
	}

	public static int[] insertAscendSorting(int[] array) {
		if (validate(array)) {
			for (int i = 1; i < array.length; ++i) {
				int value = array[i];
				int j = i - 1;
				while (j >= 0 && array[j] < value) {
					array[j + 1] = array[j];
					--j;
				}
				array[j + 1] = value;
			}
		}
		return array;
	}

	public static int[] insertDescendSorting(int[] array) {
		if (validate(array)) {
			for (int i = 1; i < array.length; ++i) {
				for (int j = i - 1; j >= 0; --j) {
					int pre = array[j];
					int cur = array[j + 1];
					if (cur < pre) {
						array[j] = cur;
						array[j + 1] = pre;
						continue;
					}
					break;
				}
			}
		}

		return array;
	}

	private static boolean validate(int[] array) {
		return array != null && array.length > 0;
	}
}
