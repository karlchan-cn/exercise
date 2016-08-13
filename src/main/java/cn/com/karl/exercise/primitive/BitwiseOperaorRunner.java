package cn.com.karl.exercise.primitive;

import java.util.concurrent.ConcurrentHashMap;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Weigher;

public class BitwiseOperaorRunner {
	public static void main(String[] args) {

		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
		map.put("test", 1);
		System.out.println(map.size());
		Cache<String, String> cache = CacheBuilder.newBuilder().maximumWeight(10000)
				.weigher(new Weigher<String, String>() {

					@Override
					public int weigh(String key, String value) {

						return 0;
					}
				}).build();
	}

}
