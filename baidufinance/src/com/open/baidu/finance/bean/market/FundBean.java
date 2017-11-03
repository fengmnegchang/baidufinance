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
	
	//基金预测净值
	private Double pre_nav;// "2.2706",
	private Double nav_chg;// "0.38019452",
	private Double last_nav;// "2.2620",
	private String nav_date;// "2017-11-02",
	private Double accu_nav;// "2.2620",
	private String pre_date;// "2017-11-03"
	
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

	public Double getPre_nav() {
		return pre_nav;
	}

	public void setPre_nav(Double pre_nav) {
		this.pre_nav = pre_nav;
	}

	public Double getNav_chg() {
		return nav_chg;
	}

	public void setNav_chg(Double nav_chg) {
		this.nav_chg = nav_chg;
	}

	public Double getLast_nav() {
		return last_nav;
	}

	public void setLast_nav(Double last_nav) {
		this.last_nav = last_nav;
	}

	public String getNav_date() {
		return nav_date;
	}

	public void setNav_date(String nav_date) {
		this.nav_date = nav_date;
	}

	public Double getAccu_nav() {
		return accu_nav;
	}

	public void setAccu_nav(Double accu_nav) {
		this.accu_nav = accu_nav;
	}

	public String getPre_date() {
		return pre_date;
	}

	public void setPre_date(String pre_date) {
		this.pre_date = pre_date;
	}

}
