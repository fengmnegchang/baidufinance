/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:48:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.news.ExpertViewBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:48:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ExpertViewJson extends CommonJson {
	private List<ExpertViewBean> list;

	public List<ExpertViewBean> getList() {
		return list;
	}

	public void setList(List<ExpertViewBean> list) {
		this.list = list;
	}
	
}
