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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PinnedSectionListView.PinnedSectionListAdapter;
import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.hot.ThemeStockDetailListFragmentActivity;
import com.open.baidu.finance.activity.kline.StockScrollMarketFragmentActivity;
import com.open.baidu.finance.bean.hot.ThemeStockBean;

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
public class ThemeStockPinnedSectionListAdapter extends CommonAdapter<ThemeStockBean> implements PinnedSectionListAdapter {
	public static final int ITEM_VIEW_TYPE_HEADER = 1;

	public ThemeStockPinnedSectionListAdapter(Context mContext, List<ThemeStockBean> list) {
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
		final ThemeStockBean bean = (ThemeStockBean) getItem(position);
		if (getItemViewType(position) == ITEM_VIEW_TYPE_HEADER) {
			convertView = mInflater.inflate(R.layout.adapter_theme_itemtype, parent, false);
			TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			TextView txt_subject = (TextView) convertView.findViewById(R.id.txt_subject);
			txt_name.setText(bean.getName());
			txt_time.setText(bean.getTime());
			txt_subject.setText(bean.getSubject()+bean.getEvent());
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ThemeStockDetailListFragmentActivity.startThemeStockDetailListFragmentActivity(mContext, bean.getHref(),bean.getEvent(),bean.getName());
				}
			});
		} else {
			convertView = mInflater.inflate(R.layout.adapter_theme_stock, parent, false);
			TextView txt_stock_name = (TextView) convertView.findViewById(R.id.txt_stock_name);
			TextView txt_stock_code = (TextView) convertView.findViewById(R.id.txt_stock_code);
			TextView txt_close = (TextView) convertView.findViewById(R.id.txt_close);
			TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			TextView txt_rate = (TextView) convertView.findViewById(R.id.txt_rate);
			if (bean != null) {
				txt_stock_name.setText(bean.getStockName());
				txt_stock_code.setText(bean.getStockCode());
				txt_close.setText(bean.getClose());
				txt_time.setText(bean.getTime());
				txt_rate.setText(bean.getRate());
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						StockScrollMarketFragmentActivity.startStockScrollMarketFragmentActivity(mContext, bean.getStockCode(), bean.getStockName(), bean.getStockCode());
					}
				});
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
		return ((ThemeStockBean) getItem(position)).getViewType();
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
