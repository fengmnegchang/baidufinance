/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午4:46:23
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import java.util.List;

import com.open.baidu.finance.bean.news.TagNewsBean;
import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.news.TagNewsDataJson.TagNewsDataModel;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午4:46:23
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class TagNewsDataJson extends CommonDataJson<TagNewsDataModel> {

	public class TagNewsDataModel {
		private List<TagNewsBean> tagnews;

		public List<TagNewsBean> getTagnews() {
			return tagnews;
		}

		public void setTagnews(List<TagNewsBean> tagnews) {
			this.tagnews = tagnews;
		}

	}

}
