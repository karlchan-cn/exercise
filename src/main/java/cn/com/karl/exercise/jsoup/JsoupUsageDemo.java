package cn.com.karl.exercise.jsoup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

public class JsoupUsageDemo {

	public static void getGzipContent() throws IOException {
		String body = Jsoup
				.connect(
						"http://m.baidu.com/appsrv?action=detail&native_api=1&docid=8084395" )
				.ignoreContentType( true ).execute().body();
		JSONObject json = JSON.parseObject( body );
		System.out.println( body );
		System.out.println( json.toString() );
	}

	public static void main( String[] args ) throws Exception {
		URL url = new URL(
				"http://m.baidu.com/appsrv?action=detail&native_api=1&docid=8084395" );

		JsoupUsageDemo.getGzipContent();
		Set<String> topSet = getToplistPkgs();
		//FileUtils.write( new File( "C:\\Users\\chenjinlong\\Desktop\\worklog\\平板适配工作\\toplist.txt" ), Iterables.toString( topSet ), Charsets.UTF_8 );
		Set<String> catSet = getPkgsByCat();
		//FileUtils.write( new File( "C:\\Users\\chenjinlong\\Desktop\\worklog\\平板适配工作\\catpkg.txt" ), Iterables.toString( catSet ), Charsets.UTF_8 );
		Sets.union( topSet, catSet );
		FileUtils.write( new File( "C:\\Users\\chenjinlong\\Desktop\\worklog\\平板适配工作\\all.txt" ), Iterables.toString( Sets.union( topSet, catSet ) ), Charsets.UTF_8 );
		
	}

	/**
	 * @param padPkgSet
	 * @throws IOException
	 */
	private static Set<String> getToplistPkgs() throws IOException {
		Set<String> padPkgSet = Sets.newHashSet();
		String miUrl = "http://app.xiaomi.com/topList?type=pad&page=";
		for( int i = 1; i <= 42; ++i ) {
			Document doc = Jsoup.connect( miUrl + i ).userAgent( "Mozilla" ).get();
			Elements els = doc.select( ".applist-wrap .applist li h5 a" );
			for( Element element : els )
				padPkgSet.add( StringUtils.removeStart( element.attr( "href" ), "/details?id=" ) );
		}
		System.out.println( "APP with pad version sum: " + padPkgSet.size() );
		return padPkgSet;
	}

	private static final Set<String> getPkgsByCat() throws Exception {
		Set<String> catSet = Sets.newHashSet();
		String miApi = "http://app.xiaomi.com/categotyAllListApi?categoryId=@c&page=@p&pageSize=@s&type=pad";
		String[] cats = new String[] { "2", "5", "12", "9", "14", "1", "11", "27", "7", "10", "4", "6", "8", "13" };
		for( String cat : cats ) {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet( StringUtils.replaceEach( miApi, new String[] { "@c", "@p", "@s" }, new String[] { cat, "1", "30" } ) );
			CloseableHttpResponse response = client.execute( get );
			HttpEntity entity = response.getEntity();
			JSONObject json = JSONObject.parseObject( IOUtils.toString( entity.getContent(), "UTF-8" ), JSONObject.class );
			EntityUtils.consume( entity );
			int count = json.getInteger( "count" );
			int pageCount = ( count - 1 ) / 30 + 1;
			for( int p = 1; p <= pageCount; ++p ) {
				get = new HttpGet( StringUtils.replaceEach( miApi, new String[] { "@c", "@p", "@s" }, new String[] { cat, String.valueOf( p ), "30" } ) );
				response = client.execute( get );
				entity = response.getEntity();
				json = JSONObject.parseObject( IOUtils.toString( entity.getContent(), "UTF-8" ), JSONObject.class );
				EntityUtils.consume( entity );
				JSONArray datas = json.getJSONArray( "data" );
				for( Object obj : datas ) {
					JSONObject data = ( JSONObject )obj;
					catSet.add( data.getString( "packageName" ) );
				}
			}
		}
		System.out.println( "APP with pad version sum: " + catSet.size() );
		return catSet;
	}
}
