/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2下午5:35:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.market;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.market.IndexBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2下午5:35:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class IndexAdapter extends CommonAdapter<IndexBean> {

	public IndexAdapter(Context mContext, List<IndexBean> list) {
		super(mContext, list);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final IndexBean bean = (IndexBean) getItem(position);
		ViewHodler mViewHodler;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.adapter_index_grid, parent, false);
			mViewHodler = new ViewHodler();
			mViewHodler.txt_plate_name = (TextView) convertView.findViewById(R.id.txt_plate_name);
			mViewHodler.txt_plate_rate = (TextView) convertView.findViewById(R.id.txt_plate_rate);
			mViewHodler.txt_stock_close = (TextView) convertView.findViewById(R.id.txt_stock_close);
			convertView.setTag(mViewHodler);
		}else{
			mViewHodler = (ViewHodler) convertView.getTag();
		}
		
		mViewHodler.txt_plate_name.setText(bean.getStockName());
		if (bean.getNetChnageRate() > 0) {
			mViewHodler.txt_plate_rate.setTextColor(mContext.getResources().getColor(R.color.red_color));
		} else if (bean.getNetChnageRate() < 0) {
			mViewHodler.txt_plate_rate.setTextColor(mContext.getResources().getColor(R.color.green_color));
		} else {
			mViewHodler.txt_plate_rate.setTextColor(mContext.getResources().getColor(R.color.black_color));
		}
		mViewHodler.txt_plate_rate.setText(String.format("%.2f", bean.getClose()) + "");
//		if (bean.getStockNetChnageRate() > 0) {
//			txt_stock_close.setTextColor(mContext.getResources().getColor(R.color.red_color));
//		} else if (bean.getStockNetChnageRate() < 0) {
//			txt_stock_close.setTextColor(mContext.getResources().getColor(R.color.green_color));
//		} else {
//			txt_stock_close.setTextColor(mContext.getResources().getColor(R.color.black_color));
//		}
		mViewHodler.txt_stock_close.setText(String.format("%.2f", bean.getNetChnage()) +" "+String.format("%.2f", bean.getNetChnageRate()) + "%");
		return convertView;
	}
	
	class ViewHodler{
		TextView txt_plate_name;
		TextView txt_plate_rate;
		TextView txt_stock_close;
	}

}
