/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-14上午10;//26;//05
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.kline;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.kline.ReportDataBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-14上午10;//26;//05
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class ReportDataJson extends CommonJson {
	private List<ReportDataBean> reportdata;
	private int errorNo;// 0,
	private String errorMsg;// SUCCESS,
	private int pageIndex;// 3,
	private int pageSize;// 15,
	private int pageTotal;// 32,
	private int reportTotal;// 474,

	public List<ReportDataBean> getReportdata() {
		return reportdata;
	}

	public void setReportdata(List<ReportDataBean> reportdata) {
		this.reportdata = reportdata;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public int getReportTotal() {
		return reportTotal;
	}

	public void setReportTotal(int reportTotal) {
		this.reportTotal = reportTotal;
	}

}
