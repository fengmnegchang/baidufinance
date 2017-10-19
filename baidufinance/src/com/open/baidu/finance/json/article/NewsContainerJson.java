/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:29:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.article;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.article.NewsContainerBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:29:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class NewsContainerJson extends CommonJson {
	private List<NewsContainerBean> list;

	public List<NewsContainerBean> getList() {
		return list;
	}

	public void setList(List<NewsContainerBean> list) {
		this.list = list;
	}
	
	

}
