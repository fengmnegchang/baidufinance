/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午11:07:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.kline;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.kline.ReportDataBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午11:07:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockReportDataAdapter extends CommonAdapter<ReportDataBean> {

	public StockReportDataAdapter(Context mContext, List<ReportDataBean> list) {
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_stock_report_data, parent, false);
			viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			viewHolder.txt_rank = (TextView) convertView.findViewById(R.id.txt_rank);
			viewHolder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final ReportDataBean bean = (ReportDataBean) getItem(position);
		if (bean != null) {
			viewHolder.txt_name.setText(bean.getInstitutionName());
			switch (bean.getRank()) {
			case 0:
				viewHolder.txt_rank.setText("中性");
				viewHolder.txt_rank.setTextColor(mContext.getResources().getColor(R.color.pink_color));
				break;
			case 1:
				viewHolder.txt_rank.setText("增持");
				viewHolder.txt_rank.setTextColor(mContext.getResources().getColor(R.color.yellow2_color));
				break;
			case 2:
				viewHolder.txt_rank.setText("买入");
				viewHolder.txt_rank.setTextColor(mContext.getResources().getColor(R.color.red_color));
				break;
			default:
				break;
			}
			
			viewHolder.txt_date.setText(bean.getDate());
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_name, txt_rank, txt_date;
	}

}
