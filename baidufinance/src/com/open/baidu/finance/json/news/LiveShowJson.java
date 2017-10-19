/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:24:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.news.LiveShowBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:24:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class LiveShowJson extends CommonJson {
	private List<LiveShowBean> list;

	public List<LiveShowBean> getList() {
		return list;
	}

	public void setList(List<LiveShowBean> list) {
		this.list = list;
	}
	
	

}
