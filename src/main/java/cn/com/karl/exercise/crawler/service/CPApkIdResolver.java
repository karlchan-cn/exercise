/**
 * 
 */
package cn.com.karl.exercise.crawler.service;

/**
 * @author karl
 *
 */
public interface CPApkIdResolver {
	/**
	 * 解析第三方apk应用的id.
	 * 
	 * @param apkName
	 *            apk名称
	 * @return apkId
	 */
	String resolverCPId(String apkName);
}
