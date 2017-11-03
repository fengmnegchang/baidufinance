/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-31下午4;//43;//56
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.market;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-31下午4;//43;//56
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class PlateStockBean extends CommonBean {
	private String symbol;// sh600145,
	private String code;// 600145,
	private String name;// *ST新亿,
	private double trade;// 1.870,
	private String pricechange;// 0.000,
	private Double changepercent;// 0.000,
	private String buy;// 0.000,
	private String sell;// 0.000,
	private String settlement;// 1.870,
	private String open;// 0.000,
	private String high;// 0.000,
	private String low;// 0.000,
	private double volume;// 0,
	private double amount;// 0,
	private String ticktime;// 15;//00;//00,
	private double per;// -935,
	private double pb;// 4.503,
	private double mktcap;// 278835.77106,
	private double nmc;// 278835.77106,
	private double turnoverratio;// 0
	private int viewType;
	private int netRatioType;
	
	//港股 代码	名称	最新价	涨跌额	涨跌幅	昨收	今开	最高	最低	成交量/万	成交额/万
	private String  engname;
	private String tradetype;
	private double lasttrade;
	private double prevclose;
	private double currentvolume;
	private double high_52week;
	private double low_52week;
	private double eps;
	private double dividend;
	private long stocks_sum;
	
	//adr
	private String chname;
	private double last;
	private double chg;
	private double pchg;
	
	//ah 深港通，沪股通
	private String a;
	private String h;
	
	
	

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getH() {
		return h;
	}

	public void setH(String h) {
		this.h = h;
	}

	public String getChname() {
		return chname;
	}

	public void setChname(String chname) {
		this.chname = chname;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public double getChg() {
		return chg;
	}

	public void setChg(double chg) {
		this.chg = chg;
	}

	public double getPchg() {
		return pchg;
	}

	public void setPchg(double pchg) {
		this.pchg = pchg;
	}

	public String getEngname() {
		return engname;
	}

	public void setEngname(String engname) {
		this.engname = engname;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public double getLasttrade() {
		return lasttrade;
	}

	public void setLasttrade(double lasttrade) {
		this.lasttrade = lasttrade;
	}

	public double getPrevclose() {
		return prevclose;
	}

	public void setPrevclose(double prevclose) {
		this.prevclose = prevclose;
	}

	public double getCurrentvolume() {
		return currentvolume;
	}

	public void setCurrentvolume(double currentvolume) {
		this.currentvolume = currentvolume;
	}

	public double getHigh_52week() {
		return high_52week;
	}

	public void setHigh_52week(double high_52week) {
		this.high_52week = high_52week;
	}

	public double getLow_52week() {
		return low_52week;
	}

	public void setLow_52week(double low_52week) {
		this.low_52week = low_52week;
	}

	public double getEps() {
		return eps;
	}

	public void setEps(double eps) {
		this.eps = eps;
	}

	public double getDividend() {
		return dividend;
	}

	public void setDividend(double dividend) {
		this.dividend = dividend;
	}

	public long getStocks_sum() {
		return stocks_sum;
	}

	public void setStocks_sum(long stocks_sum) {
		this.stocks_sum = stocks_sum;
	}

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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTrade() {
		return trade;
	}

	public void setTrade(double trade) {
		this.trade = trade;
	}

	public String getPricechange() {
		return pricechange;
	}

	public void setPricechange(String pricechange) {
		this.pricechange = pricechange;
	}

	public Double getChangepercent() {
		return changepercent;
	}

	public void setChangepercent(Double changepercent) {
		this.changepercent = changepercent;
	}

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getSettlement() {
		return settlement;
	}

	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTicktime() {
		return ticktime;
	}

	public void setTicktime(String ticktime) {
		this.ticktime = ticktime;
	}

	public double getPer() {
		return per;
	}

	public void setPer(double per) {
		this.per = per;
	}

	public double getPb() {
		return pb;
	}

	public void setPb(double pb) {
		this.pb = pb;
	}

	public double getMktcap() {
		return mktcap;
	}

	public void setMktcap(double mktcap) {
		this.mktcap = mktcap;
	}

	public double getNmc() {
		return nmc;
	}

	public void setNmc(double nmc) {
		this.nmc = nmc;
	}

	public double getTurnoverratio() {
		return turnoverratio;
	}

	public void setTurnoverratio(double turnoverratio) {
		this.turnoverratio = turnoverratio;
	}

}
