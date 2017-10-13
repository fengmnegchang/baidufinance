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
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (bean != null) {

		}
		return convertView;
	}

	class ViewHolder {

	}

}
