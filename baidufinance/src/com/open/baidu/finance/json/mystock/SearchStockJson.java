/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午1:53:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.mystock;

import java.util.List;

import com.open.baidu.finance.bean.mystock.SearchStockData;
import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.mystock.SearchStockJson.SearchStockModel;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午1:53:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SearchStockJson extends CommonDataJson<SearchStockModel> {

	public class SearchStockModel {
		private List<SearchStockData> stock_data;
		private List<String> industry_concept_data;// :[],

		public List<SearchStockData> getStock_data() {
			return stock_data;
		}

		public void setStock_data(List<SearchStockData> stock_data) {
			this.stock_data = stock_data;
		}

		public List<String> getIndustry_concept_data() {
			return industry_concept_data;
		}

		public void setIndustry_concept_data(List<String> industry_concept_data) {
			this.industry_concept_data = industry_concept_data;
		}

	}

}
