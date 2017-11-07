/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午10:51:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.kline;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.kline.NewsBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午10:51:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class NewsJson extends CommonJson {
	private List<NewsBean> list;

	public List<NewsBean> getList() {
		return list;
	}

	public void setList(List<NewsBean> list) {
		this.list = list;
	}
	

}
