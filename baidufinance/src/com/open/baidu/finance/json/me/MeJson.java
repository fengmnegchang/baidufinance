/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午10:19:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.me;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.me.MeBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午10:19:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MeJson extends CommonJson {
	private List<MeBean> list;

	public List<MeBean> getList() {
		return list;
	}

	public void setList(List<MeBean> list) {
		this.list = list;
	}
	

}
