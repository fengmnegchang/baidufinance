/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1下午5:47:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1下午5:47:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SyncHorizontalScrollView extends HorizontalScrollView {
	
	private View mView;
	
	public SyncHorizontalScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SyncHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
 
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		//设置控件滚动监听，得到滚动的距离，然后让传进来的view也设置相同的滚动具体
		if(mView!=null) {
			mView.scrollTo(l, t);
		}
	}
	
	/**
	* 设置跟它联动的view
	* @param view
	*/
	public void setScrollView(View view) {
		mView = view;
	}
}