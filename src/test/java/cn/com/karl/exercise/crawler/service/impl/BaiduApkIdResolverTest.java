package cn.com.karl.exercise.crawler.service.impl;

import mockit.Tested;

import org.testng.Assert;
import org.testng.annotations.Test;

public class BaiduApkIdResolverTest {

	@Tested
	BaiduApkIdResolver baiduApkIdResolver;

	@Test
	public void resolverCPId() {
		Assert.assertNotNull( baiduApkIdResolver.resolverCPId( "微信" ) );
	}

	public static void main( String[] args ) {
		String string = "/mnt/mstore/fileserver/filetmp/gifttmp\1\f95339af22104fafb18cf80fc88e773b.jpg";
		System.out.println( string.replaceAll( "\\\\", "/" ) );
	}
}
