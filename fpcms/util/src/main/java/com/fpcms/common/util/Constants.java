package com.fpcms.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Constants {

	public static String ADMIN_LOGIN_USER = "ADMIN_LOGIN_USER";
	
	public static String CHANNED_CODE_NEWS = "news";
	
	public static String[] FAIPIAO_KEYWORDS = {"发票","增值税","税控","地税","国税","税务","发票税","纳税","纳税人","税法","税收",
		"发票抬头","增值税发票","发票抽奖",
		"住宿发票","会计","财务","税务师","评估师","会计师","出纳","建账","做账","减免税",
		"营业税","增值税","消费税"," 企业所得税 ","个人所得税 ","税收优惠 ","关税","金融","会计报表","审计",
		"房产税","契税","印花税","城建税","车船税","定额发票"};
	
	public static String[] BAIDU_BUZZ_URLS = {
		"http://top.baidu.com/buzz.php?p=weekhotspot",
		"http://top.baidu.com/buzz.php?p=top10",//实时热点
		"http://top.baidu.com/buzz?b=342", //社会民生
		"http://top.baidu.com/buzz?b=344", //娱乐八卦
//		"http://top.baidu.com/buzz.php?b=291", //美食
		"http://top.sogou.com/hotword0.html",
		"http://top.soso.com/index.php?type=realtime", // 实时热点
		"http://top.soso.com/index.php?type=sevenday", // 本周热点
		};

	public static String PROPERTY_DEFAULT_GROUP = "localhost";

	// =============== 频道ID START  ==================
	/**
	 * 频道 tree的root的父亲ID
	 */
	public static long TREE_ROOT_PARENT_ID = -1;
	/**
	 * 频道 tree的root id
	 */
	public static long TREE_ROOT_ID = 1;
	// =============== 频道ID END  ==================
	
	
	/**
	 * 网络蜘蛛日志类
	 */
	public static Logger LOGGER_SPIDER = LoggerFactory.getLogger("spider");
	
	/**
	 * 性能类
	 */
	public static Logger LOGGER_DUMP_PROFILER = LoggerFactory.getLogger("dump-profiler");
	
	/**
	 * 开发密码，用于在开发环境下采用的密码
	 */
	public static String DEV_PASSWORD_KEY = "devModePassword";
	public static String DEV_PASSWORD_VALUE = "4a3f3Ff2as4dio27LFsffguiDKJAuiD43ui2vmoOnq4O2N0hHu9J6K5kg6e45ui5K5qwe9823Ulkuiad";

	public static String QUERY_STRING = "queryString";
	
	/**
	 * 采集回来的文章存放的站点,作者,频道
	 */
	public static String CRAWL_SITE = "localhost";
	public static String CRAWL_AUTHOR = "crawler";
	public static String CRAWL_CHANNEL_CODE = "news";

	/**
	 * key_value的group
	 */
	public static String KEY_VALUE_GROUP_SEARCH_BUZZ = "search_buzz";
	
}
