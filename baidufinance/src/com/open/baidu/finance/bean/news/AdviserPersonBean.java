/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:05:31
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
<div class="adviser-person">
    <div class="photo">
        <a href="/expert/10000" target="_blank" data-spm="1">
                <img src="https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/fb_gushitong/pic/item/0eb30f2442a7d933576f9269aa4bd11372f001e0.jpg" alt="投资顾问">
                <div class="photo-footer">个人主页</div>
        </a>
    </div>
    <div class="content">
        <h3>
            <a href="/expert/10000" target="_blank" data-spm="2">
            刘琦</a></h3>
        <h4>大同证券 资深投资顾问</h4>
        <div>中长线价值投资，政策深度解析</div>
        <div class="ask">
            <a href="/expert/10000?tab=1" target="_blank" data-spm="3">提问</a>
        </div>
    </div>
</div>
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:05:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class AdviserPersonBean extends CommonBean {
	private String href;
	private String src;
	private String expertName;
	private String level;
	private String info;
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
	public String getExpertName() {
		return expertName;
	}
	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
