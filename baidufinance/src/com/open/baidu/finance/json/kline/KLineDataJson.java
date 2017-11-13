/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-13下午2:53:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.kline;

import java.util.List;

import com.open.android.json.CommonJson;
import com.open.baidu.finance.bean.kline.TimeLineBean;

/**
 *****************************************************************************************************************************************************************************
 * http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/var%20_sz000725_60_1510555547326=/CN_MarketData.getKLineData?symbol=sz000725&scale=60&ma=no&datalen=1023
 * @author :fengguangjing
 * @createTime:2017-11-13下午2:53:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class KLineDataJson extends CommonJson {
	private List<TimeLineBean> list;

	public List<TimeLineBean> getList() {
		return list;
	}

	public void setList(List<TimeLineBean> list) {
		this.list = list;
	}
	
	

}
