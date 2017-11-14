/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14上午11:37:20
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.kline;

import java.util.List;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14上午11:37:20
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockBasicInfoBean extends CommonBean {
	private int errorNo;// 0,
	private String errorMsg;// SUCCESS,
	private String industry;// 制造业,
	private String mainBusiness;// 以光电子与显示器件、显示信息终端设备的产销为主营，北京市国资委控股。公司是中国内地最大，全球排名第九位的TFT-LCD制造商，也是中国内地唯一一家拥有TFT-LCD核心技术的企业，全球第一大显示器生产商。,
	private String netIncreaseRate;// 4503.512504,
	private String majoGrow;// 51.408095,
	private String date;// 20170930,
	private StockBasicInfoExt30 stockBasicInfoExt30;
	private List<RelatedConcept> relatedConcept;

	public class RelatedConcept {
		private String conceptName;// 转融券",
		private String conceptId;// 1000120",
		private float changeRatio;// -0.63414174318314,
		private MaxStock riseMaxStock;
		private MaxStock fallMaxStock;
		private float price;
		private float netChange;
		
		

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}

		public float getNetChange() {
			return netChange;
		}

		public void setNetChange(float netChange) {
			this.netChange = netChange;
		}

		public String getConceptName() {
			return conceptName;
		}

		public void setConceptName(String conceptName) {
			this.conceptName = conceptName;
		}

		public String getConceptId() {
			return conceptId;
		}

		public void setConceptId(String conceptId) {
			this.conceptId = conceptId;
		}

		public float getChangeRatio() {
			return changeRatio;
		}

		public void setChangeRatio(float changeRatio) {
			this.changeRatio = changeRatio;
		}

		public MaxStock getRiseMaxStock() {
			return riseMaxStock;
		}

		public void setRiseMaxStock(MaxStock riseMaxStock) {
			this.riseMaxStock = riseMaxStock;
		}

		public MaxStock getFallMaxStock() {
			return fallMaxStock;
		}

		public void setFallMaxStock(MaxStock fallMaxStock) {
			this.fallMaxStock = fallMaxStock;
		}

		public class MaxStock {
			private String exchange;// sh",
			private String stockCode;// 600050",
			private String stockName;// 中国联通",
			private int stockStatus;// 2,
			private int asset;// 0

			public String getExchange() {
				return exchange;
			}

			public void setExchange(String exchange) {
				this.exchange = exchange;
			}

			public String getStockCode() {
				return stockCode;
			}

			public void setStockCode(String stockCode) {
				this.stockCode = stockCode;
			}

			public String getStockName() {
				return stockName;
			}

			public void setStockName(String stockName) {
				this.stockName = stockName;
			}

			public int getStockStatus() {
				return stockStatus;
			}

			public void setStockStatus(int stockStatus) {
				this.stockStatus = stockStatus;
			}

			public int getAsset() {
				return asset;
			}

			public void setAsset(int asset) {
				this.asset = asset;
			}

		}
	}

	public List<RelatedConcept> getRelatedConcept() {
		return relatedConcept;
	}

	public void setRelatedConcept(List<RelatedConcept> relatedConcept) {
		this.relatedConcept = relatedConcept;
	}

	public int getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getNetIncreaseRate() {
		return netIncreaseRate;
	}

	public void setNetIncreaseRate(String netIncreaseRate) {
		this.netIncreaseRate = netIncreaseRate;
	}

	public String getMajoGrow() {
		return majoGrow;
	}

	public void setMajoGrow(String majoGrow) {
		this.majoGrow = majoGrow;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public StockBasicInfoExt30 getStockBasicInfoExt30() {
		return stockBasicInfoExt30;
	}

	public void setStockBasicInfoExt30(StockBasicInfoExt30 stockBasicInfoExt30) {
		this.stockBasicInfoExt30 = stockBasicInfoExt30;
	}

	public class StockBasicInfoExt30 {
		private List<Events> events;
		private NetProfit netProfit;
		private NetProfit businessIncome;
		private NetProfit earningsPerShare;

		public List<Events> getEvents() {
			return events;
		}

		public void setEvents(List<Events> events) {
			this.events = events;
		}

		public NetProfit getNetProfit() {
			return netProfit;
		}

		public void setNetProfit(NetProfit netProfit) {
			this.netProfit = netProfit;
		}

		public NetProfit getBusinessIncome() {
			return businessIncome;
		}

		public void setBusinessIncome(NetProfit businessIncome) {
			this.businessIncome = businessIncome;
		}

		public NetProfit getEarningsPerShare() {
			return earningsPerShare;
		}

		public void setEarningsPerShare(NetProfit earningsPerShare) {
			this.earningsPerShare = earningsPerShare;
		}

		public class NetProfit {
			private int eventType;
			private List<String> year;
			private List<Float> fianceData;
			private List<Float> industryAvg;

			public int getEventType() {
				return eventType;
			}

			public void setEventType(int eventType) {
				this.eventType = eventType;
			}

			public List<String> getYear() {
				return year;
			}

			public void setYear(List<String> year) {
				this.year = year;
			}

			public List<Float> getFianceData() {
				return fianceData;
			}

			public void setFianceData(List<Float> fianceData) {
				this.fianceData = fianceData;
			}

			public List<Float> getIndustryAvg() {
				return industryAvg;
			}

			public void setIndustryAvg(List<Float> industryAvg) {
				this.industryAvg = industryAvg;
			}

		}

		public class Events {
			private String datetime;// 2017-07-14,
			private String eventType;// 业绩披露,
			private String message;// 预计2017年半年度归属于上市公司股东的净利润盈利：420,000万元–450,000万元，同比实现扭亏为盈。

			public String getDatetime() {
				return datetime;
			}

			public void setDatetime(String datetime) {
				this.datetime = datetime;
			}

			public String getEventType() {
				return eventType;
			}

			public void setEventType(String eventType) {
				this.eventType = eventType;
			}

			public String getMessage() {
				return message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

		}
	}
}
