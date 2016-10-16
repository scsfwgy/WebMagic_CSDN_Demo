package com.wgyscsf.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 高远</n>
 * 编写日期   2016-9-24下午9:39:37</n>
 * 邮箱  wgyscsf@163.com</n>
 * 博客  http://blog.csdn.net/wgyscsf</n>
 * TODO</n>
 */
public class MyStringUtils {
	// 获取最后“/”后面的内容
	public static String getLastSlantContent(String fullPath) {
		int pos = fullPath.lastIndexOf("/");
		if (pos != -1) {
			return fullPath.substring(pos + 1);
		} else {
			return null;
		}
	}


	// 获取一个字符串中的数字部分，剔除掉不是数字的
	public static String getStringPureNumber(String str) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(str);
		if (matcher.replaceAll("").equals(""))
			return -1 + "";
		return matcher.replaceAll("");
	}

	// 获取一个字符串中的数字部分，剔除掉不是数字的
	public static int getIntPureNumber(String str) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(str);
		String num = matcher.replaceAll("");
		if (!num.equals(""))
			return Integer.parseInt(num);
		return -1;
	}

	// 剔除空格
	public static String getNoTrimStr(String str) {
		return str.replaceAll("\\s*", "");
	}

	// 获取“|”前面的字符
	public static String getBeforeVercitalLine(String str) {
		if (str.indexOf("|") == -1)
			return str;
		return (String) str.substring(0, str.indexOf("|"));
	}

	// 获取“|”/后面面的字符
	public static String getAfterVercitalLine(String str) {
		return (String) str.substring(str.indexOf("|") + 1);
	}

	// 获取“/”前面的字符
	public static String getLastBeforeSprit(String str) {
		if (str.lastIndexOf("/") == -1)
			return str;
		return (String) str.substring(0, str.indexOf("|"));
	}

	// 获取“/”后面面的字符
	public static String getLastAfterSprit(String str) {
		return (String) str.substring(str.lastIndexOf("/") + 1);
	}
	// 生成GUID
	public static String getGUID() {
		return UUID.randomUUID() + "";
	}

	// 生成当前时间
	public static String getCurrentDateTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		return str;
	}
}
