/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-15上午11:24:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.kline;

import java.util.List;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-15上午11:24:11
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class ShareHolderBean {
	private int errorNo;// 0,
	private String errorMsg;// SUCCESS",
	private Adata Adata;//

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

	public Adata getAdata() {
		return Adata;
	}

	public void setAdata(Adata adata) {
		Adata = adata;
	}

	public class Adata {
		private List<ShareHolder> ltsh;
		private List<ShareHolder> jjsh;
		private List<ShareHolder> atth;

		public List<ShareHolder> getLtsh() {
			return ltsh;
		}

		public void setLtsh(List<ShareHolder> ltsh) {
			this.ltsh = ltsh;
		}

		public List<ShareHolder> getJjsh() {
			return jjsh;
		}

		public void setJjsh(List<ShareHolder> jjsh) {
			this.jjsh = jjsh;
		}

		public List<ShareHolder> getAtth() {
			return atth;
		}

		public void setAtth(List<ShareHolder> atth) {
			this.atth = atth;
		}

		public class ShareHolder {
			private String name;// "北京国有资本经营管理中心",
			private double percent;// 11.68,
			private int changeType;// 0,
			private double changeCount;// 0

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public double getPercent() {
				return percent;
			}

			public void setPercent(double percent) {
				this.percent = percent;
			}

			public int getChangeType() {
				return changeType;
			}

			public void setChangeType(int changeType) {
				this.changeType = changeType;
			}

			public double getChangeCount() {
				return changeCount;
			}

			public void setChangeCount(double changeCount) {
				this.changeCount = changeCount;
			}

		}
	}
}
