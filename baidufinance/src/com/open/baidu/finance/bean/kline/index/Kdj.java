/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-9下午3:00:14
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
 * @createTime:2017-11-9下午3:00:14
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class Kdj implements Serializable{
	private float k;//78.042390188685,
	private float d;//81.324886293791,
	private float j;//71.477397978473
	public float getK() {
		return k;
	}
	public void setK(float k) {
		this.k = k;
	}
	public float getD() {
		return d;
	}
	public void setD(float d) {
		this.d = d;
	}
	public float getJ() {
		return j;
	}
	public void setJ(float j) {
		this.j = j;
	}
	
	
}
