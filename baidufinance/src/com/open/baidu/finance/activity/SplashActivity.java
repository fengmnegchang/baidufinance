/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午1:53:23
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.open.android.R;
import com.open.android.activity.common.CommonCommonFragmentActivity;
import com.open.baidu.finance.fragment.SplashFragment;
import com.open.baidu.finance.presenter.impl.SplashPresenterImpl;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午1:53:23
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SplashActivity extends CommonCommonFragmentActivity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.activity.CommonFragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		requestWindowFeature(Window.FEATURE_NO_TITLE);    
//        //全屏    
//       getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,      
//                      WindowManager.LayoutParams. FLAG_FULLSCREEN);   
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_common_f);
		init();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#addfragment()
	 */
	@Override
	public void addfragment() {
		// TODO Auto-generated method stub
		SplashFragment fragment = SplashFragment.newInstance();
		getSupportFragmentManager().beginTransaction().replace(R.id.id_common_fragment, fragment).commit();
		new SplashPresenterImpl(this, fragment);
	}


	public static void startSplashActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, SplashActivity.class);
		context.startActivity(intent);
	}
}
