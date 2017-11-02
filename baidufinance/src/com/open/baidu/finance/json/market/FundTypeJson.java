/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2下午4:39:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.market;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.market.FundTypeBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2下午4:39:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class FundTypeJson extends CommonJson {
	private List<FundTypeBean> list;

	public List<FundTypeBean> getList() {
		return list;
	}

	public void setList(List<FundTypeBean> list) {
		this.list = list;
	}
	
	

}
