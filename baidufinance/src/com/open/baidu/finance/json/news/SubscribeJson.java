/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:17:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.news.SubscribeBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:17:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SubscribeJson extends CommonJson {
	private List<SubscribeBean> list;

	public List<SubscribeBean> getList() {
		return list;
	}

	public void setList(List<SubscribeBean> list) {
		this.list = list;
	}
	

}
