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
	/**申请的qq开发appid**/
	public static final String APP_ID="101421230";
    /**申请的微信开发appid**/
    public static final String WX_APP_ID = "wx8e8dc60535c9cd93";
	
	public static final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
	public static final String userAgentMoblie = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Mobile Safari/537.36";
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
	/**行业解析*/
	public static final String GETTAGNEWS="https://gupiao.baidu.com/api/news/gettagnews?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tagid=4000007&tagname=%E8%A1%8C%E4%B8%9A%E8%A7%A3%E6%9E%90&count=31&timestamp=1508316036756";
	/**宏观经济*/
	public static final String GETTAGNEWS_ECONOMY="https://gupiao.baidu.com/api/news/gettagnews?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tagid=4000008&tagname=%E5%AE%8F%E8%A7%82%E7%BB%8F%E6%B5%8E&count=31&timestamp=1508318189026";
	/**概念热点*/
	public static final String GETTAGNEWS_THEME_HOT="https://gupiao.baidu.com/api/news/gettagnews?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tagid=4000004&tagname=%E6%A6%82%E5%BF%B5%E7%83%AD%E7%82%B9&count=31&timestamp=1508318292918";
	/**研究报告*/
	public static final String GETTAGNEWS_REPORT="https://gupiao.baidu.com/api/news/gettagnews?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tagid=&tagname=%E7%A0%94%E7%A9%B6%E6%8A%A5%E5%91%8A&count=31&timestamp=1508318333541";
	public static final String GETTAGNEWS_REPORT_2="https://gupiao.baidu.com/api/news/gettagnews?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tagid=&tagname=%E8%82%A1%E7%A5%A8%E7%A0%94%E6%8A%A5&count=31&timestamp=1508318333543";
	public static final String GETTAGNEWS_REPORT_3="https://gupiao.baidu.com/api/news/gettagnews?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tagid=&tagname=%E6%95%B0%E6%8D%AE%E6%8A%A5%E5%91%8A&count=31&timestamp=1508318333544";
	/**股票*/
	public static final String GUPIAO_BAIDU="https://gupiao.baidu.com";
	/**文章页面*/
	public static final String ARTICLE="https://gupiao.baidu.com/article/TT1049386";
	/**跳转文章页面*/
	public static final String ARTICLE_URL="https://gupiao.baidu.com/article/";
	/**收藏文章*/
	public static final String USERCOLLECT="https://gupiao.baidu.com/api/collect/usercollect?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&timestamps=1508465833284&timestamp=1508465833284&collectids=";
	/**m要闻*/
	public static final String M_GETTODAYNEWS="https://gupiao.baidu.com/api/news/gettodaynews?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=json";
	/**m宏观*/
	public static final String M_GETTAGNEWS_ECONOMY="https://gupiao.baidu.com/api/news/gettagnews?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=json&tagname=%E5%AE%8F%E8%A7%82%E7%BB%8F%E6%B5%8E&tagid=4000008";
	/**m行业*/
	public static final String M_GETTAGNEWS="https://gupiao.baidu.com/api/news/gettagnews?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=json&tagname=%E8%A1%8C%E4%B8%9A%E8%A7%A3%E6%9E%90&tagid=4000007";
	/**m机构*/
	public static final String M_ORGANIZATION="https://gupiao.baidu.com/api/news/gettagnews?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=json&tagname=%E6%A6%82%E5%BF%B5%E7%83%AD%E7%82%B9&tagid=4000004";
	/**m文章评论*/
	public static final String M_GETCOMMENTLIST="https://gupiao.baidu.com/api/comment/getcommentlist?from=h5&os_ver=0&cuid=xxx&vv=2.2&format=h5&datetime=&type=lt&stype=1&count=20&sid=";
	/**精英投资顾问*/
	public static final String EXPERT = "https://gupiao.baidu.com/expert/10008";
	/**顾问观点分页*/
	public static final String EXPERT_LIST = "https://gupiao.baidu.com/tpl/expertList?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tab=0&time=1508985900313&timestamp=1508987022625&page=";
	/**问答*/
	public static final String QUESTION ="https://gupiao.baidu.com/expert/10008?tab=1";
	/**问答分页*/
	public static final String QUESTION_LIST="https://gupiao.baidu.com/tpl/expertList?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&tab=1&time=1508987838641&timestamp=1508988690708&page=";

	public static final String EXPERT_TAB="https://gupiao.baidu.com/expert/";
	/**提问*/
	public static final String USERQUESTIONADD ="https://gupiao.baidu.com/api/rails/userquestionadd";
	/**最新热点*/
	public static final String SEARCHDATE = "https://gupiao.baidu.com/concept/?searchdate=";
}
