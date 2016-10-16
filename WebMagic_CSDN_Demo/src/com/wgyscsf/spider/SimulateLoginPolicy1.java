package com.wgyscsf.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.wgyscsf.utils.HttpUtils;

/**
 * @author 高远</n>
 * 编写日期   2016-9-24下午7:25:36</n>
 * 邮箱  wgyscsf@163.com</n>
 * 博客  http://blog.csdn.net/wgyscsf</n>
 * TODO</n>
 */
public class SimulateLoginPolicy1 {
	static boolean result = false;
	public static void main(String[] args) {
		// 登录页面，获取以及给服务器传递必要信息
		loginCsdnPager();
		// 登陆后即可以进入登陆后的页面。
		try {
			loginedPager();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 登陆后的页面获取，全局可以用，比如http://my.csdn.net/wgyscsf也是可以用的。
	 */
	private static void loginedPager() throws IOException,
			ClientProtocolException {
		//构造需要访问的页面
		HttpUriRequest httpUriRequest = new HttpPost(
				"http://blog.csdn.net/wgyscsf");
		// 添加必要的头信息
		httpUriRequest
				.setHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpUriRequest.setHeader("Accept-Encoding", "gzip,deflate,sdch");
		httpUriRequest.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
		httpUriRequest.setHeader("Connection", "keep-alive");
		// 模拟浏览器，否则CSDN服务器限制访问
		httpUriRequest
				.setHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
		// 【特别注意】：这个一定需要和登录用同一个“httpClient”，不然会失败。登陆信息全部在“httpClient”中保存
		HttpResponse response = HttpUtils.httpClient.execute(httpUriRequest);
		InputStream content = response.getEntity().getContent();
		// 将inputstream转化为reader，并使用缓冲读取，还可按行读取内容
		BufferedReader br = new BufferedReader(new InputStreamReader(content));
		String line = "";
		String result = "";
		while ((line = br.readLine()) != null) {
			result += line;
		}
		br.close();

		// 这里是获取的页码，就可以进行界面解析处理了。
		System.out.println(result);
	}

	/**
	 * 登录页面
	 */
	private static void loginCsdnPager() {
		String html = HttpUtils
				.sendGet("https://passport.csdn.net/account/login?ref=toolbar");// 这个是登录的页面
		Document doc = Jsoup.parse(html);
		// 获取表单所在的节点
		Element form = doc.select(".user-pass").get(0);
		// 以下三个是服务器给的标记信息，必须具有该信息登录才有效。
		String lt = form.select("input[name=lt]").get(0).val();
		String execution = form.select("input[name=execution]").get(0).val();
		String _eventId = form.select("input[name=_eventId]").get(0).val();

		// 开始构造登录的信息：账号、密码、以及三个标记信息
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "wgyscsf@163.com"));
		nvps.add(new BasicNameValuePair("password", "wanggaoyuan"));
		nvps.add(new BasicNameValuePair("lt", lt));
		nvps.add(new BasicNameValuePair("execution", execution));
		nvps.add(new BasicNameValuePair("_eventId", _eventId));
		// 开始请求CSDN服务器进行登录操作。一个简单封装，直接获取返回结果
		String ret = HttpUtils.sendPost(
				"https://passport.csdn.net/account/login?ref=toolbar", nvps);
		// ret中会包含以下信息，进行判断即可。
		if (ret.indexOf("redirect_back") > -1) {
			System.out.println("登陆成功。。。。。");
			result = true;
		} else if (ret.indexOf("登录太频繁") > -1) {
			System.out.println("登录太频繁，请稍后再试。。。。。");
			return;
		} else {
			System.out.println("登陆失败。。。。。");
			return;
		}
	}
}
