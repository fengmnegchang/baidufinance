/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-13上午10:56:40
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
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.kline.TimeLineBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-13上午10:56:40
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class StockSallBuyAddapter extends CommonAdapter<TimeLineBean> {

	public StockSallBuyAddapter(Context mContext, List<TimeLineBean> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_stock_sall_buy, parent, false);
			viewHolder.txt_sall_time = (TextView) convertView.findViewById(R.id.txt_sall_time);
			viewHolder.txt_close = (TextView) convertView.findViewById(R.id.txt_close);
			viewHolder.txt_volume = (TextView) convertView.findViewById(R.id.txt_volume);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final TimeLineBean bean = (TimeLineBean) getItem(position);
		if (bean != null) {
			if(bean.getType()==0){
				viewHolder.txt_sall_time.setText(bean.getBsLevel());
				viewHolder.txt_close.setText(String.format("%.2f", bean.getPrice()));
				if(bean.getVolume()<1000000){
					viewHolder.txt_volume.setText(String.format("%.2f",bean.getVolume()/100f)+"");
				}else if(bean.getVolume()>=1000000){
					viewHolder.txt_volume.setText(String.format("%.2f",bean.getVolume()/1000000f)+"万");
				}
				viewHolder.txt_close.setTextColor(mContext.getResources().getColor(R.color.red_color));
				viewHolder.txt_volume.setTextColor(mContext.getResources().getColor(R.color.gray_53_color));
			}else{
				viewHolder.txt_sall_time.setText(bean.getTime()/100000+"");
				viewHolder.txt_close.setText(String.format("%.2f", bean.getPrice()));
				if(bean.getVolume()<1000000){
					viewHolder.txt_volume.setText(String.format("%.2f",bean.getVolume()/100f)+" "+bean.getBsflag());
				}else if(bean.getVolume()>=1000000){
					viewHolder.txt_volume.setText(String.format("%.2f",bean.getVolume()/1000000f)+"万 " +bean.getBsflag());
				}
				viewHolder.txt_close.setTextColor(mContext.getResources().getColor(R.color.red_color));
				if("S".equals(bean.getBsflag())){
					viewHolder.txt_volume.setTextColor(mContext.getResources().getColor(R.color.green_color));
				}else{
					viewHolder.txt_volume.setTextColor(mContext.getResources().getColor(R.color.red_color));
				}
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_sall_time, txt_close, txt_volume;
	}


}
