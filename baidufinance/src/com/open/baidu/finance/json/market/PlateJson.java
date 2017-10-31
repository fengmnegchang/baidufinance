/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:19:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.market;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.market.PlateBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:19:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PlateJson extends CommonJson {
	private List<PlateBean> list;

	public List<PlateBean> getList() {
		return list;
	}

	public void setList(List<PlateBean> list) {
		this.list = list;
	}
	

}
