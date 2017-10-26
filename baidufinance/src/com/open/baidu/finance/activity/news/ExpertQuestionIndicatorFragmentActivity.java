/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午5:36:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.news.AdviserPersonBean;
import com.open.baidu.finance.fragment.news.ExpertViewQuestionIndicatorFragment;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午5:36:09
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ExpertQuestionIndicatorFragmentActivity extends CommonTitleBarActivity{
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
			url = UrlUtils.EXPERT_TAB+"10008";
		}
		setCenterTextValue("投资顾问");
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
		Fragment fragment = ExpertViewQuestionIndicatorFragment.newInstance(url,(AdviserPersonBean)getIntent().getSerializableExtra("ADVISER_PERSON_BEAN"), true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		 
	}

	public static void startExpertQuestionIndicatorFragmentActivity(Context context,String url, AdviserPersonBean bean) {
		Intent intent = new Intent();
		intent.putExtra("ADVISER_PERSON_BEAN", bean);
		intent.putExtra("URL", url);
		intent.setClass(context, ExpertQuestionIndicatorFragmentActivity.class);
		context.startActivity(intent);
	}
}