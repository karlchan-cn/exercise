/**
 * 
 */
package cn.com.karl.apache.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

/**
 * @author karl
 *
 */
public class ZookeeperExecutor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
