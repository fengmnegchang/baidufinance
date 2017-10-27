/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午5:09:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.hot;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.hot.FollowBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午5:09:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class FollowJson extends CommonJson {
	private List<FollowBean> list;

	public List<FollowBean> getList() {
		return list;
	}

	public void setList(List<FollowBean> list) {
		this.list = list;
	}
	
	

}
