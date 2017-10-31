/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:51:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.market;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.market.PlatePullToRefreshPinnedSectionListViewFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:51:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PlatePullToRefreshPinnedSectionListViewFragmentActivity extends CommonTitleBarActivity{
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
			url = UrlUtils.QMXINDUSTRYHQ;
		}
		setCenterTextValue("行业跌涨幅");
		setStatusBarColor(getResources().getColor(R.color.status_bar_color));
		
		setLeftImageResId(R.drawable.stockdetails_back_n);
		setRightImageResId(R.drawable.refresh_loading);
		setRightImage2ResId(R.drawable.searchbox_logo_normal);
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
		Fragment fragment = PlatePullToRefreshPinnedSectionListViewFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.id_iv_left){
		}else if(v.getId()==R.id.id_iv_right){
		}
	}

	public static void startPlatePullToRefreshPinnedSectionListViewFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, PlatePullToRefreshPinnedSectionListViewFragmentActivity.class);
		context.startActivity(intent);
	}
}