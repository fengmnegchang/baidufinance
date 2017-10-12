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

import com.open.baidu.finance.bean.mystock.StockBean;

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
public class ComparatorNetRatioType implements Comparator<StockBean>{
	int type;
	public ComparatorNetRatioType(int type){
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(StockBean lhs, StockBean rhs) {
		// TODO Auto-generated method stub
		Double change1 = 0.00;
		if (lhs.getNetChangeRatio() != null) {
			change1 = lhs.getNetChangeRatio().doubleValue();
		}
		Double change2 = 0.00;
		if (rhs.getNetChangeRatio() != null) {
			change2 = rhs.getNetChangeRatio().doubleValue();
		}
		return change1.compareTo(change2) * type;
	}

}
