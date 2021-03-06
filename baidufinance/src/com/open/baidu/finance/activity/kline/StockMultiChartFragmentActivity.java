/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午11:18:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.kline;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.kline.StockCombinedChartFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午11:18:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class StockMultiChartFragmentActivity extends CommonTitleBarActivity{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		if (getIntent().getStringExtra("URL") != null) {
			url = getIntent().getStringExtra("URL");
		} else {
			url = UrlUtils.STOCKTIMELINE+"sz000725";
		}
		setCenterTextValue("分时多控件");
		setStatusBarColor(getResources().getColor(R.color.status_bar_color));
		
		setLeftVisivableGone();
		setRightVisivableGone();
		addfragment();
	}
	
	
	/**
	 * 设置title
	 */
	public void setCenterTitle(String title){
		setCenterTextValue(title+"  ");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#addfragment()
	 */
	@Override
	public void addfragment() {
		// TODO Auto-generated method stub
		super.addfragment();
		Fragment fragment = StockCombinedChartFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}
	
 

	public static void startStockMultiChartFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, StockMultiChartFragmentActivity.class);
		context.startActivity(intent);
	}
}