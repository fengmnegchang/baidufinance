/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:50:27
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
 * <li>
    <h3>
        <a href="http://tieba.baidu.com/p/4421899778" target="_blank" data-spm="1">职业股票T+0之路，每日实盘!积小赢为大赢</a>
        <span>2017-10-18 09:53</span>
    </h3>
    <p>先来个全景，装个B，混个脸熟后背杂乱的蜘蛛网六个显示器都靠这个小家伙撑着，同时俩小黑也可以备用（遇到过主机电源故障开不了机，立马笔记本转接显示器。）自己找亮点哈AC88U保障稳稳的网络UPS不间断电源，防止意外断电。以前做权证的时候宽带线路也有备...</p>
    <div class="statistic">
        <span>阅读</span>
        <span>20318</span>
        <span class="m-l">回复</span>
        <span>723</span>
    </div>
</li>
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:50:27
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotTiebaTopicBean extends CommonBean {
	private String href;
	private String title;
	private String time;
	private String desc;
	private String readcount;
	private String replycount;
	
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getReadcount() {
		return readcount;
	}
	public void setReadcount(String readcount) {
		this.readcount = readcount;
	}
	public String getReplycount() {
		return replycount;
	}
	public void setReplycount(String replycount) {
		this.replycount = replycount;
	}
	
	
}
