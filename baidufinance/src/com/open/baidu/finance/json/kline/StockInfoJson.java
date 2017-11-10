/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-10下午2:03:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.kline;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.kline.StockInfoBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-10下午2:03:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class StockInfoJson extends CommonJson {
	private StockInfoBean stock;

	public StockInfoBean getStock() {
		return stock;
	}

	public void setStock(StockInfoBean stock) {
		this.stock = stock;
	}
	
	

}
