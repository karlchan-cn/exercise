package cn.com.karl.exercise.jsoup;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsoupUsageDemo {
	public static void getGzipContent() throws IOException {
		String body = Jsoup
				.connect(
						"http://m.baidu.com/appsrv?action=detail&native_api=1&docid=8084395")
				.ignoreContentType(true).execute().body();
		JSONObject json = JSON.parseObject(body);
		System.out.println(body);
		System.out.println(json.toString());
	}

	public static void main(String[] args) throws IOException {
		URL url = new URL(
				"http://m.baidu.com/appsrv?action=detail&native_api=1&docid=8084395");
		JsoupUsageDemo.getGzipContent();
	}
}
