/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-27下午5;//07;//57
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.hot;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-27下午5;//07;//57
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class FollowBean extends CommonBean {
	private int followIndex;// 1415,
	private String followTime;// 10-27,
	private String followPercent;// -7.52%,
	private float followPrice;// 10.618888881471

	public int getFollowIndex() {
		return followIndex;
	}

	public void setFollowIndex(int followIndex) {
		this.followIndex = followIndex;
	}

	public String getFollowTime() {
		return followTime;
	}

	public void setFollowTime(String followTime) {
		this.followTime = followTime;
	}

	public String getFollowPercent() {
		return followPercent;
	}

	public void setFollowPercent(String followPercent) {
		this.followPercent = followPercent;
	}

	public float getFollowPrice() {
		return followPrice;
	}

	public void setFollowPrice(float followPrice) {
		this.followPrice = followPrice;
	}

}
