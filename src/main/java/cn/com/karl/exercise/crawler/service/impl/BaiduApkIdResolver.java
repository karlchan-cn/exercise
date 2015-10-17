/**
 * 
 */
package cn.com.karl.exercise.crawler.service.impl;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.karl.exercise.crawler.service.CPApkIdResolver;

/**
 * @author karl
 *
 */
public class BaiduApkIdResolver implements CPApkIdResolver {
	private final static Logger logger = LoggerFactory
			.getLogger(BaiduApkIdResolver.class);
	/**
	 * 百度搜索URL,用作搜索百度对应的应用ID。
	 */
	private static final String CONST_BD_SEARCH_URL = "http://shouji.baidu.com/s?wd=";

	public String resolverCPId(String apkName) {
		String resultStr = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			String apkSearchUrlString = CONST_BD_SEARCH_URL + apkName;
			HttpGet httpGet = new HttpGet(apkSearchUrlString);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			Document document = Jsoup.parse(response.getEntity().getContent(),
					"UTF-8", apkSearchUrlString);
			Elements eles = document.select(".s-special-type .main-icon a");
			if (eles.size() > 0) {
				String href = eles.get(0).attr("href").split("&")[0];
				resultStr = href.substring(href.indexOf("docid=") + 6);
			}
			logger.debug("the apk id is {}", resultStr);
		} catch (IOException ioe) {
			logger.error("close httpclient got io error:{}", ioe.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("close httpclient got error:{}", e.getMessage());
			}
		}
		return resultStr;
	}
}
