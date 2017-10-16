/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-16下午2:57:20
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.mystock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.mystock.NewGroupNameFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 新建分组
 * @author :fengguangjing
 * @createTime:2017-10-16下午2:57:20
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class NewGroupNameFragmentActivity extends CommonTitleBarActivity{
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
			url = UrlUtils.GATHERMYSTOCK;
		}
		setCenterTextValue("新建分组");
		setStatusBarColor(getResources().getColor(R.color.status_bar_color));
		
		setLeftImageResId(R.drawable.stockdetails_back_n);
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
		Fragment fragment = NewGroupNameFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.id_iv_left){
			finish();
		}
	}

	public static void startNewGroupNameFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, NewGroupNameFragmentActivity.class);
		context.startActivity(intent);
	}
}