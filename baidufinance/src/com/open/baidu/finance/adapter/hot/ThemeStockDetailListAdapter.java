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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.kline.StockScrollMarketFragmentActivity;
import com.open.baidu.finance.bean.hot.HotStockBean;

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
public class ThemeStockDetailListAdapter extends CommonAdapter<HotStockBean> {

	public ThemeStockDetailListAdapter(Context mContext, List<HotStockBean> list) {
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
		final HotStockBean bean = (HotStockBean) getItem(position);
		convertView = mInflater.inflate(R.layout.adapter_theme_stock_detail, parent, false);
		TextView txt_stock_name = (TextView) convertView.findViewById(R.id.txt_stock_name);
		TextView txt_stock_code = (TextView) convertView.findViewById(R.id.txt_stock_code);
		TextView txt_close = (TextView) convertView.findViewById(R.id.txt_close);
		TextView txt_rate = (TextView) convertView.findViewById(R.id.txt_rate);
		if (bean != null) {
			txt_stock_name.setText(bean.getStockName());
			txt_stock_code.setText(bean.getStockCode());
			txt_close.setText(bean.getClose());
			txt_rate.setText(bean.getRate());
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StockScrollMarketFragmentActivity.startStockScrollMarketFragmentActivity(mContext, bean.getStockCode(), bean.getStockName(), bean.getStockCode());
				}
			});
		}
		return convertView;
	}

}
