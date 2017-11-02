/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午5:08:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.utils;

import java.util.Comparator;

import com.open.baidu.finance.bean.market.FundBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午5:08:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ComparatorFundRatioType implements Comparator<FundBean>{
	int type;
	public ComparatorFundRatioType(int type){
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(FundBean lhs, FundBean rhs) {
		// TODO Auto-generated method stub
		Double change1 = 0.00;
		if (lhs.getJzzz() != null) {
			change1 = lhs.getJzzz().doubleValue();
		}
		Double change2 = 0.00;
		if (rhs.getJzzz() != null) {
			change2 = rhs.getJzzz().doubleValue();
		}
		return change1.compareTo(change2) * type;
	}
 

}
