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
	private int date;// 20171107,
	private long time;// 91500000,
	private float price;// 6.5,
	private float volume;// 0,
	private float avgPrice;// 6.539999961853,
	private int ccl;// 0,
	private float netChangeRatio;// 0,
	private float preClose;// 6.539999961853,
	private float amount;// 0

	private float open;// 6.4099998474121,
	private float high;// 6.5300002098083,
	private float low;// 6.2399997711182,
	private float close;// 6.4899997711182,
	private String bsflag;//S 卖；B 买
	private int type;//0 五档；1：买卖明细
	private String bsLevel;
	
	private String day;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBsLevel() {
		return bsLevel;
	}

	public void setBsLevel(String bsLevel) {
		this.bsLevel = bsLevel;
	}

	public String getBsflag() {
		return bsflag;
	}

	public void setBsflag(String bsflag) {
		this.bsflag = bsflag;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}

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

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
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

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

}
