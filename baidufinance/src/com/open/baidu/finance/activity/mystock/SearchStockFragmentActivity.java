/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午2:18:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.mystock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.open.android.activity.common.CommonCommonFragmentActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.mystock.SearchStockFragment;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午2:18:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SearchStockFragmentActivity extends CommonCommonFragmentActivity {

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
		
		setStatusBarColor(getResources().getColor(R.color.round_solid_color));
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.activity.CommonFragmentActivity#addfragment()
	 */
	@Override
	public void addfragment() {
		// TODO Auto-generated method stub
		SearchStockFragment fragment = SearchStockFragment.newInstance(url,true);
		getSupportFragmentManager().beginTransaction().replace(R.id.id_common_fragment, fragment).commit();
	}


	public static void startSearchStockFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, SearchStockFragmentActivity.class);
		context.startActivity(intent);
	}

}
