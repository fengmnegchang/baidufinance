/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:10:53
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
 * 板块 数量　 平均价　 涨跌额　 涨跌幅　 总成交量(手)　 总成交额(万元)　 领涨股 涨跌幅　 当前价　 涨跌额 "lycy":
 * "lycy,旅游餐饮,29,13.987777777778,0.24259259259259,1.7649277861608,172612988,1872491223,sh600358,9.946949602122,8.290,0.75,国旅联合"
 * ,
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:10:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class PlateBean extends CommonBean {
	private String plateSimpleCode;// 简码
	private String plateName;// 板块名称
	private int num;// 数量
	private double agvprice;// 平均价
	private double netChange;// 涨跌额
	private Double netChangeRate;// 涨跌幅
	private long totalvolume;// 总成交量(手)
	private long totalvolumeMoney;// 总成交额(万元)
	private String stockCode;
	private double stockNetChnageRate;
	private double stockClose;
	private double stockNetChnage;
	private String stockName;
	
	private int viewType;
	/**0:涨跌幅；1：涨幅榜；-1：跌幅榜;**/
	private int netRatioType;
	

	public int getNetRatioType() {
		return netRatioType;
	}

	public void setNetRatioType(int netRatioType) {
		this.netRatioType = netRatioType;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public String getPlateSimpleCode() {
		return plateSimpleCode;
	}

	public void setPlateSimpleCode(String plateSimpleCode) {
		this.plateSimpleCode = plateSimpleCode;
	}

	public String getPlateName() {
		return plateName;
	}

	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getAgvprice() {
		return agvprice;
	}

	public void setAgvprice(double agvprice) {
		this.agvprice = agvprice;
	}

	public double getNetChange() {
		return netChange;
	}

	public void setNetChange(double netChange) {
		this.netChange = netChange;
	}

	public Double getNetChangeRate() {
		return netChangeRate;
	}

	public void setNetChangeRate(Double netChangeRate) {
		this.netChangeRate = netChangeRate;
	}

	public long getTotalvolume() {
		return totalvolume;
	}

	public void setTotalvolume(long totalvolume) {
		this.totalvolume = totalvolume;
	}

	public long getTotalvolumeMoney() {
		return totalvolumeMoney;
	}

	public void setTotalvolumeMoney(long totalvolumeMoney) {
		this.totalvolumeMoney = totalvolumeMoney;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public double getStockNetChnageRate() {
		return stockNetChnageRate;
	}

	public void setStockNetChnageRate(double stockNetChnageRate) {
		this.stockNetChnageRate = stockNetChnageRate;
	}

	public double getStockClose() {
		return stockClose;
	}

	public void setStockClose(double stockClose) {
		this.stockClose = stockClose;
	}

	public double getStockNetChnage() {
		return stockNetChnage;
	}

	public void setStockNetChnage(double stockNetChnage) {
		this.stockNetChnage = stockNetChnage;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

}
