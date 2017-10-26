/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午11:18:44
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
 *  <li   data-qid=10400>
<div class="user-icon"><img src="https://ss1.bdstatic.com/7Ls0a8Sm1A5BphGlnYG/sys/portrait/item/147ee69d8ee5bca0e99ba8a90c.jpg" width="50px" height="50px"></div>
<div class="article">
    <div>
        <span class="uname">李张雨</span>
        <span>2017-09-02 11:05 </span>
    </div>
    <div class="title">老师我想问紫金矿业下周会怎样？谢谢</div>
                                        </div>
</li>

 * @author :fengguangjing
 * @createTime:2017-10-26上午11:18:44
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QuestionBean extends CommonBean {
	private String src;
	private String uname;
	private String time;
	private String title;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
