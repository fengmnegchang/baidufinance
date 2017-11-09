/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-9下午2:59:04
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
 * @createTime:2017-11-9下午2:59:04
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class Rsi implements Serializable{
	private float rsi1;//71.978049603588,
	private float rsi2;//74.089992060221,
	private float rsi3;//74.021535429197
	public float getRsi1() {
		return rsi1;
	}
	public void setRsi1(float rsi1) {
		this.rsi1 = rsi1;
	}
	public float getRsi2() {
		return rsi2;
	}
	public void setRsi2(float rsi2) {
		this.rsi2 = rsi2;
	}
	public float getRsi3() {
		return rsi3;
	}
	public void setRsi3(float rsi3) {
		this.rsi3 = rsi3;
	}
	
	
}
