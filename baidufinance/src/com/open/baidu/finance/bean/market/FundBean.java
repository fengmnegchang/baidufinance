/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-1下午5;//16;//11
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
 * 基金 代码 名称 最新公布净值 累计净值 前单位净值 净值增长率 公布日期 基金规模
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-1下午5;//16;//11
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class FundBean extends CommonBean {
	private String symbol;// "000001",
	private String name;// "华夏成长混合",
	private Double dwjz;// "1.2380",
	private Double ljdwjz;// "3.5690",
	private Double zrjz;// 1.231,
	private Double jzzz;// "0.56864338",
	private String date;// "2017-10-31",
	private Double jjgm;// "43.0363"

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDwjz() {
		return dwjz;
	}

	public void setDwjz(Double dwjz) {
		this.dwjz = dwjz;
	}

	public Double getLjdwjz() {
		return ljdwjz;
	}

	public void setLjdwjz(Double ljdwjz) {
		this.ljdwjz = ljdwjz;
	}

	public Double getZrjz() {
		return zrjz;
	}

	public void setZrjz(Double zrjz) {
		this.zrjz = zrjz;
	}

	public Double getJzzz() {
		return jzzz;
	}

	public void setJzzz(Double jzzz) {
		this.jzzz = jzzz;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getJjgm() {
		return jjgm;
	}

	public void setJjgm(Double jjgm) {
		this.jjgm = jjgm;
	}

}
