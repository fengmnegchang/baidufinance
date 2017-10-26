/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:19:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.hot;

import java.util.List;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:19:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class HotConceptBean extends CommonBean {
	private String name;
	private String searchCount;
	private String time;
	private String subject;
	private String event;
	private List<HotStockBean> stocklist;
	private int viewType;
	
	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(String searchCount) {
		this.searchCount = searchCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public List<HotStockBean> getStocklist() {
		return stocklist;
	}

	public void setStocklist(List<HotStockBean> stocklist) {
		this.stocklist = stocklist;
	}

}
