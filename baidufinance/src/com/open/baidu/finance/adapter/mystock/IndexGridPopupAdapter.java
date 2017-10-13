/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13上午11:19:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.mystock;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.mystock.StockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13上午11:19:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class IndexGridPopupAdapter extends CommonAdapter<StockBean> {

	public IndexGridPopupAdapter(Context mContext, List<StockBean> list) {
		super(mContext, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("ResourceAsColor") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		StockBean bean = (StockBean) getItem(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_index_grid_popup, parent, false);
			viewHolder.txt_index_name = (TextView) convertView.findViewById(R.id.txt_index_name);
			viewHolder.txt_index_value = (TextView) convertView.findViewById(R.id.txt_index_value);
			viewHolder.txt_index_rate = (TextView) convertView.findViewById(R.id.txt_index_rate);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if(bean!=null){
			viewHolder.txt_index_name.setText(bean.getStockName());
			viewHolder.txt_index_value.setText(String.format("%.2f", bean.getClose()));
			viewHolder.txt_index_rate.setText(String.format("%.2f", bean.getNetChange())+" "+String.format("%.2f", bean.getNetChangeRatio()) +"%");
			if(bean.getNetChangeRatio()>0){
				viewHolder.txt_index_rate.setTextColor(mContext.getResources().getColor(R.color.red_color));
				viewHolder.txt_index_value.setTextColor(mContext.getResources().getColor(R.color.red_color));
			}else if(bean.getNetChangeRatio()<0){
				viewHolder.txt_index_rate.setTextColor(mContext.getResources().getColor(R.color.green_color));
				viewHolder.txt_index_value.setTextColor(mContext.getResources().getColor(R.color.green_color));
			}else{
				viewHolder.txt_index_rate.setTextColor(mContext.getResources().getColor(R.color.gray_color));
				viewHolder.txt_index_value.setTextColor(mContext.getResources().getColor(R.color.gray_color));
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_index_name, txt_index_value, txt_index_rate;
	}

}
