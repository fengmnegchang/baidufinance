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

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.mystock.GroupEditDragSortListViewFragment;
import com.open.baidu.finance.json.mystock.GroupListJson;
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
public class GroupEditDragSortListViewFragmentActivity extends CommonTitleBarActivity{
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
		setCenterTextValue("编辑分组  ");
		setStatusBarColor(getResources().getColor(R.color.status_bar_color));
		setLeftVisivableGone();
		
		setRightTextVisivable(true);
		setRightTextValue("完成");
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
		Fragment fragment = GroupEditDragSortListViewFragment.newInstance(url,(GroupListJson)getIntent().getSerializableExtra("GROUPJSON"), true);
		getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, fragment).commit();
	}

	public static void startGroupEditDragSortListViewFragmentActivity(Context context, String url,GroupListJson  groupJson) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.putExtra("GROUPJSON", groupJson);
		intent.setClass(context, GroupEditDragSortListViewFragmentActivity.class);
		context.startActivity(intent);
	}
}