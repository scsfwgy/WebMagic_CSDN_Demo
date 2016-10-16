package com.wgyscsf.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author 高远</n> 编写日期 2016-9-24下午7:25:36</n> 邮箱 wgyscsf@163.com</n> 博客
 *         http://blog.csdn.net/wgyscsf</n> TODO</n>
 */
public class SimulateLoginPolicy2 implements PageProcessor {
	private Site site = Site
			.me()
			.setDomain("blog.csdn.net")
			.setSleepTime(300)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
			// 以下信息可以模拟登陆，信息全部来自于浏览器
			.addCookie("AU", "CA9")
			.addCookie("BT", "1475675181189")
			.addCookie("UD", "self-control%2Cself-free")
			.addCookie("UE", "wgyscsf@163.com")
			.addCookie("UN", "wgyscsf")
			.addCookie(
					"UserInfo",
					"Duu8rW1HcDcOII5T9vZVX4DGXWxbzgLbyuAxIF04i3M6hfCvIRR6tEUOpn%2BbTcs7vGn0tDBTMmwNTxzsUc4G2Pu3%2FBTm9EFGC5aaVX4gca4jBrNy%2FETzPK0q58T6RQ2a")
			.addCookie("UserName", "wgyscsf")
			.addCookie("UserNick", "%E5%89%A9%E8%8F%9C%E5%89%A9%E9%A5%AD")
			.addCookie("__message_cnel", "0")
			.addCookie("__message_district_code", "00000")
			.addCookie("__message_gu_msg_id", "0")
			.addCookie("__message_in_school", "0")
			.addCookie("__message_sys_msg_id", "0")
			.addCookie("access-token", "41443ae0-e60e-4820-953b-2992e5009ced")
			.addCookie("dc_session_id", "1475675181394")
			.addCookie("dc_tos", "oeku99")
			.addCookie("uuid", "0fa13b5b-bb33-4f60-8339-4d64aaffa4f3")
			.addCookie("uuid_tt_dd", "7303527758529643523_20161005");

	@Override
	public void process(Page page) {
		// 打印网页源码查看和登录之前有何区别
		System.out.println(page.toString());
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new SimulateLoginPolicy2()).addPipeline(null)
				.addUrl("http://blog.csdn.net/" + "wgyscsf").run();
	}

}
