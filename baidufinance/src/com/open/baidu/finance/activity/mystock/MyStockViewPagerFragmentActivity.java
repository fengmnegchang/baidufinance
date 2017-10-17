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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.open.android.activity.common.CommonTitleBarActivity;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.mystock.GroupPopupAdapter;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.bean.mystock.StockBean;
import com.open.baidu.finance.fragment.mystock.MyStockPullToRefreshPinnedSectionListViewFragment;
import com.open.baidu.finance.fragment.mystock.MyStockViewPagerFragment;
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
public class MyStockViewPagerFragmentActivity extends CommonTitleBarActivity{
	public PopupWindow popupWindow;
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
			url = UrlUtils.GATHERMYSTOCK_ALL;
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
				GroupListJson  groupJson = new GroupListJson();
				groupJson.setGroupList(fragment.getList());
				StockEditDragSortListViewFragmentActivity.startMyStockViewPagerFragmentActivity(this, url,groupJson,fragment.position);
			}
			break;
		case R.id.txt_title:
			//分组
			showPopupWindow(txt_title);
			break;
		default:
			break;
		}
	}
	
	public void showPopupWindow(View parent) {
		// 加载布局
		View view = LayoutInflater.from(this).inflate(R.layout.layout_stock_group_popup_window, null);
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		
		MyStockViewPagerFragment fragment = (MyStockViewPagerFragment) getSupportFragmentManager().findFragmentById(R.id.layout_content);
		GroupPopupAdapter mGroupPopupAdapter = new GroupPopupAdapter(this, fragment.getList());
		ListView listview = (ListView) view.findViewById(R.id.listview);
		listview.setAdapter(mGroupPopupAdapter);
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), fragment.getList().size()*150+150);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);//设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));

		popupWindow.showAsDropDown(parent, 0, -100);
		
		TextView txt_add_group = (TextView) view.findViewById(R.id.txt_add_group);
		TextView txt_edit_group = (TextView) view.findViewById(R.id.txt_edit_group);
		ImageView img_close = (ImageView) view.findViewById(R.id.img_close);
		img_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
			}
		});
		txt_add_group.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//新建分组
				// TODO Auto-generated method stub
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
				NewGroupNameFragmentActivity.startNewGroupNameFragmentActivity(MyStockViewPagerFragmentActivity.this,"", "",null);
			}
		});
		txt_edit_group.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//编辑分组
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
				MyStockViewPagerFragment fragment = (MyStockViewPagerFragment) getSupportFragmentManager().findFragmentById(R.id.layout_content);
				GroupListJson  groupJson = new GroupListJson();
				groupJson.setGroupList(fragment.getList());
				GroupEditDragSortListViewFragmentActivity.startGroupEditDragSortListViewFragmentActivity(MyStockViewPagerFragmentActivity.this, url, groupJson);
			}
		});
		popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 *            屏幕透明度0.0-1.0 1表示完全不透明
	 */
	public void setBackgroundAlpha(float bgAlpha) {
	    WindowManager.LayoutParams lp = getWindow()
	            .getAttributes();
	    lp.alpha = bgAlpha;
	    getWindow().setAttributes(lp);
	}


	public static void startMyStockViewPagerFragmentActivity(Context context, String url) {
		Intent intent = new Intent();
		intent.putExtra("URL", url);
		intent.setClass(context, MyStockViewPagerFragmentActivity.class);
		context.startActivity(intent);
	}
}