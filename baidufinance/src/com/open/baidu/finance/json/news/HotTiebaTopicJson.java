/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:52:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.news.HotTiebaTopicBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:52:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotTiebaTopicJson extends CommonJson {
	private List<HotTiebaTopicBean> list;

	public List<HotTiebaTopicBean> getList() {
		return list;
	}

	public void setList(List<HotTiebaTopicBean> list) {
		this.list = list;
	}
	
	

}
