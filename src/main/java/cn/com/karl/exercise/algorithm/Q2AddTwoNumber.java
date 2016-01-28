/**
 * 
 */
package cn.com.karl.exercise.algorithm;

/**
 * @author karl
 *
 */
public class Q2AddTwoNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ListNode l1_1 = new ListNode(2);

		ListNode l1_2 = new ListNode(4);
		ListNode l1_3 = new ListNode(3);
		l1_1.next = l1_2;
		l1_2.next = l1_3;

		ListNode l2_1 = new ListNode(5);
		ListNode l2_2 = new ListNode(6);
		ListNode l2_3 = new ListNode(4);
		l2_1.next = l2_2;
		l2_2.next = l2_3;
		ListNode result = Q2AddTwoNumber.addTwoNumbers(l1_1, l2_1);
		System.out.println(result.val);
	}

	private static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode temp1 = l1;
		ListNode temp2 = l2;
		ListNode result = null;
		ListNode nextNode = result;
		int plus = 0;
		while (temp1 != null) {
			if(temp2 == null){
				break;
			}
			int sum = temp1.val + temp2.val + plus;
			int val = sum % 10;
			plus = sum / 10;
			ListNode node = new ListNode(val);

			if (result == null) {
				result = node;
			}
			if (nextNode == null) {
				nextNode = node;
			} else {
				nextNode.next = node;
				nextNode = node;
			}

			temp1 = temp1.next;
			temp2 = temp2.next;
		}
		return result;
	}

}
