/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午11:06:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.hot;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.hot.SentimentBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午11:06:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SentimentJson extends CommonJson {
	private List<SentimentBean> list;
	private String dateTime;
	public List<SentimentBean> getList() {
		return list;
	}
	public void setList(List<SentimentBean> list) {
		this.list = list;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}
