/**
 * 
 */
package cn.com.karl.exercise.bigdata.avro;

import com.oppo.dc.data.avro.generated.AppInstallEvent;

/**
 * @author Administrator
 *
 */
public class AppInstallRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppInstallEvent event = new AppInstallEvent();
		event.setAction(1);
		System.out.println(event);

	}

} 
