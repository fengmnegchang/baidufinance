/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:47:39
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
 * <li >
<div class="title">声明</div>
<div class="content">由于百度股市通业务的调整，本博以后将不再此发表观点，感兴趣的股友可通过微信搜索“平安看盘”或“key601318”，关注公众号，获取及时资讯</div>
<div class="info">
    <div class="operation">
        <ul>
            <!-- <li class="menu-border _commentView" nid="pc34_2760_1463646660"><span>评论&nbsp;123</span></li>
            <li><i class="comment-hand _addZan" vid="3264646"></i>&nbsp;<span class="_favourNum">0</span></li>-->
                                    </ul>
    </div>
    <div class="time">发布时间 ：2016-05-19 16:31</div>
</div>
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:47:39
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ExpertViewBean extends CommonBean {
	private String title;
	private String content;
	private String time;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
