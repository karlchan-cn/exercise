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
	public static final List<Long> list = new ArrayList<Long>();

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		int i = 0;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					list.add(new Long(1));
				}
			}
		}).start();
		Thread.sleep(999999999999999l);
	}

}
