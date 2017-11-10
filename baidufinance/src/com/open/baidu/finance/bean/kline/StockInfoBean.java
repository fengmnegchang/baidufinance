/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-10下午2:03:15
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
 * 
 * @author :fengguangjing
 * @createTime:2017-11-10下午2:03:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockInfoBean extends CommonBean {
	private String href;
	private String title;
	
	private float close;
	private float netChange;
	private String netChangeRatio;
	private float open;
	private float preClose;
	private String volume;
	private String exchangeRate;// 换手率
	private String stockName;
	private String stockCode;
	private String time;

	private float high;
	private float low;
	private float up;// 涨停
	private float down;// 跌停

	private String m915; // 内盘
	private String m930; // 外盘
	private String amount;// 成交额
	private String rate;// 振幅

	private String weibi; // 委比
	private String volumebi; // 量比
	private String total_exmoney;// 流通市值
	private String total_money;// 总市值

	private String mrq; // 市盈率MRQ
	private String jing_rate; // 市净率
	private String profit;// 每股收益
	private String jingmoney;// 每股净资产
	private String total_guben; // 总股本
	private String ex_guben; // 流通股

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public float getNetChange() {
		return netChange;
	}

	public void setNetChange(float netChange) {
		this.netChange = netChange;
	}

	public String getNetChangeRatio() {
		return netChangeRatio;
	}

	public void setNetChangeRatio(String netChangeRatio) {
		this.netChangeRatio = netChangeRatio;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getPreClose() {
		return preClose;
	}

	public void setPreClose(float preClose) {
		this.preClose = preClose;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public float getUp() {
		return up;
	}

	public void setUp(float up) {
		this.up = up;
	}

	public float getDown() {
		return down;
	}

	public void setDown(float down) {
		this.down = down;
	}

	public String getM915() {
		return m915;
	}

	public void setM915(String m915) {
		this.m915 = m915;
	}

	public String getM930() {
		return m930;
	}

	public void setM930(String m930) {
		this.m930 = m930;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getWeibi() {
		return weibi;
	}

	public void setWeibi(String weibi) {
		this.weibi = weibi;
	}

	public String getVolumebi() {
		return volumebi;
	}

	public void setVolumebi(String volumebi) {
		this.volumebi = volumebi;
	}

	public String getTotal_exmoney() {
		return total_exmoney;
	}

	public void setTotal_exmoney(String total_exmoney) {
		this.total_exmoney = total_exmoney;
	}

	public String getTotal_money() {
		return total_money;
	}

	public void setTotal_money(String total_money) {
		this.total_money = total_money;
	}

	public String getMrq() {
		return mrq;
	}

	public void setMrq(String mrq) {
		this.mrq = mrq;
	}

	public String getJing_rate() {
		return jing_rate;
	}

	public void setJing_rate(String jing_rate) {
		this.jing_rate = jing_rate;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getJingmoney() {
		return jingmoney;
	}

	public void setJingmoney(String jingmoney) {
		this.jingmoney = jingmoney;
	}

	public String getTotal_guben() {
		return total_guben;
	}

	public void setTotal_guben(String total_guben) {
		this.total_guben = total_guben;
	}

	public String getEx_guben() {
		return ex_guben;
	}

	public void setEx_guben(String ex_guben) {
		this.ex_guben = ex_guben;
	}

}
