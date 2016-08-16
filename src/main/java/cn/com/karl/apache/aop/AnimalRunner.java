/**
 * 
 */
package cn.com.karl.apache.aop;


/**
 * <p>Project:			<p>
 * <p>Module:			<p>
 * <p>Description:		<p>
 *
 * @author chenjinlong
 * @date 2016年8月16日 上午9:32:09
 */
public class AnimalRunner {

	/**
	 * @param args
	 */
	public static void main( String[] args ) {

		((Animal)new AnimalDynamicProxy().bind( new Dog() )).sound();

	}

}
