/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午4:32:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.hot;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PinnedSectionListView.PinnedSectionListAdapter;
import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.hot.HotConceptBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午4:32:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class HotConceptPinnedSectionListAdapter extends CommonAdapter<HotConceptBean> implements PinnedSectionListAdapter {
	public static final int ITEM_VIEW_TYPE_HEADER = 1;

	public HotConceptPinnedSectionListAdapter(Context mContext, List<HotConceptBean> list) {
		super(mContext, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HotConceptBean bean = (HotConceptBean) getItem(position);
		if (getItemViewType(position) == ITEM_VIEW_TYPE_HEADER) {
			convertView = mInflater.inflate(R.layout.adapter_hot_concept_itemtype, parent, false);
			TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			txt_time.setText(bean.getTime());
		} else {
			convertView = mInflater.inflate(R.layout.adapter_hot_concept, parent, false);
			TextView txt_count = (TextView) convertView.findViewById(R.id.txt_count);
			TextView txt_rate = (TextView) convertView.findViewById(R.id.txt_rate);
			TextView txt_follow = (TextView) convertView.findViewById(R.id.txt_follow);
			TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			TextView txt_subject = (TextView) convertView.findViewById(R.id.txt_subject);
			TextView txt_stock = (TextView) convertView.findViewById(R.id.txt_stock);
			if (bean != null) {
				txt_count.setText(bean.getSearchCount().replace("热搜指数:", ""));
				txt_rate.setText("");
				txt_name.setText(bean.getName());
				txt_time.setText(bean.getTime());
				txt_subject.setText(bean.getSubject());
				txt_stock.setText(Html.fromHtml("相关股：<font color='#1a83ff'>"+bean.getStocklist().get(0).getStockName()+"</font> <font color='#f24957'>"+bean.getStocklist().get(0).getRate()+"</font>"));
			}
		}
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handmark.pulltorefresh.library.PinnedSectionListView.
	 * PinnedSectionListAdapter#isItemViewTypePinned(int)
	 */
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return viewType == ITEM_VIEW_TYPE_HEADER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return ((HotConceptBean) getItem(position)).getViewType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
