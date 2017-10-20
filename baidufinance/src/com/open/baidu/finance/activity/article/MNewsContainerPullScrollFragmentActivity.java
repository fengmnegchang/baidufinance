/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:31:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.article;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.android.utils.ScreenUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.article.MNewsContainerPullScrollFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:31:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MNewsContainerPullScrollFragmentActivity extends CommonTitleBarActivity{
	private boolean largeSize;
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
			url = UrlUtils.ARTICLE;
		}
		setCenterTextValue("财经资讯");
		setStatusBarColor(getResources().getColor(R.color.status_bar_color));
		
		setLeftImageResId(R.drawable.stockdetails_back_n);
		setRightImageResId(R.drawable.news_checkbox_text_1);
		id_iv_right.getLayoutParams().width =  (int) ScreenUtils.getIntToDip(this, 40);
		id_iv_right.getLayoutParams().height = (int) ScreenUtils.getIntToDip(this, 30);
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
		Fragment fragment = MNewsContainerPullScrollFragment.newInstance(url, true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if(v.getId()==R.id.id_iv_left){
			finish();
		}else if(v.getId()==R.id.id_iv_right){
			MNewsContainerPullScrollFragment fragment = (MNewsContainerPullScrollFragment) getSupportFragmentManager().findFragmentById(R.id.layout_content);
			 if(largeSize){
				 //小
				 setRightImageResId(R.drawable.news_checkbox_text_1);
			 }else{
				 //大
				 setRightImageResId(R.drawable.news_checkbox_text_2);
			 }
			 largeSize = !largeSize;
			 fragment.setLargeSize(largeSize);
		}
	}

	public static void startMNewsContainerPullScrollFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MNewsContainerPullScrollFragmentActivity.class);
		context.startActivity(intent);
	}
}
