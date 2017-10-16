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
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.open.android.adapter.CommonAdapter;
import com.open.android.weak.WeakReferenceHandler;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.StockAlarmSettingFragmentActivity;
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
	private WeakReferenceHandler weakReferenceHandler;
	public StockEditDragSortAdapter(Context mContext,WeakReferenceHandler weakReferenceHandler, List<StockBean> list) {
		super(mContext, list);
		this.weakReferenceHandler = weakReferenceHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final StockBean bean = (StockBean) getItem(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_stock_edit_drag_sort, parent, false);
			viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
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
			viewHolder.group_top.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = weakReferenceHandler.obtainMessage(1000,position,0);
					weakReferenceHandler.sendMessage(msg);
				}
			});
			
			viewHolder.drag_alarm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StockAlarmSettingFragmentActivity.startStockAlarmSettingFragmentActivity(mContext,bean, null);
				}
			});
			
			viewHolder.checkbox.setChecked(bean.isCheck());
			viewHolder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					bean.setCheck(isChecked);
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_exchange,txt_stockname,txt_stockcode;
		ImageView drag_alarm,group_top,drag_handle;
		CheckBox checkbox;

	}

}
