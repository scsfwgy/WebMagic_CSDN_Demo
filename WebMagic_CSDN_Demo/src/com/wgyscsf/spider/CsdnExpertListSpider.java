package com.wgyscsf.spider;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.wgyscsf.utils.MyStringUtils;

/**
 * @author wgy</n> 编写日期 2016-9-24下午7:25:36</n> 邮箱 wgyscsf@163.com</n> 博客
 *         http://blog.csdn.net/wgyscsf</n> TODO</n>
 */
public class CsdnExpertListSpider implements PageProcessor {
	// 这个是列表页
	public static final String EXPERTS_LIST = "http://blog\\.csdn\\.net/peoplelist\\.html\\?channelid=0\\&page="
			+ "\\w+";
	private static final String TAG = "CsdnExpertListSpider";
	// 博客详情页
	// public static final String EXPERTS_DETAILS =
	// "http://blog.csdn.net/\\w+";// \\w+是一个匹配符，可以匹配任意字段
	private Site site = Site
			.me()
			.setDomain("blog.csdn.net")
			.setSleepTime(300)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		// 列表页： 这里进行匹配，匹配出列表页进行相关处理。
		if ((page.getUrl()).regex(EXPERTS_LIST).match()) {
			// 遍历出div[@class=\"page_nav\"]节点下的所有超链接，这里的超链接是分页的超链接，可以进行分页。
			page.addTargetRequests(page.getHtml()
					.xpath("//div[@class=\"page_nav\"]").links()
					.regex(EXPERTS_LIST).all());// 是一个正则规则，校验使用，可以省略。

			// 获取专家列表元素
			Elements expertList_elements = page.getHtml().getDocument()
					.getElementsByClass("experts_list");

			for (Element element : expertList_elements) {
				// 两个根节点
				Element tag_dt = element.getElementsByTag("dt").get(0);
				Element tag_dd = element.getElementsByTag("dd").get(0);
				// 获取用户id
				String id_expert = MyStringUtils.getLastSlantContent(tag_dt
						.getElementsByTag("a").get(0).attributes().get("href"));
				// 获取用户头像
				String headImg = tag_dt.getElementsByTag("a").get(0)
						.getElementsByClass("expert_head").get(0).attributes()
						.get("src");
				// 获取用户名
				String name = tag_dd.getElementsByTag("a").get(0).text();
				String address_job = tag_dd.getElementsByTag("div").get(0)
						.text();
				// 获取地址
				String localtion = MyStringUtils
						.getBeforeVercitalLine(address_job);
				// 获取职位
				String job = MyStringUtils.getAfterVercitalLine(address_job);
				// 获取文章数
				String articleNums = tag_dd.getElementsByTag("div").get(1)
						.getElementsByTag("div").get(1).getElementsByTag("b")
						.get(0).text();
				// 获取阅读数
				// TODO:这里逻辑不太对呀，但是结果正确
				String readNums = tag_dd.getElementsByTag("div").get(1)
						.getElementsByTag("div").get(2).getElementsByTag("b")
						.get(0).text();

				// 开始组装数据
				System.out.println(TAG + ":" + "专家id:" + id_expert + ",专家名字："
						+ name + ",地区：" + localtion + ",职业：" + job + ",文章总阅读数："
						+ readNums + ",文章数：" + articleNums);

			}

		}
	}
	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new CsdnExpertListSpider())
				.addUrl("http://blog.csdn.net/peoplelist.html?channelid=0&page=1")
				.addPipeline(null).thread(5).run();
	}

}
