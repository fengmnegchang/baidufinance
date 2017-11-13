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
	/**主题股票*/
	public static final String THEME_STOCK_DETAIL="https://gupiao.baidu.com/concept/1000313.html";
	/**取消关注**/
	public static final String DELCONCEPTFOLLOW ="https://gupiao.baidu.com/api/follow/delconceptfollow";
	/**关注**/
	public static final String ADDCONCEPTFOLLOW ="https://gupiao.baidu.com/api/follow/addconceptfollow";
	/**主题留言**/
	public static final String COMMENTLIST="https://gupiao.baidu.com/tpl/commentList?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&datetime=";

	public static final String CONCEPT = "https://gupiao.baidu.com/concept/";
	/**主题看空。看好**/
	public static final String VOTETOCONCEPT="https://gupiao.baidu.com/api/concept/votetoconcept";
	/**主题 评论**/
	public static final String CREATECOMMENT ="https://gupiao.baidu.com/ajax/createComment";
	
	/**登陆用户名称**/
	public static final String UNAME ="fengxian038";
	
	//新浪行情数据
	/**沪深指数**/
	public static final String INDEX_SH_SZ ="http://hq.sinajs.cn/list=s_sh000001,s_sz399001,s_sz399006,s_sh000300";
	/**基金指数**/
	public static final String INDEX_SH_SZ_FUND ="http://hq.sinajs.cn/list=s_sh000011,s_sz399305";
	/**港股指数**/
	public static final String INDEX_HONG_KONG ="http://hq.sinajs.cn/?_=1509677324760&list=rt_hkHSI,rt_hkHSCCI,rt_hkHSCEI";
	/**美股指数**/
	public static final String INDEX_US ="http://hq.sinajs.cn/rn=1509690467872&list=gb_dji,gb_ixic,gb_inx";
	/**启明星行业**/
	public static final String QMXINDUSTRYHQ="http://biz.finance.sina.com.cn/hq/qmxIndustryHq.php";
	/**新浪行业**/
	public static final String NEWSINAHY="http://vip.stock.finance.sina.com.cn/q/view/newSinaHy.php";
	/**申万行业**/
	public static final String SWHY="http://vip.stock.finance.sina.com.cn/q/view/SwHy.php";
	/**地域**/
	public static final String NEWFLJK_AREA="http://vip.stock.finance.sina.com.cn/q/view/newFLJK.php?param=area";
	/**概念**/
	public static final String NEWFLJK_CLASS="http://vip.stock.finance.sina.com.cn/q/view/newFLJK.php?param=class";
	/**行业**/
	public static final String NEWFLJK_INDUSTRY="http://vip.stock.finance.sina.com.cn/q/view/newFLJK.php?param=industry";
	/**板块个股**/
	public static final String GETHQNODEDATA ="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=symbol&asc=1&node=new_tchy&symbol=&_s_r_a=init";
	/**node板块个股**/
	public static final String GETHQNODEDATA_NODE ="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=symbol&asc=1&symbol=&_s_r_a=init&node=";
	/**沪深A股跌幅**/
	public static final String GETHQNODEDATA_CHANGE_PERCENT_1="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=changepercent&asc=1&node=hs_a&symbol=&_s_r_a=init";
	/**沪深A股z涨幅**/
	public static final String GETHQNODEDATA_CHANGE_PERCENT_0="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=changepercent&asc=0&node=hs_a&symbol=&_s_r_a=init";
	/**次新股**/
	public static final String GETHQNODEDATA_NEW_STOCK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=symbol&asc=1&node=new_stock&symbol=&_s_r_a=init";
	/**换手率榜**/
	public static final String GETHQNODEDATA_TURNOVERRATIO="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=turnoverratio&asc=0&node=hs_a&symbol=&_s_r_a=init";
	/**成交额**/
	public static final String GETHQNODEDATA_AMOOUNT="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=amount&asc=0&node=hs_a&symbol=&_s_r_a=init";
	/**涨速榜**/
	public static final String STOCK_HS_UP_5MIN_20="http://hq.sinajs.cn/rn=q2q25&format=text&list=stock_hs_up_5min_20";
	/**风险警示**/
	public static final String GETHQNODEDATA_SHFXJS="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=symbol&asc=1&node=shfxjs&symbol=&_s_r_a=init";
	/**开放式基金**/
	public static final String GETFUNDNETDATA_OPEN_FUND="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getFundNetData?page=1&num=80&sort=symbol&asc=1&node=open_fund&_s_r_a=init";
	/**ETF基金净值**/
	public static final String GETFUNDNETDATA_ETF_JZ_FUND ="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getFundNetData?page=1&num=80&sort=symbol&asc=1&node=etf_jz_fund&_s_r_a=init";
	/**封闭式基金**/
	public static final String GETHQNODEDATASIMPLE_CLOSE_FUND="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeDataSimple?page=1&num=80&sort=symbol&asc=1&node=close_fund&_s_r_a=init";
	/**ETF基金行情**/
	public static final String GETHQNODEDATASIMPLE_ETF_HQ_FUND="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeDataSimple?page=1&num=80&sort=symbol&asc=1&node=etf_hq_fund&_s_r_a=init";
	/**LOF基金行情**/
	public static final String GETHQNODEDATASIMPLE_LOF_HQ_FUND="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeDataSimple?page=1&num=80&sort=symbol&asc=1&node=lof_hq_fund&_s_r_a=init";
	/** 基金预测净值**/
	public static final String GETFUNDPREVDATA="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getFundPrevData?page=1&num=80&sort=symbol&asc=1&node=jjycjz&_s_r_a=init";
	/**货币型基金**/
	public static final String GETNAMELIST_MONEY_FUND="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getNameList?page=1&num=80&sort=symbol&asc=1&node=money_fund&_s_r_a=init";

	//港股
	/**蓝筹股**/
	public static final String GETHKSTOCKDATA_LCG_HK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=symbol&asc=1&node=lcg_hk&_s_r_a=init";
	/**红筹股**/
	public static final String GETHKSTOCKDATA_HCG_HK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=symbol&asc=1&node=hcg_hk&_s_r_a=init";
	/**国企股**/
	public static final String GETHKSTOCKDATA_GQG_HK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=symbol&asc=1&node=gqg_hk&_s_r_a=init";
	/**创业股**/
	public static final String GETHKSTOCKDATA_CYB_HK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=symbol&asc=1&node=cyb_hk&_s_r_a=init";
	/**ADR**/
	public static final String GETADRDATA_ADR_HK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getADRData?page=1&num=80&sort=symbol&asc=1&node=adr_hk&_s_r_a=init";
	/**港股涨幅**/
	public static final String GETHKSTOCKDATA_CHANGEPERCENT0="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=changepercent&asc=0&node=qbgg_hk&_s_r_a=init";
	/**港股跌幅**/
	public static final String GETHKSTOCKDATA_CHANGEPERCENT1="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=changepercent&asc=1&node=qbgg_hk&_s_r_a=init";
	/**港股成交额**/
	public static final String GETHKSTOCKDATA_AMOUNT="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=amount&asc=0&node=qbgg_hk&_s_r_a=init";
	/**港股成交量**/
	public static final String GETHKSTOCKDATA_VOLUME="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=volume&asc=0&node=qbgg_hk&_s_r_a=init";


	//沪港通
	/**沪股通**/
	public static final String GETHQNODEDATA_HGT_SH="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=symbol&asc=1&node=hgt_sh&symbol=&_s_r_a=init";
	/**港股通**/
	public static final String GETHQNODEDATA_HGT_HK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=symbol&asc=1&node=hgt_hk&_s_r_a=init";
//	/**A+H**/
//	public static final String GETANHDATA_HGT_AH="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getANHData?page=1&num=80&sort=hrap&asc=0&node=hgt_ah&_s_r_a=init";
	//深港通
	/**深股通**/
	public static final String GETHQNODEDATA_SGT_SZ="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page=1&num=80&sort=symbol&asc=1&node=sgt_sz&symbol=&_s_r_a=init";
	/**港股通**/
	public static final String GETHQNODEDATA_SGT_HK="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHKStockData?page=1&num=80&sort=symbol&asc=1&node=sgt_hk&_s_r_a=init";
	/**A+H**/
	public static final String GETANHDATA_HGT_AH="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getANHData?page=1&num=80&sort=hrap&asc=0&node=hgt_ah&_s_r_a=init";

	//美股
	/**中国概念股**/
	public static final String GETUSLIST_CHINA_US="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getUSList?page=1&num=80&sort=chg&asc=0&node=china_us&_s_r_a=init";
	/**科技类**/
	public static final String GETUSLIST_TECT_US="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getUSList?page=1&num=80&sort=chg&asc=0&node=tect_us&_s_r_a=init";
	/**新浪获取个股信息**/
	public static final String SINA_LIST="http://hq.sinajs.cn/list=";
	/**金融类**/
	public static final String GETUSLIST_FINANCE_US="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getUSList?page=1&num=80&sort=chg&asc=0&node=finance_us&_s_r_a=init";
	/**制造零售类**/
	public static final String GETUSLIST_SALES_US="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getUSList?page=1&num=80&sort=chg&asc=0&node=sales_us&_s_r_a=init";
	/**汽车能源类**/
	public static final String GETUSLIST_AUTO_US="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getUSList?page=1&num=80&sort=chg&asc=0&node=auto_us&_s_r_a=init";
	/**媒体类**/
	public static final String GETUSLIST_MEIDA_US="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getUSList?page=1&num=80&sort=chg&asc=0&node=meida_us&_s_r_a=init";
	/**医药食品类**/
	public static final String GETUSLIST_YYSP_US="http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getUSList?page=1&num=80&sort=chg&asc=0&node=yysp_us&_s_r_a=init";

	//全球指数
	/**美洲*/
	public static final String GETGLOBALINDEX_AMERICA_GLOBAL = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getGlobalIndex?page=1&num=80&sort=_&asc=1&node=america_global&_s_r_a=init";
	/**欧洲*/
	public static final String GETGLOBALINDEX_EURO_GLOBAL = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getGlobalIndex?page=1&num=80&sort=_&asc=1&node=euro_global&_s_r_a=init";
	/**亚洲*/
	public static final String GETGLOBALINDEX_ASIA_GLOBAL = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getGlobalIndex?page=1&num=80&sort=_&asc=1&node=asia_global&_s_r_a=init";
	/**非洲*/
	public static final String GETGLOBALINDEX_AFRICA_GLOBAL = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getGlobalIndex?page=1&num=80&sort=_&asc=1&node=africa_global&_s_r_a=init";
	/**大洋洲*/
	public static final String GETGLOBALINDEX_OCEANIA_GLOBAL = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getGlobalIndex?page=1&num=80&sort=_&asc=1&node=oceania_global&_s_r_a=init";


	//kline
	/**个股-新闻**/
	public static final String STOCK_ASYNCNEWSLIST_NEWS ="https://gupiao.baidu.com/stock/asyncnewslist?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&timestamp=1510022811084&code=sz000725&tab=news";
	/**个股-公告**/
	public static final String STOCK_ASYNCNEWSLIST_NOTICE ="https://gupiao.baidu.com/stock/asyncnewslist?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&timestamp=1510022811084&code=sz000725&tab=notice";
	/**个股-研报**/
	public static final String STOCK_ASYNCNEWSLIST_REPORT ="https://gupiao.baidu.com/stock/asyncnewslist?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&timestamp=1510022811084&code=sz000725&tab=report";

	/**个股-分时图**/
	public static final String STOCKTIMELINE ="https://gupiao.baidu.com/api/stocks/stocktimeline?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&stock_code=sz000725&timestamp=1510040773836";
	/**个股-5日图**/
	public static final String STOCKTIMELINEFIVE ="https://gupiao.baidu.com/api/stocks/stocktimelinefive?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&stock_code=sz000725&step=3&timestamp=1510133035727";
	/**个股-日K**/
	public static final String STOCKDAYBAR ="https://gupiao.baidu.com/api/stocks/stockdaybar?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&stock_code=sz000725&step=3&start=&count=160&fq_type=no&timestamp=1510212893650";
	/**个股-周K**/
	public static final String STOCKWEEKBAR="https://gupiao.baidu.com/api/stocks/stockweekbar?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&stock_code=sz000725&step=3&start=&count=160&fq_type=no&timestamp=1510304846130";
	/**个股-月K**/
	public static final String STOCKMONTHBAR="https://gupiao.baidu.com/api/stocks/stockmonthbar?from=pc&os_ver=1&cuid=xxx&vv=100&format=json&stock_code=sz000725&step=3&start=&count=160&fq_type=no&timestamp=1510304899214";
	
	/**股票信息**/
	public static final String STOCK ="https://gupiao.baidu.com/stock/sz000725.html";
	/**新浪60分**/
	public static final String GETKLINEDATA_60="http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/var%20_sz000725_60_1510555547326=/CN_MarketData.getKLineData?symbol=sz000725&scale=60&ma=no&datalen=1023";
	/**新浪5分**/
	public static final String GETKLINEDATA_5="http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/var%20_sz000725_5_1510555547326=/CN_MarketData.getKLineData?symbol=sz000725&scale=5&ma=no&datalen=1023";
	/**新浪15分**/
	public static final String GETKLINEDATA_15="http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/var%20_sz000725_15_1510555547326=/CN_MarketData.getKLineData?symbol=sz000725&scale=15&ma=no&datalen=1023";
	/**新浪30分**/
	public static final String GETKLINEDATA_30="http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/var%20_sz000725_30_1510555547326=/CN_MarketData.getKLineData?symbol=sz000725&scale=30&ma=no&datalen=1023";

	
}
