/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午3:51:02
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.mystock;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.bean.mystock.StockBean;
import com.open.baidu.finance.fragment.mystock.MyStockPullToRefreshPinnedSectionListViewFragment;
import com.open.baidu.finance.fragment.mystock.MyStockViewPagerFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午3:51:02
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MyStockViewPagerFragmentActivity extends CommonTitleBarActivity{
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
		setCenterTextValue(getResources().getString(R.string.app_mystock_desp)+"  ");
		setStatusBarColor(getResources().getColor(R.color.status_bar_color));
		setCenterCompoundDrawables(R.drawable.title_bar3_down);
		setLeftTextValue("编辑");
		setLeftTextVisivable(true);
		
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
		Fragment fragment = MyStockViewPagerFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.txt_left:
			//编辑
			MyStockViewPagerFragment fragment = (MyStockViewPagerFragment) getSupportFragmentManager().findFragmentById(R.id.layout_content);
			if(fragment!=null){
				MyStockPullToRefreshPinnedSectionListViewFragment f = (MyStockPullToRefreshPinnedSectionListViewFragment) fragment.listFragment.get(fragment.position);
				GroupBean bean = new GroupBean();
				ArrayList<StockBean> list = (ArrayList<StockBean>) f.getList();
				list.remove(0);
				bean.setStock(list);
				StockEditDragSortListViewFragmentActivity.startMyStockViewPagerFragmentActivity(this, url,bean);
			}
			break;
		default:
			break;
		}
	}

	public static void startMyStockViewPagerFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MyStockViewPagerFragmentActivity.class);
		context.startActivity(intent);
	}
}