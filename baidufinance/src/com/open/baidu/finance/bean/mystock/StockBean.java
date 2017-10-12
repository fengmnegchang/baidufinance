/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-12上午11;//19;//42
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.mystock;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-12上午11;//19;//42
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class StockBean extends CommonBean {
	private String stockCode;// 000001,
	private String stockName;// 上证指数,
	private String exchange;// sh,
	private int asset;// 4,
	private int stockStatus;// 2,
	private double close;// 3378.8334960938,
	private double capitalization;// null,
	private double netChange;// -9.4501953125,
	private double netChangeRatio;// -0.27890804409981,
	private int delayFlag;// 1,
	private int smartStockPick;// 0,
	private int isAlert;// 1
	private int viewType;

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

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public int getAsset() {
		return asset;
	}

	public void setAsset(int asset) {
		this.asset = asset;
	}

	public int getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(int stockStatus) {
		this.stockStatus = stockStatus;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getCapitalization() {
		return capitalization;
	}

	public void setCapitalization(double capitalization) {
		this.capitalization = capitalization;
	}

	public double getNetChange() {
		return netChange;
	}

	public void setNetChange(double netChange) {
		this.netChange = netChange;
	}

	public double getNetChangeRatio() {
		return netChangeRatio;
	}

	public void setNetChangeRatio(double netChangeRatio) {
		this.netChangeRatio = netChangeRatio;
	}

	public int getDelayFlag() {
		return delayFlag;
	}

	public void setDelayFlag(int delayFlag) {
		this.delayFlag = delayFlag;
	}

	public int getSmartStockPick() {
		return smartStockPick;
	}

	public void setSmartStockPick(int smartStockPick) {
		this.smartStockPick = smartStockPick;
	}

	public int getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(int isAlert) {
		this.isAlert = isAlert;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

}
