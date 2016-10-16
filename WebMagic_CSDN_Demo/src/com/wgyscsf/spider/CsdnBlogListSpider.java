package com.wgyscsf.spider;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import com.wgyscsf.utils.MyStringUtils;

/**
 * @author wgyscsf</n> 编写日期 2016-9-24下午7:25:36</n> 邮箱 wgyscsf@163.com</n> 博客
 *         http://blog.csdn.net/wgyscsf</n> TODO</n>
 */
public class CsdnBlogListSpider extends BaseSpider {
	private Site site = Site
			.me()
			.setDomain("blog.csdn.net")
			.setSleepTime(300)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		// 列表页： 这里进行匹配，匹配出列表页进行相关处理。在列表页我们获取必要信息。对于全文、评论、顶、踩在文章详情中。
		if ((page.getUrl()).regex(
				"^http://blog.csdn.net/\\w+/article/list/[0-9]*[1-9][0-9]*$")
				.match()) {
			// 遍历出页码：遍历出div[@class=\"pagelist\"]节点下的所有超链接，该链接下是页码链接。将其加入到爬虫队列。【核心代码】
			page.addTargetRequests(page
					.getHtml()
					.xpath("//div[@class=\"list_item_new\"]//div[@class=\"pagelist\"]")
					.links().all());
			// 作者
			Selectable links = page.getHtml()
					.xpath("//div[@class=\"header\"]//div[@id=\"blog_title\"]")
					.links();
			String blogUrl = links.get();
			String id_author = MyStringUtils.getLastSlantContent(blogUrl);
			id_author = id_author != null ? id_author : "获取作者id失败";
			// System.out.println(TAG + author);
			// 获取列表最外层节点的所有子节点。经过分析可以知道子节点有3个，“置顶文章”列表和“普通文章列表”和分页div。
			List<String> out_div = page.getHtml()
					.xpath("//div[@class=\"list_item_new\"]/div").all();

			// 判断是否存在置顶文章：如何div的个数为3说明存在置顶文章，否则不存在置顶文章。
			if (out_div.size() == 3) {
				// 存在
				processTopArticle(out_div.get(0), id_author);
				processCommArticle(out_div.get(1), id_author);
			} else if (out_div.size() == 2) {
				// 不存在
				processCommArticle(out_div.get(0), id_author);
			} else {
				System.err.println(TAG + ":逻辑出错");
			}

		} else if (page.getUrl()
				.regex("http://blog.csdn.net/\\w+/article/details/\\w+")
				.match()) {
			// 这里的逻辑还没处理，主要是为了获取全文、标签、顶、踩、评论等在列表页获取不到的数据
		}
	}

	/**
	 * 处理普通文章列表
	 */
	private void processCommArticle(String str, String id_author) {
		// 从列表页获取列表信息
		List<String> all;
		all = new Html(str).xpath("//div[@id=\"article_list\"]/div").all();
		if (!all.isEmpty())
			for (String string : all) {
				// 这里开始获取具体内容

				// 单项第一部分：article_title
				// 文章地址
				String detailsUrl = new Html(string)
						.xpath("//div[@class='article_title']//span[@class='link_title']//a/@href")
						.toString();
				// 文章id
				String id_blog = MyStringUtils.getLastSlantContent(detailsUrl);
				// 文章标头
				String title = new Html(string)
						.xpath("//div[@class='article_title']//span[@class='link_title']//a/text()")
						.toString();
				// 文章类型
				String type = getArticleType(string);
				// 单项第二部分：article_description
				String summary = new Html(string).xpath(
						"//div[@class='article_description']//text()")
						.toString();
				// 单项第三部分：article_manage
				String publishDateTime = new Html(string)
						.xpath("//div[@class='article_manage']//span[@class='link_postdate']//text()")
						.toString();
				// 阅读量
				String viewNums = new Html(string)
						.xpath("//div[@class='article_manage']//span[@class='link_view']//text()")
						.toString();
				viewNums = MyStringUtils.getStringPureNumber(viewNums);
				// 评论数
				String commentNums = new Html(string)
						.xpath("//div[@class='article_manage']//span[@class='link_comments']//text()")
						.toString();
				commentNums = MyStringUtils.getStringPureNumber(commentNums);
				// 开始组织数据
				System.out.println(TAG + ":,文章id：" + id_blog + ",文章标头：" + title
						+ ",文章类型('0':原创；'1'：转载；'2'：翻译):" + type + ",发表日期："
						+ publishDateTime + ",阅读量:" + viewNums + ",评论数："
						+ commentNums + ",文章地址：" + detailsUrl + ",文章摘要："
						+ summary + "");

			}

	}

	/**
	 * 处理置顶文章列表
	 */
	private void processTopArticle(String topListDiv, String id_author) {
		// 从列表页获取列表信息
		List<String> all;
		all = new Html(topListDiv).xpath("//div[@id=\"article_toplist\"]/div")
				.all();
		if (!all.isEmpty())
			for (String string : all) {
				// 单项第一部分：article_title
				// 文章地址
				String detailsUrl = new Html(string)
						.xpath("//div[@class='article_title']//span[@class='link_title']//a/@href")
						.toString();
				// 文章id
				String id_blog = MyStringUtils.getLastSlantContent(detailsUrl);
				// 文章标头
				String title = new Html(string)
						.xpath("//div[@class='article_title']//span[@class='link_title']//a/text()")
						.toString();
				// 文章类型
				String type = getArticleType(string);
				// 单项第二部分：article_description
				String summary = new Html(string).xpath(
						"//div[@class='article_description']//text()")
						.toString();
				// 单项第三部分：article_manage
				String publishDateTime = new Html(string)
						.xpath("//div[@class='article_manage']//span[@class='link_postdate']//text()")
						.toString();
				// 阅读量
				String viewNums = new Html(string)
						.xpath("//div[@class='article_manage']//span[@class='link_view']//text()")
						.toString();
				viewNums = MyStringUtils.getStringPureNumber(viewNums);
				// 评论数
				String commentNums = new Html(string)
						.xpath("//div[@class='article_manage']//span[@class='link_comments']//text()")
						.toString();
				commentNums = MyStringUtils.getStringPureNumber(commentNums);

				// 开始组织数据
				System.out.println(TAG + ":,文章id：" + id_blog + ",文章标头：" + title
						+ ",文章类型('0':原创；'1'：转载；'2'：翻译):" + type + ",发表日期："
						+ publishDateTime + ",阅读量:" + viewNums + ",评论数："
						+ commentNums + ",文章地址：" + detailsUrl + ",文章摘要："
						+ summary + "");

			}
	}

	/**
	 * 获取文章类型
	 */
	private String getArticleType(String string) {
		String type;
		type = new Html(string)
				.xpath("//div[@class='article_title']//span[@class='ico ico_type_Original']//text()")
				.get();// 原创类型
		if (type != null)
			return 0 + "";// 说明是原创
		type = new Html(string)
				.xpath("//div[@class='article_title']//span[@class='ico ico_type_Repost']//text()")
				.get();// 原创类型
		if (type != null)
			return 1 + "";
		type = new Html(string)
				.xpath("//div[@class='article_title']//span[@class='ico ico_type_Translated']//text()")
				.get();// 原创类型
		if (type != null)
			return 2 + "";
		return 3 + "";
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new CsdnBlogListSpider())
				.addPipeline(null)
				.addUrl("http://blog.csdn.net/" + "wgyscsf" + "/"
						+ "article/list/1").run();
	}

}
