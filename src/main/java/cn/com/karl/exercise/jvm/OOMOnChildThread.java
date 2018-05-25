/**
 * 
 */
package cn.com.karl.exercise.jvm;

import java.util.List;

import org.testng.collections.Lists;

/**
 * @author karl
 *
 */
public class OOMOnChildThread {
	private static List<Float> list = Lists.newArrayList();

	private static void infinitedLoop() {
//		infinitedLoop();
	}

	/**
	 * vm option -Xmx32m -Xms32m Xmn16m -XX:+PrintGcDateStamps
	 * -XX:+PrintGCDetails -XX:+PrintTenuringDistribution
	 * -XX:ErrorFile=/Users/karl/dev/test_source/java_error%p.log
	 * -XX:+PrintConcurrentLocks -XX:+PrintClassHistogram
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {

				for (;;) {
					list.add(new Float(10f));
				}
			}
		});
		thread.start();
		for (;;) {
			System.out.println("main thread is active!!!");
			try {
				Thread.sleep(10000);
				infinitedLoop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
