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

import com.open.baidu.finance.bean.market.PlateStockBean;

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
public class ComparatorPlateStockRatioType implements Comparator<PlateStockBean>{
	int type;
	public ComparatorPlateStockRatioType(int type){
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(PlateStockBean lhs, PlateStockBean rhs) {
		// TODO Auto-generated method stub
		Double change1 = 0.00;
		if (lhs.getChangepercent() != null) {
			change1 = lhs.getChangepercent().doubleValue();
		}
		Double change2 = 0.00;
		if (rhs.getChangepercent() != null) {
			change2 = rhs.getChangepercent().doubleValue();
		}
		return change1.compareTo(change2) * type;
	}
 

}
