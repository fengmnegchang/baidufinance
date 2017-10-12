/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12上午9:58:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.MainTabBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12上午9:58:36
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MainTabJson extends CommonJson {
	private List<MainTabBean> list;

	public List<MainTabBean> getList() {
		return list;
	}

	public void setList(List<MainTabBean> list) {
		this.list = list;
	}
	

}
