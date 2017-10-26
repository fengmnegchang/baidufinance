/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:26:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.hot;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.hot.HotConceptBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:26:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotConceptJson extends CommonJson {
	private List<HotConceptBean> list;

	public List<HotConceptBean> getList() {
		return list;
	}

	public void setList(List<HotConceptBean> list) {
		this.list = list;
	}
	

}
