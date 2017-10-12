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
public class ComparatorCloseType implements Comparator<StockBean>{
	int type;
	public ComparatorCloseType(int type){
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(StockBean lhs, StockBean rhs) {
		// TODO Auto-generated method stub
		Double close1 = 0.00;
		if (lhs.getClose() != null) {
			close1 = lhs.getClose().doubleValue();
		}
		Double close2 = 0.00;
		if (rhs.getClose() != null) {
			close2 = rhs.getClose().doubleValue();
		}
		return close1.compareTo(close2) * type;
	}

}
