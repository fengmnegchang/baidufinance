/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-14上午11;//17;//11
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.kline;

import java.util.List;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-14上午11;//17;//11
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class CompanyProfilesBean extends CommonBean {
	private List<ItemBean> description;
	private StockIncome stockIncome;

	public StockIncome getStockIncome() {
		return stockIncome;
	}

	public void setStockIncome(StockIncome stockIncome) {
		this.stockIncome = stockIncome;
	}

	public List<ItemBean> getDescription() {
		return description;
	}

	public void setDescription(List<ItemBean> description) {
		this.description = description;
	}

	public class StockIncome {
		private List<Income> income;

		public List<Income> getIncome() {
			return income;
		}

		public void setIncome(List<Income> income) {
			this.income = income;
		}

	}

	public class Income {
		private String name;// "智慧系统业务",
		private float money;// 8271879666,
		private String InComeDate;// 20170630,
		private float InComeTime;// 0,
		private float changeRatio;// 55.582949186118

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getMoney() {
			return money;
		}

		public void setMoney(float money) {
			this.money = money;
		}

		public String getInComeDate() {
			return InComeDate;
		}

		public void setInComeDate(String inComeDate) {
			InComeDate = inComeDate;
		}

		public float getInComeTime() {
			return InComeTime;
		}

		public void setInComeTime(float inComeTime) {
			InComeTime = inComeTime;
		}

		public float getChangeRatio() {
			return changeRatio;
		}

		public void setChangeRatio(float changeRatio) {
			this.changeRatio = changeRatio;
		}

	}

	public class ItemBean {
		private String itemName;// 公司名称;//,
		private String itemValue;// 京东方科技集团股份有限公司

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public String getItemValue() {
			return itemValue;
		}

		public void setItemValue(String itemValue) {
			this.itemValue = itemValue;
		}

	}

}
