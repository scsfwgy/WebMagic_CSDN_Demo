package com.wgyscsf.spider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author 高远</n>
 * 编写日期   2016-11-13下午9:02:01</n>
 * 邮箱  wgyscsf@163.com</n>
 * 博客  http://blog.csdn.net/wgyscsf</n>
 * TODO</n>
 */
public class SeleniumTest {
	public static void main(String[] args) {
		// 第一步： 设置chromedriver地址。一定要指定驱动的位置。
		System.setProperty("webdriver.chrome.driver",
				"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		// 第二步：初始化驱动
		WebDriver driver = new ChromeDriver();
		// 第三步：获取目标网页
		driver.get("http://blog.csdn.net/wgyscsf/article/details/52835845");
		// 第四步：解析。以下就可以进行解了。使用webMagic、jsoup等进行必要的解析。
		System.out.println("Page title is: " + driver.getTitle());
		System.out.println("Page title is: " + driver.getPageSource());
	}
}
