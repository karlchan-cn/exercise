package cn.com.karl.exercise.crawler.service.impl;

import mockit.Tested;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BaiduApkIdResolverTest {
	@Tested
	BaiduApkIdResolver baiduApkIdResolver;

	@Test
	public void resolverCPId() {
		Assert.assertNotNull(baiduApkIdResolver.resolverCPId("微信"));
	}
}
