/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:30:31
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
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.kline.StockScrollMarketFragmentActivity;
import com.open.baidu.finance.bean.market.PlateStockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:30:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class PlateStockExpandListAdapter extends CommonAdapter<PlateStockBean> {

	public PlateStockExpandListAdapter(Context mContext, List<PlateStockBean> list) {
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
		final PlateStockBean bean = (PlateStockBean) getItem(position);
		ViewHodler mViewHodler;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.adapter_plate_stock, parent, false);
			mViewHodler = new ViewHodler();
			mViewHodler.txt_stock_name = (TextView) convertView.findViewById(R.id.txt_stock_name);
			mViewHodler.txt_stock_code = (TextView) convertView.findViewById(R.id.txt_stock_code);
			mViewHodler.txt_close = (TextView) convertView.findViewById(R.id.txt_close);
			mViewHodler.txt_netChangeRatio = (TextView) convertView.findViewById(R.id.txt_netChangeRatio);
			convertView.setTag(mViewHodler);
		}else{
			mViewHodler = (ViewHodler) convertView.getTag();
		}

		mViewHodler.txt_stock_name.setText(bean.getName());
		mViewHodler.txt_stock_code.setText(bean.getCode());
		mViewHodler.txt_close.setText(String.format("%.2f", bean.getTrade()));

		if (bean.getChangepercent()!=null && bean.getChangepercent() > 0) {
			mViewHodler.txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.red_color));
		} else if (bean.getChangepercent()!=null && bean.getChangepercent() < 0) {
			mViewHodler.txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.green_color));
		} else {
			mViewHodler.txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.black_color));
		}
		mViewHodler.txt_netChangeRatio.setText((bean.getChangepercent()==null ? "--":String.format("%.2f", bean.getChangepercent()) + "%"));
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String stockExCode  = bean.getSymbol();
				StockScrollMarketFragmentActivity.startStockScrollMarketFragmentActivity(mContext, stockExCode, bean.getName(), bean.getCode());
			}
		});
		return convertView;
	}
	
	class ViewHodler{
		TextView txt_stock_name,txt_stock_code,txt_close,txt_netChangeRatio;
	}

}
