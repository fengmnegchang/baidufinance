/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:09:19
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.news;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * <a href="http://tieba.baidu.com/p/4295170815" target="_blank" data-spm="1">
 * <img src=
 * "https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/fb_gushitong/pic/item/9a504fc2d5628535fbdb852297ef76c6a7ef63fd.jpg"
 * > <div class="desc"> 【正在直播】昨日结束五连阴迎反弹，你有信心了吗？ </div> </a>
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:09:19
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class LiveShowBean extends CommonBean {
	private String href;
	private String src;
	private String desc;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
