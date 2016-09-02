package cn.com.karl.exercise.cocurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(10);
		service.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("runnable");

			}
		});

	}
}
