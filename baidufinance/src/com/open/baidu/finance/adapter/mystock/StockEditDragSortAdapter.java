/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午5:16:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.mystock;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.mystock.StockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午5:16:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockEditDragSortAdapter extends CommonAdapter<StockBean> {

	public StockEditDragSortAdapter(Context mContext, List<StockBean> list) {
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
		StockBean bean = (StockBean) getItem(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_stock_edit_drag_sort, parent, false);
			viewHolder.txt_exchange = (TextView) convertView.findViewById(R.id.txt_exchange);
			viewHolder.txt_stockname = (TextView) convertView.findViewById(R.id.txt_stockname);
			viewHolder.txt_stockcode = (TextView) convertView.findViewById(R.id.txt_stockcode);
			
			viewHolder.drag_alarm = (ImageView) convertView.findViewById(R.id.drag_alarm);
			viewHolder.group_top = (ImageView) convertView.findViewById(R.id.group_top);
			viewHolder.drag_handle = (ImageView) convertView.findViewById(R.id.drag_handle);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (bean != null) {
			viewHolder.txt_exchange.setText(bean.getExchange());
			viewHolder.txt_exchange.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
			if("sz".equalsIgnoreCase(bean.getExchange()) || "sh".equalsIgnoreCase(bean.getExchange())){
				viewHolder.txt_exchange.setVisibility(View.INVISIBLE);
			}else{
				if("hk".equalsIgnoreCase(bean.getExchange())){
					viewHolder.txt_exchange.setBackgroundColor(mContext.getResources().getColor(R.color.hk_color));
				}else if("us".equalsIgnoreCase(bean.getExchange())){
					viewHolder.txt_exchange.setBackgroundColor(mContext.getResources().getColor(R.color.us_color));
				}
				viewHolder.txt_exchange.setVisibility(View.VISIBLE);
			}
			
			viewHolder.txt_stockname.setText(bean.getStockName());
			viewHolder.txt_stockcode.setText(bean.getStockCode());
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_exchange,txt_stockname,txt_stockcode;
		ImageView drag_alarm,group_top,drag_handle;

	}

}
