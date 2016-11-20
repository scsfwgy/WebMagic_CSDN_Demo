package com.wgyscsf.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;




/**
 * @author 高远</n>
 * 编写日期   2016-10-1上午12:59:12</n>
 * 邮箱  wgyscsf@163.com</n>
 * 博客  http://blog.csdn.net/wgyscsf</n>
 * TODO</n>
 */
public class Test1 {
	private String a;
	private String b;
	public static void main(String[] args) {
		// CsdnSpiderDao csdnSpiderDao = new CsdnSpiderDao();
		// List<String> mineIdByPager = csdnSpiderDao.getMineIdByPager(1);
		// for (String string : mineIdByPager) {
		// System.out.println(string);
		// }
		// long begin = System.currentTimeMillis();
		// CsdnSpiderDao csdnSpiderDao = new CsdnSpiderDao();
		// List<Mine> listByPager = csdnSpiderDao.listMineByPager(1);
		// for (Mine string : listByPager) {
		// System.out.println(string);
		// }
		// System.out.println("查询时间：" + (System.currentTimeMillis() - begin) +
		// "");// 10条：
		// 查询时间：131819；20条：查询时间：131143

		// Pattern p = Pattern.compile("^(?!(HKG|MAC|TWN))[A-Z]{3}$");
		// Matcher m = p.matcher("werHKGwe");
		// System.out.println(m.matches());
		// http://blog.csdn.net/wgyscsf/article/list/2
		// 查找以Java开头,任意结尾的字符串
		// Pattern pattern = Pattern
		// .compile("^http://blog.csdn.net/\\w+/article/list/[0-9]*[1-9][0-9]*$");//
		// ^((?!XXX).)*$
		// Matcher matcher = pattern
		// .matcher("http://blog.csdn.net/wgyscsf/article/list/32423");
		// boolean b = matcher.matches();
		// // 当条件满足时，将返回true，否则返回false
		// System.out.println(b);
		// 博主博客信息
		// Spider.create(new CsdnBlogListSpider())
		// .addPipeline(null)
		// .thread(5)
		// .addUrl(Contents.Blog.blogList_rootUrl + "wgyscsf" + "/"
		// + "article/list/1").run();// 这里页码（list后面的数字）只用传1即可，可以自动遍历其它页码

		// Test test = new Test();
		// test.setA("a").setB("b");
		// System.out.println(test);
		try {
			fetchNecessaryParam();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void fetchNecessaryParam() throws IOException {
		// 查看CSDN登陆页面源码发现登陆时需要post5个参数
		// name、password，另外三个在页面的隐藏域中，a good start
		// 这样登陆不行，因为第一次需要访问需要拿到上下文context
		// Document doc = Jsoup.connect(LOGIN_URL).get();
		String html = HttpUtils
				.sendGet("https://passport.csdn.net/account/login?ref=toolbar");
		Document doc = Jsoup.parse(html);
		Element form = doc.select(".user-pass").get(0);
		String lt = form.select("input[name=lt]").get(0).val();
		String execution = form.select("input[name=execution]").get(0).val();
		String _eventId = form.select("input[name=_eventId]").get(0).val();

		boolean result = false;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "wgyscsf@163.com"));
		nvps.add(new BasicNameValuePair("password", "wanggaoyuan"));
		nvps.add(new BasicNameValuePair("lt", lt));
		nvps.add(new BasicNameValuePair("execution", execution));
		nvps.add(new BasicNameValuePair("_eventId", _eventId));
		String ret = HttpUtils.sendPost(
				"https://passport.csdn.net/account/login?ref=toolbar", nvps);
		if (ret.indexOf("redirect_back") > -1) {
			System.out.println("登陆成功。。。。。");
			result = true;
		} else if (ret.indexOf("登录太频繁") > -1) {
			System.out.println("登录太频繁，请稍后再试。。。。。");
		} else {
			System.out.println("登陆失败。。。。。");
		}
		HttpUriRequest httpUriRequest = new HttpPost(
				"http://blog.csdn.net/wgyscsf");
		// HttpGet httpUriRequest = new HttpGet("http://blog.csdn.net/wgyscsf");
		httpUriRequest
				.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpUriRequest.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpUriRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpUriRequest.setHeader("Connection", "keep-alive");
		httpUriRequest.setHeader("Host", "blog.csdn.net");
		httpUriRequest
				.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		httpUriRequest
				.addHeader(
				"userAgent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

		HttpResponse response = HttpUtils.httpClient.execute(httpUriRequest);
		InputStream content = response.getEntity().getContent();
		// 将inputstream转化为reader，并使用缓冲读取，还可按行读取内容
		BufferedReader br = new BufferedReader(new InputStreamReader(content));
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		// CloseableHttpClient httpclient = HttpClients.createDefault();
		//
		// try {
		// // 以get方法执行请求
		// HttpGet httpGet = new HttpGet("http://blog.csdn.net/wgyscsf");
		// httpGet.setHeader("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// httpGet.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		// httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		// httpGet.setHeader("Connection", "keep-alive");
		// httpGet.setHeader("Host", "blog.csdn.net");
		// httpGet.setHeader(
		// "User-Agent",
		// "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		// httpGet.addHeader(
		// "userAgent",
		// "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
		// // 获得服务器响应的所有信息
		// CloseableHttpResponse responseGet = httpclient.execute(httpGet);
		// try {
		// System.out.println(responseGet.getStatusLine());
		// // 获得服务器响应的消息体（不包括http head）
		// HttpEntity entity = responseGet.getEntity();
		//
		// if (entity != null) {
		// // 获得响应字符集编码
		// ContentType contentType = ContentType.getOrDefault(entity);
		// Charset charset = contentType.getCharset();
		// InputStream is = entity.getContent();
		// // 将inputstream转化为reader，并使用缓冲读取，还可按行读取内容
		// BufferedReader br = new BufferedReader(
		// new InputStreamReader(is, charset));
		// String line = null;
		// while ((line = br.readLine()) != null) {
		// System.out.println(line);
		// }
		// is.close();
		// }
		// } finally {
		// responseGet.close();
		// }
		//
		// } finally {
		// httpclient.close();
		// }
	}
	public String getA() {
		return a;
	}

	public Test1 setA(String a) {
		this.a = a;
		return this;
	}

	public String getB() {
		return b;
	}

	public Test1 setB(String b) {
		this.b = b;
		return this;
	}

	@Override
	public String toString() {
		return "Test [a=" + a + ", b=" + b + "]";
	}

	@Test
	public void testSrc() {
		System.err.println("--" + getClass().getResource("/").getPath()
				+ "config/config.ini");
		File file = new File(getClass().getResource("/").getPath()
				+ "config/config.ini");
		System.out.println(file.isFile());
	}

}
