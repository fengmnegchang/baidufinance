/**
 *****************************************************************************************************************************************************************************
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-17下午1;//52;//15
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.mystock;

import com.open.android.bean.CommonBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author ;//fengguangjing
 * @createTime;//2017-10-17下午1;//52;//15
 * @version;//4.2.4
 * @modifyTime;//
 * @modifyAuthor;//
 * @description;//
 ***************************************************************************************************************************************************************************** 
 */
public class SearchStockData extends CommonBean {
	public String f_exchange;// sz,
	public String f_asset;// 0,
	public String f_code;// sz000001,
	public String f_symbol;// 000001,
	public String f_symbolName;// 平安银行,
	public String f_usedName;// ,
	public String f_fullName;// ,
	public String f_otherName;//
	private boolean isBottom;
	private String groupId;

	public String getF_exchange() {
		return f_exchange;
	}

	public void setF_exchange(String f_exchange) {
		this.f_exchange = f_exchange;
	}

	public String getF_asset() {
		return f_asset;
	}

	public void setF_asset(String f_asset) {
		this.f_asset = f_asset;
	}

	public String getF_code() {
		return f_code;
	}

	public void setF_code(String f_code) {
		this.f_code = f_code;
	}

	public String getF_symbol() {
		return f_symbol;
	}

	public void setF_symbol(String f_symbol) {
		this.f_symbol = f_symbol;
	}

	public String getF_symbolName() {
		return f_symbolName;
	}

	public void setF_symbolName(String f_symbolName) {
		this.f_symbolName = f_symbolName;
	}

	public String getF_usedName() {
		return f_usedName;
	}

	public void setF_usedName(String f_usedName) {
		this.f_usedName = f_usedName;
	}

	public String getF_fullName() {
		return f_fullName;
	}

	public void setF_fullName(String f_fullName) {
		this.f_fullName = f_fullName;
	}

	public String getF_otherName() {
		return f_otherName;
	}

	public void setF_otherName(String f_otherName) {
		this.f_otherName = f_otherName;
	}

	public boolean isBottom() {
		return isBottom;
	}

	public void setBottom(boolean isBottom) {
		this.isBottom = isBottom;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
