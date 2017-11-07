/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7下午3:55:53
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
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7下午3:55:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class TimeLineJson extends CommonJson {
	private int errorNo;//0,
	private String errorMsg;//SUCCESS",
    private List<TimeLineBean> timeLine;
	public int getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public List<TimeLineBean> getTimeLine() {
		return timeLine;
	}
	public void setTimeLine(List<TimeLineBean> timeLine) {
		this.timeLine = timeLine;
	}
    
    
}
