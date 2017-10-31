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

import com.open.baidu.finance.bean.market.PlateBean;

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
public class ComparatorPlateRatioType implements Comparator<PlateBean>{
	int type;
	public ComparatorPlateRatioType(int type){
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(PlateBean lhs, PlateBean rhs) {
		// TODO Auto-generated method stub
		Double change1 = 0.00;
		if (lhs.getNetChangeRate() != null) {
			change1 = lhs.getNetChangeRate().doubleValue();
		}
		Double change2 = 0.00;
		if (rhs.getNetChangeRate() != null) {
			change2 = rhs.getNetChangeRate().doubleValue();
		}
		return change1.compareTo(change2) * type;
	}

}
