package cn.com.karl.exercise.cocurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunnableTest implements Runnable {

	public RunnableTest(int runCount) {
		this.runCount = runCount;
	}

	private int runCount;

	public static void main(String[] args) throws Exception {
		Thread a = new Thread(new RunnableTest(3));
		a.start();
		try {
			a.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; ++i)
			service.execute(new RunnableTest(3));
		service.shutdown();
		service.awaitTermination(10, TimeUnit.SECONDS);
		System.out.println("main thread stopped");
	}

	@Override
	public void run() {
		int count = this.runCount;
		while (count > 0) {
			System.out.println(Thread.currentThread().getId() + " running count down: " + count);
			Thread.yield();
			--count;

		}

	}

}
