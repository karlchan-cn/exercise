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
	public static void main( String[] args ) {
		ListNode l1_1 = new ListNode( 2 );

		ListNode l1_2 = new ListNode( 4 );
		ListNode l1_3 = new ListNode( 3 );
		l1_1.next = l1_2;
		l1_2.next = l1_3;

		ListNode l2_1 = new ListNode( 5 );
		ListNode l2_2 = new ListNode( 6 );
		ListNode l2_3 = new ListNode( 4 );
		l2_1.next = l2_2;
		l2_2.next = l2_3;
		ListNode result = Q2AddTwoNumber.addTwoNumbers( l1_1, l2_1 );
		System.out.println( result.val );
	}

	private static class ListNode {

		int val;

		ListNode next;

		ListNode( int x ) {
			val = x;
		}
	}

	public static ListNode addTwoNumbers( ListNode l1, ListNode l2 ) {
		ListNode temp1 = l1;
		ListNode temp2 = l2;
		ListNode result = null;
		ListNode nextNode = result;
		int plus = 0;
		while( true ) {
			if( temp1 == null && temp2 == null ) {
				if( plus > 0 && nextNode != null ) {
					ListNode node = new ListNode( plus );
					nextNode.next = node;
				}
				break;
			}
			int val1 = 0;
			int val2 = 0;
			if( temp1 != null ) {
				val1 = temp1.val;
			}
			if( temp2 != null ) {
				val2 = temp2.val;
			}

			int sum = val1 + val2 + plus;
			int val = sum % 10;
			plus = sum / 10;
			ListNode node = new ListNode( val );

			if( result == null ) {
				result = node;
			}
			if( nextNode == null ) {
				nextNode = node;
			} else {
				nextNode.next = node;
				nextNode = node;
			}

			if( temp1 != null ) {
				temp1 = temp1.next;
			}
			if( temp2 != null ) {
				temp2 = temp2.next;
			}
		}
		return result;
	}

}
