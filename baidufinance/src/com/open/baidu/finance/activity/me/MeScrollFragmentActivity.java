/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午9:49:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.me;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.me.MeScrollFragment;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午9:49:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MeScrollFragmentActivity extends CommonTitleBarActivity{
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
		}
		setCenterTextValue(" ");
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
	
	/* (non-Javadoc)
	 * @see com.open.android.activity.common.CommonTitleBarActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.txt_right){
			finish();
		}
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
		Fragment fragment = MeScrollFragment.newInstance(url,  true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}

	public static void startMeScrollFragmentActivity(Context context, String url ) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MeScrollFragmentActivity.class);
		context.startActivity(intent);
	}
}