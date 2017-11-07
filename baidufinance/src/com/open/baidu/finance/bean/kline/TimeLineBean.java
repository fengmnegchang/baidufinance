/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-7下午3;//53;//48
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.kline;

import com.open.android.bean.CommonBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-7下午3;//53;//48
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
public class TimeLineBean extends CommonBean {
	private int date;//20171107,
	private long time;//91500000,
	private float  price;//6.5,
	private long  volume;//0,
	private float  avgPrice;//6.539999961853,
	private int ccl;//0,
	private float netChangeRatio;//0,
	private float  preClose;//6.539999961853,
	private long  amount;//0
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public float getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(float avgPrice) {
		this.avgPrice = avgPrice;
	}
	public int getCcl() {
		return ccl;
	}
	public void setCcl(int ccl) {
		this.ccl = ccl;
	}
	public float getNetChangeRatio() {
		return netChangeRatio;
	}
	public void setNetChangeRatio(float netChangeRatio) {
		this.netChangeRatio = netChangeRatio;
	}
	public float getPreClose() {
		return preClose;
	}
	public void setPreClose(float preClose) {
		this.preClose = preClose;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	
}
