/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1下午5:19:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.market;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.market.FundBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1下午5:19:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class FundJson extends CommonJson {
	private List<FundBean> list;

	public List<FundBean> getList() {
		return list;
	}

	public void setList(List<FundBean> list) {
		this.list = list;
	}
	

}
