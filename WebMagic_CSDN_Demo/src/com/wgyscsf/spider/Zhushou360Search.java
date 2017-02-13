package com.wgyscsf.spider;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author 高远</n>
 * 编写日期   2017-2-12下午5:09:38</n>
 * 邮箱  wgyscsf@163.com</n>
 * 博客  http://blog.csdn.net/wgyscsf</n>
 * TODO</n>
 */
/**
 * 360应用市场搜索，爬取页面：http://zhushou.360.cn/search/index/?kw=keyword
 * 
 */
public class Zhushou360Search {
	private static String TAG = "CoreService";

	/**
	 * 
	 * 根据关键字获取360应用市场搜索结果列表信息。
	 */
	public synchronized static List<Info> getSearchInfos(String searchWord,
			String pager) {
		// 搜索的链接
		String searchUrl = "http://zhushou.360.cn/search/index/?kw=";
		// 数据存放的集合
		List<Info> infoList = new ArrayList<Info>();
		// 目标搜索url
		if (pager == null || pager == "") {
			searchUrl += searchWord;
		} else {
			searchUrl += searchWord + "&page=" + pager;
		}
		// 获取源码
		String html = look(searchUrl);
		// 所去所有节点
		Document doc = Jsoup.parse(html);
		// 获取目标根节点
		Elements elements = doc.getElementsByClass("SeaCon");
		// 只有一个元素节点
		Element element = elements.get(0);
		// 获取搜索信息
		String searchInfo = element.getElementsByClass("title_tr").get(0)
				.getElementsByClass("red").get(0).text();
		// System.out.println(TAG + "：" + searchInfo);
		// 找到核心数据li列表
		Elements liElements = element.getElementsByTag("li");
		for (Element e : liElements) {
			Info info = new Info();
			// 设置搜索信息
			info.setSearchNums(searchInfo);
			// 获取app图标
			String appIocn = e.getElementsByTag("dt").get(0)
					.getElementsByTag("a").get(0).getElementsByTag("img")
					.get(0).attr("_src");
			info.setImgPath(appIocn);
			// 获取应用名
			String appName = e.getElementsByTag("dd").get(0)
					.getElementsByTag("a").get(0).text();
			info.setName(appName);
			// 获取描述信息
			String appTips = e.getElementsByTag("dd").get(0)
					.getElementsByTag("p").get(0).text();
			info.setTips(appTips);
			// 找到评分以及下载量
			String starsAndDownInfo = e.getElementsByClass("sdlft").get(0)
					.text();
			info.setStars(starsAndDownInfo.substring(0,
					starsAndDownInfo.indexOf("分 ") + 1));
			info.setDownNums(starsAndDownInfo.substring(starsAndDownInfo
					.indexOf("分 ") + 2));
			// System.out.println(TAG + ":" + info.getStars() + "*"
			// + info.getDownNums());// ok
			// 开始获取下载链接
			String downPath = e.getElementsByClass("seaDown").get(0)
					.getElementsByTag("a").attr("href");
			info.setDownPath(downPath);
			infoList.add(info);
			System.out.println(TAG + ":" + info);
		}
		return infoList;

	}

	private static String look(String path) {

		String html = null;

		try {
			URL url = new URL(path);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			conn.setConnectTimeout(5000);

			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
				}
				html = bos.toString("UTF-8");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return html;
	}
	public static void main(String[] args) {
		List<Info> infos = getSearchInfos("qq", "");// 搜索关键字以及搜索结果的页码（可为空，默认为1）
		System.out.println("单个信息如下：" + infos.get(0));
	}
}

class Info {
	private String id;
	private String name;
	private String imgPath;
	private String downPath;
	private String tips;
	private String downNums;
	private String stars;
	private String searchNums;// 多余字段，统计搜索到的软件的数量

	public String getSearchNums() {
		return searchNums;
	}

	public void setSearchNums(String searchNums) {
		this.searchNums = searchNums;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDownPath() {
		return downPath;
	}

	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getDownNums() {
		return downNums;
	}

	public void setDownNums(String downNums) {
		this.downNums = downNums;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	@Override
	public String toString() {
		return "Info [id=" + id + ", name=" + name + ", imgPath=" + imgPath
				+ ", downPath=" + downPath + ", tips=" + tips + ", downNums="
				+ downNums + ", stars=" + stars + ", searchNums=" + searchNums
				+ "]";
	}

}
