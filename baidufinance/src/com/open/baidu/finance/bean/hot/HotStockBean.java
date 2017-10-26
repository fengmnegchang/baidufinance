/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:22:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.hot;

import com.open.android.bean.CommonBean;

/**
 *****************************************************************************************************************************************************************************
 *  <li class="no-click" data-code="sz000063" data-name="中兴通讯">
    <div class="column2">
        <a href="/stock/sz000063.html" target="_blank">
            <div>中兴通讯</div>
            <div class="code">000063</div>
        </a>
    </div>
    <div class="column2" data-close>30.00</div>
    <div data-ratio
                                        class="column2 s-up">
                                    +6.12%
    </div>
    <div class="column2 opinion">
        <span class="zan zan-up"></span>
        <span data-predict-up>0</span>
    </div>
    <div class="column2 opinion">
        <span class="zan zan-down"></span>
        <span data-predict-down>0</span>
    </div>
    <div class="column2">
        <button class="watch">加自选</button>
    </div>
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:22:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotStockBean extends CommonBean {
	private String stockName;
	private String stockCode;
	private String close;
	private String rate;
	private String up;
	private String down;
	private String href;
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
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getUp() {
		return up;
	}
	public void setUp(String up) {
		this.up = up;
	}
	public String getDown() {
		return down;
	}
	public void setDown(String down) {
		this.down = down;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	
}
