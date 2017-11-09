/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-9下午2:56:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.kline;

import com.open.android.bean.CommonBean;
import com.open.baidu.finance.bean.kline.index.Kdj;
import com.open.baidu.finance.bean.kline.index.Ma;
import com.open.baidu.finance.bean.kline.index.Macd;
import com.open.baidu.finance.bean.kline.index.Rsi;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-9下午2:56:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MashDataBean extends CommonBean {
	private int date;// 20171109,
	private TimeLineBean kline;//
	private Ma ma5;
	private Ma ma10;
	private Ma ma20;
	private Macd macd;
	private Kdj kdj;
	private Rsi rsi;

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public TimeLineBean getKline() {
		return kline;
	}

	public void setKline(TimeLineBean kline) {
		this.kline = kline;
	}

	public Ma getMa5() {
		return ma5;
	}

	public void setMa5(Ma ma5) {
		this.ma5 = ma5;
	}

	public Ma getMa10() {
		return ma10;
	}

	public void setMa10(Ma ma10) {
		this.ma10 = ma10;
	}

	public Ma getMa20() {
		return ma20;
	}

	public void setMa20(Ma ma20) {
		this.ma20 = ma20;
	}

	public Macd getMacd() {
		return macd;
	}

	public void setMacd(Macd macd) {
		this.macd = macd;
	}

	public Kdj getKdj() {
		return kdj;
	}

	public void setKdj(Kdj kdj) {
		this.kdj = kdj;
	}

	public Rsi getRsi() {
		return rsi;
	}

	public void setRsi(Rsi rsi) {
		this.rsi = rsi;
	}

}
