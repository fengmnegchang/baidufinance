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
import com.open.baidu.finance.fragment.kline.StockScrollMarketFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 个股信息
 * @author :fengguangjing
 * @createTime:2017-11-7上午11:18:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class StockScrollMarketFragmentActivity extends CommonTitleBarActivity{
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.activity.CommonFragmentActivity#initValue()
	 */
	@Override
	protected void initValue() {
		// TODO Auto-generated method stub
		if (getIntent().getStringExtra("URL") != null) {
			url = UrlUtils.STOCK+ getIntent().getStringExtra("URL")+".html";
			setCenterTextValue(getIntent().getStringExtra("STOCKNAME")+"("+getIntent().getStringExtra("STOCKCODE")+")");
		} else {
			url = UrlUtils.STOCK+"sz000725.html";
			setCenterTextValue("京东方A(000725)");
		}
		setCenterTimeTextValue("交易中 11-10 14:00:33");
		setStatusBarColor(getResources().getColor(R.color.red_color));
		
		setLeftImageResId(R.drawable.stockdetails_back_n);
		setRightImage2ResId(R.drawable.searchbox_logo_normal);
		setRightImageResId(R.drawable.refresh_loading);
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
		Fragment fragment = StockScrollMarketFragment.newInstance(url,(getIntent().getStringExtra("URL")==null?"sz000725":getIntent().getStringExtra("URL")),getIntent().getStringExtra("STOCKCODE"), true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}
	
 

	public static void startStockScrollMarketFragmentActivity(Context context, String url,String stockName,String stockCode) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.putExtra("STOCKNAME", stockName);
		intent.putExtra("STOCKCODE", stockCode);
		intent.setClass(context, StockScrollMarketFragmentActivity.class);
		context.startActivity(intent);
	}
}