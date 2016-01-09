package cn.com.karl.exercise.java.concurrency;

/**
 * 
 */

/**
 * @author karl
 *
 */
public class TestMaxThread implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Thread.sleep(10000);
		for (int i = 0; i < 2000; i++) {
			try {
				if(i == 500)
					Thread.sleep(300000);
				Thread thread = new Thread(new TestMaxThread());
				thread.start();
			} catch (Exception e) {

			}

			System.out.println("start thread:" + (i + 1));
		}

	}

	public void overFlow() {
		overFlow();
	}

	@Override
	public void run() {
		try {
			overFlow();
			Thread.sleep(10000000);
		} catch (Exception e) {
			try {
				Thread.sleep(10000000);
			} catch (Exception e2) {

			}
		}

	}

}
