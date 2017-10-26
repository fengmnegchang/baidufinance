/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:08:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.news.AdviserPersonBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:08:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class AdviserPersonJson extends CommonJson {
	private List<AdviserPersonBean> list;

	public List<AdviserPersonBean> getList() {
		return list;
	}

	public void setList(List<AdviserPersonBean> list) {
		this.list = list;
	}
	
	
}
