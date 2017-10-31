/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31上午10:33:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.market;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 指数
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31上午10:33:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class IndexBean extends CommonBean {
	private String stockCode;
	private String stockName;
	private double close;
	private double netChnage;
	private double netChnageRate;
	private long volume;// 手=100股
	private long volumeMoney;// 万元

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getNetChnage() {
		return netChnage;
	}

	public void setNetChnage(double netChnage) {
		this.netChnage = netChnage;
	}

	public double getNetChnageRate() {
		return netChnageRate;
	}

	public void setNetChnageRate(double netChnageRate) {
		this.netChnageRate = netChnageRate;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public long getVolumeMoney() {
		return volumeMoney;
	}

	public void setVolumeMoney(long volumeMoney) {
		this.volumeMoney = volumeMoney;
	}

}
