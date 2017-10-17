/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午2:00:28
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.utils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午2:00:28
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class UrlUtils {
	public static final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
	/**自选id**/
	public static final String GATHERMYSTOCK = "https://gupiao.baidu.com/api/mystock/gathermystock?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&timestamp=1507788271919&cmdlist=%5B%7B%22cmd%22%3A15%2C%22group_id%22%3A%22";
	/**自选置顶**/
	public static final String TOPMYSTOCK = "https://gupiao.baidu.com/api/rails/topmystock";
	/**所有分组**/
	public static final String GATHERMYSTOCK_ALL ="https://gupiao.baidu.com/api/mystock/gathermystock?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&cmdlist=%5B%7B%22cmd%22%3A15%2C%22group_id%22%3A%22all%22%7D%5D&timestamp=1508147151690";
	/**COOKIE**/
	public static final String COOKIE = "BAIDUID=CF6C53AD63064C72CDA6A1CF7788EC1C:FG=1; BIDUPSID=CF6C53AD63064C72CDA6A1CF7788EC1C; PSTM=1504837961; MCITY=-289%3A; FP_UID=e49b6707341251abb076b7ac70bb2a02; PSINO=5; H_PS_PSSID=1434_21118_17001_20929; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDUSS=DQ5NWhYb29PRFFYeX50eDZ-ZXUwQXM0UDM5OEU5aGJ5TmQtTmIzY0JxRlUtUXhhTVFBQUFBJCQAAAAAAAAAAAEAAAD5apgiZmVuZ3hpYW4wMzgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFRs5VlUbOVZN; stoken=37fe8d91cf2c4d8fa0796d6af6bfd48cf1115d0d6621c5b03d5f098c4d8a447c; Hm_lvt_35d1e71f4c913c126b8703586f1d2307=1507692673,1508204598,1508207604,1508207625; Hm_lpvt_35d1e71f4c913c126b8703586f1d2307=1508207733";
	/***TOKEN***/
	public static final String TOKEN = "65e93709f606922c";
	/**删除**/
	public static final String BATCHDELMYSTOCK  ="https://gupiao.baidu.com/api/rails/batchdelmystock";
	/**移动分组*/
	public static final String MOVEMYSTOCK  ="https://gupiao.baidu.com/api/rails/movemystock";
	/**新增分组*/
	public static final String ADDGROUP ="https://gupiao.baidu.com/api/rails/addgroup";
	/**删除分组*/
	public static final String DELGROUP ="https://gupiao.baidu.com/api/rails/delgroup";
	/**编辑分组*/
	public static final String MODIFYGROUP ="https://gupiao.baidu.com/api/rails/modifygroup";
	/**搜索股票*/
	public static final String STOCKQUERY="https://gupiao.baidu.com/api/search/stockquery?from=pc&os_ver=1&cuid=xxx&vv=3.2&format=json&asset=0%2C4%2C14&timestamp=1508218838054&query_content=";
	/**添加股票*/
	public static final String ADDMYSTOCK="https://gupiao.baidu.com/api/rails/addmystock";
}
