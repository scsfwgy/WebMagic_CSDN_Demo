package com.wgyscsf.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author 高远</n>
 * 编写日期   2016-9-24下午9:18:01</n>
 * 邮箱  wgyscsf@163.com</n>
 * 博客  http://blog.csdn.net/wgyscsf</n>
 * TODO</n>
 */
public class BaseSpider implements PageProcessor {
	public boolean showDbLog = false;
	public boolean showDataLog = false;
	static String TAG = null;

	public BaseSpider() {
		// TODO Auto-generated constructor stub
		TAG = this.getClass().getSimpleName();
	}

	public boolean isShowDbLog() {
		return showDbLog;
	}

	public BaseSpider setShowDbLog(boolean showDbLog) {
		this.showDbLog = showDbLog;
		return this;
	}

	public boolean isShowDataLog() {
		return showDataLog;
	}

	public BaseSpider setShowDataLog(boolean showDataLog) {
		this.showDataLog = showDataLog;
		return this;
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub

	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return null;
	}

}
