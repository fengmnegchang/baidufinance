/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午1:33:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.hot;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.hot.MsgStockBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午1:33:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MsgStockJson extends CommonJson {
	private List<MsgStockBean> list;

	public List<MsgStockBean> getList() {
		return list;
	}

	public void setList(List<MsgStockBean> list) {
		this.list = list;
	}
	
	

}
