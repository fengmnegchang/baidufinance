/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25下午1:31:47
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.article;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.article.EmojiBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25下午1:31:47
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class EmojiJson extends CommonJson {
	private List<EmojiBean> list;

	public List<EmojiBean> getList() {
		return list;
	}

	public void setList(List<EmojiBean> list) {
		this.list = list;
	}
	

}
