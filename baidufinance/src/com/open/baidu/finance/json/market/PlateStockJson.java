/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午4:45:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.market;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.market.PlateStockBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午4:45:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PlateStockJson extends CommonJson {
	private List<PlateStockBean> list;

	public List<PlateStockBean> getList() {
		return list;
	}

	public void setList(List<PlateStockBean> list) {
		this.list = list;
	}
	
	

}
