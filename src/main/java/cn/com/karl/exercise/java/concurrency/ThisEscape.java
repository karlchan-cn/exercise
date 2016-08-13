/**
 * 
 */
package cn.com.karl.exercise.java.concurrency;

/**
 * @author karl
 *
 */
public class ThisEscape {

	public ThisEscape(ThisEscapeListener listener) {
		String a ="a";
		listener.setThread(new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(this);
				System.out.println(a);
				System.out.println(this.toString());
			}
		}));
	}

	public void sing() {
		System.out.println("sing");
	}

	public static void main(String[] args) {
		ThisEscapeListener listener = new ThisEscapeListener();
		new ThisEscape(listener);
		listener.getThread().start();
	}
}
