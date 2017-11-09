/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-9下午3:01:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean.kline.index;

import java.io.Serializable;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-9下午3:01:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class Macd implements Serializable{
	private float diff;//0.50826919956475,
	private float dea;//0.47242794787026,
	private float macd;//0.071682503388966
	public float getDiff() {
		return diff;
	}
	public void setDiff(float diff) {
		this.diff = diff;
	}
	public float getDea() {
		return dea;
	}
	public void setDea(float dea) {
		this.dea = dea;
	}
	public float getMacd() {
		return macd;
	}
	public void setMacd(float macd) {
		this.macd = macd;
	}
	
	
}
