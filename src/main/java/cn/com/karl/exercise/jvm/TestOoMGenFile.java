/**
 * 
 */
package cn.com.karl.exercise.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author karl vm flags: -Xmx64M -Xms64M -Xmn32M -XX:+PrintGCDateStamps
 *         -XX:+PrintGCDetails -XX:+PrintTenuringDistribution
 *         -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
 *         -XX:+CMSClassUnloadingEnabled -XX:+UseCMSCompactAtFullCollection
 *         -XX:CMSInitiatingOccupancyFraction=80 -XX:PermSize=30M
 *         -XX:MaxPermSize=30M -XX:+HeapDumpOnOutOfMemoryError
 *         -XX:HeapDumpPath=/Users/karl/dev/gc_log/
 *         -Xloggc:/Users/karl/dev/gc_log/TestOoMGenFile_gc.log -XX:OnOutOfMemoryError="jmap -heap %p > /Users/karl/dev/gc_log/onerror.log"
 */
public class TestOoMGenFile {
	public static final List<Long> list = new ArrayList<Long>();

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		int i = 0;
		for (int j = 0; j < 3000; j++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getId());
						Thread.sleep(100000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
			
		}
		Thread.sleep(10000);
		
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
