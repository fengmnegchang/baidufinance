/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:27:20
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.hot;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.hot.HotConceptBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:27:20
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotConceptAdapter extends CommonAdapter<HotConceptBean> {

	public HotConceptAdapter(Context mContext, List<HotConceptBean> list) {
		super(mContext, list);
		// TODO Auto-generated constructor stub
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_hot_concept, parent, false);
			viewHolder.txt_count = (TextView) convertView.findViewById(R.id.txt_count);
			viewHolder.txt_rate = (TextView) convertView.findViewById(R.id.txt_rate);
			viewHolder.txt_follow = (TextView) convertView.findViewById(R.id.txt_follow);
			viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			viewHolder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			viewHolder.txt_subject = (TextView) convertView.findViewById(R.id.txt_subject);
			viewHolder.txt_stock = (TextView) convertView.findViewById(R.id.txt_stock);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HotConceptBean bean = (HotConceptBean) getItem(position);
		if (bean != null) {
			viewHolder.txt_count.setText(bean.getSearchCount().replace("热搜指数: ", ""));
			viewHolder.txt_rate.setText("");
			viewHolder.txt_name.setText(bean.getName());
			viewHolder.txt_time.setText(bean.getTime());
			viewHolder.txt_subject.setText(bean.getSubject());
			viewHolder.txt_stock.setText(Html.fromHtml("相关股：<font color='#1a83ff'>"+bean.getStocklist().get(0).getStockName()+"</font> <font color='#f24957'>"+bean.getStocklist().get(0).getRate()+"</font>"));
		}
		viewHolder.txt_follow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView txt_count, txt_rate, txt_follow,txt_name,txt_time,txt_subject,txt_stock;
	}


}
