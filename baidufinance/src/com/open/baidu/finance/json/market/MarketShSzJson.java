/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1上午10:15:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.market;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.market.MarketShSzBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1上午10:15:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MarketShSzJson extends CommonJson {
	private List<MarketShSzBean> list;

	public List<MarketShSzBean> getList() {
		return list;
	}

	public void setList(List<MarketShSzBean> list) {
		this.list = list;
	}
	
	

}
