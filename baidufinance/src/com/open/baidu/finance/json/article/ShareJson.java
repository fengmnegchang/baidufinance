/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25下午2:54:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.article;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.article.ShareBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25下午2:54:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ShareJson extends CommonJson {
	private List<ShareBean> list;

	public List<ShareBean> getList() {
		return list;
	}

	public void setList(List<ShareBean> list) {
		this.list = list;
	}
	
}
