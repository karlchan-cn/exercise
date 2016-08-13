/**
 * 
 */
package cn.com.karl.exercise.java.concurrency;

/**
 * @author karl
 *
 */
public class NoVisibility {
	private static boolean flag ;
	private static int vaule;
	private static class RunThread extends Thread{

		@Override
		public void run() {
			while(!flag)
				Thread.yield();
			System.out.println(vaule);
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new RunThread().start();
		flag = true;
		vaule = 42;
	}

}
