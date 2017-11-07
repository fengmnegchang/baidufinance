/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午10:49:13
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.kline;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * <li class="row">
 * <h4 class="text-ellipsis"><a target="_blank"
 * href="/article/report/gaotime_notice_000725_2769177" data-spm="1">
 * 【公告】京东方Ａ：第八届董事会第六次会议决议公告</a></h4>
 * <p class="desc">
 * 京东方科技集团股份有限公司 第八届董事会第六次会议决议公告 本公司及董事会全体成员保证信息披露的内容真实、准确、
 * 完整，没有虚假记载、误导性陈述或重大遗漏。 京东方科技集团股份有限公司（以下简称“公司”）第八届董事 会第六次会议通知于 2016 年 10...
 * </p>
 * <div class="bottom">
 * <ul class="left">
 * <li>2016-10-25</li>
 * </ul>
 * <div class="clearfix"></div> </div></li>
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午10:49:13
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class NewsBean extends CommonBean {
	private String href;
	private String title;
	private String desc;
	private String time;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
