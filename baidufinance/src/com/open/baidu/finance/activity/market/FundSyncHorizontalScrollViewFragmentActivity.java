/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2上午10:54:31
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
import com.open.baidu.finance.fragment.market.FundSyncHorizontalScrollViewFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2上午10:54:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class FundSyncHorizontalScrollViewFragmentActivity extends CommonTitleBarActivity{
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
			url = UrlUtils.GETFUNDNETDATA_OPEN_FUND;
		}
		if (getIntent().getStringExtra("TITLE") != null) {
			setCenterTextValue(getIntent().getStringExtra("TITLE"));
		} else {
			setCenterTextValue("基金");
		}
		
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
		Fragment fragment = FundSyncHorizontalScrollViewFragment.newInstance(url,(getIntent().getStringExtra("TITLE")==null?"基金":getIntent().getStringExtra("TITLE")), true);
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

	public static void startFundSyncHorizontalScrollViewFragmentActivity(Context context, String url,String title) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.putExtra("TITLE", title);
		intent.setClass(context, FundSyncHorizontalScrollViewFragmentActivity.class);
		context.startActivity(intent);
	}
}