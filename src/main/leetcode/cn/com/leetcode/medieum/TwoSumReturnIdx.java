package cn.com.leetcode.medieum;

/**
 * URL:
 * https://leetcode.com/problems/two-sum/
 * Given an array of integers, find two numbers such that they add up to a
 * specific target number.
 * 
 * The function twoSum should return indices of the two numbers such that they
 * add up to the target, where index1 must be less than index2. Please note that
 * your returned answers (both index1 and index2) are not zero-based.
 * 
 * You may assume that each input would have exactly one solution.
 * 
 * Input: numbers={2, 7, 11, 15}, target=9 Output: index1=1, index2=2
 * 
 * @author karl
 *
 */
public class TwoSumReturnIdx {

	public static void main(String[] args) {
		// result = twoSum(new int[] { 2, 7, 11, 15 }, 9);
		int[] result = twoSum(new int[] { 0, 4, 3, 0 }, 0);
		System.out.println(result.length);
	}

	public static int[] twoSum(int[] nums, int target) {
		int idx1 = 0;
		int idx2 = 0;
		int addCount = 0;
		int[] result = new int[2];
		for (int i = 0; i < nums.length; i++) {
			idx1 = nums[i];
			addCount = target - idx1;
			for (int j = i + 1; j < nums.length; j++) {
				idx2 = nums[j];
				if (idx2 == addCount) {
					result[0] = i + 1;
					result[1] = j + 1;
					return result;
				}
			}
		}
		return result;
	}
}