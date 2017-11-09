/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-9下午3;//04;//13
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.kline;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.kline.MashDataBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-11-9下午3;//04;//13
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class MashDataJson extends CommonJson {
	private int errorNo;// 0,
	private String errorMsg;// SUCCESS,
	private List<MashDataBean> mashData;

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

	public List<MashDataBean> getMashData() {
		return mashData;
	}

	public void setMashData(List<MashDataBean> mashData) {
		this.mashData = mashData;
	}

}
