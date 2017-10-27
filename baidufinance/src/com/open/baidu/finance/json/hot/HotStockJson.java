/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午4:09:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.hot;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.hot.HotStockBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午4:09:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotStockJson extends CommonJson {
	private List<HotStockBean> list;

	public List<HotStockBean> getList() {
		return list;
	}

	public void setList(List<HotStockBean> list) {
		this.list = list;
	}
	
	

}
