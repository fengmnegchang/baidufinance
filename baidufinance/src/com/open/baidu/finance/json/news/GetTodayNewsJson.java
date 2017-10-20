/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20上午11:28:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.news;

import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.news.GetTodayNewsJson.GetTodayNewsDataModel;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20上午11:28:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class GetTodayNewsJson extends CommonDataJson<GetTodayNewsDataModel>{

	public class GetTodayNewsDataModel{
		private TodayNewsDataJson todaynews;

		public TodayNewsDataJson getTodaynews() {
			return todaynews;
		}

		public void setTodaynews(TodayNewsDataJson todaynews) {
			this.todaynews = todaynews;
		}
		
		
	}
}
