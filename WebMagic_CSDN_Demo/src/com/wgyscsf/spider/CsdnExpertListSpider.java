package com.wgyscsf.spider;

import com.wgyscsf.utils.MyStringUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

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
	private Site site = Site.me().setDomain("blog.csdn.net").setSleepTime(300).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		// 列表页： 这里进行匹配，匹配出列表页进行相关处理。
		if ((page.getUrl()).regex(EXPERTS_LIST).match()) {
			// 遍历出div[@class=\"page_nav\"]节点下的所有超链接，这里的超链接是分页的超链接，可以进行分页。
			page.addTargetRequests(
					page.getHtml().xpath("//div[@class=\"page_nav\"]").links().regex(EXPERTS_LIST).all());// 是一个正则规则，校验使用，可以省略。

			// 获取专家列表元素
			//使用Selectable以进行页面元素的链式抽取
			Selectable selectable = page.getHtml().xpath("//dl[@class=\"experts_list\"]//dd");

			//根据xpath来获取相关信息
			for (Selectable s : selectable.nodes()) {
				String url = s.xpath("//a[@class=\"expert_name\"]").links().toString();
				String name = s.xpath("//a[@class=\"expert_name\"]/text()").toString();
				String location = s.xpath("//div[@class=\"address\"]//em/tidyText()").toString();
				String job = s.xpath("//div[@class=\"address\"]//span/tidyText()").toString();
				String articleNum = s.xpath("//div[@class=\"count_l fl\"]//b/tidyText()").toString();
				String readNum = s.xpath("//div[@class=\"count_l fr\"]//b/tidyText()").toString();

				String id_expert = MyStringUtils.getLastSlantContent(url);

				// 开始组装数据
				System.out.println(TAG + ":" + "专家id:" + id_expert + ",专家名字：" + name + ",地区：" + location + ",职业：" + job
						+ ",文章总阅读数：" + readNum + ",文章数：" + articleNum);

			}

		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new CsdnExpertListSpider()).addUrl("http://blog.csdn.net/peoplelist.html?channelid=0&page=1")
				.addPipeline(null).thread(5).run();
	}

}
