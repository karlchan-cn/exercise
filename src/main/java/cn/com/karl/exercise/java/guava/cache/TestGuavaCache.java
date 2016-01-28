package cn.com.karl.exercise.java.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class TestGuavaCache {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String name;
	private int sex;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public static void main(String[] args) throws Exception{
		LoadingCache<Integer, TestGuavaCache> cache = CacheBuilder.newBuilder()
				.maximumSize(50000).initialCapacity(1000)
				.build(new CacheLoader<Integer, TestGuavaCache>() {

					@Override 
					public TestGuavaCache load(Integer key) throws Exception {

						return new TestGuavaCache();
					}

				});
		//cache.get
		cache.get(1);
	}
}
