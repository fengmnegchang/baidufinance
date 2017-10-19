/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午9:53:35
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import java.util.List;

import com.open.baidu.finance.bean.news.TagNewsBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午9:53:35
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class TagNewsDataModel {
	private List<TagNewsBean> tagnews;

	public List<TagNewsBean> getTagnews() {
		return tagnews;
	}

	public void setTagnews(List<TagNewsBean> tagnews) {
		this.tagnews = tagnews;
	}
}
