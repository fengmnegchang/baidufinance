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
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.market.FundBean;

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
public class FundExpandListAdapter extends CommonAdapter<FundBean> {

	public FundExpandListAdapter(Context mContext, List<FundBean> list) {
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
		final FundBean bean = (FundBean) getItem(position);
		ViewHodler mViewHodler;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.adapter_fund_expand_list, parent, false);
			mViewHodler = new ViewHodler();
			mViewHodler.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			mViewHodler.txt_code = (TextView) convertView.findViewById(R.id.txt_code);
			mViewHodler.txt_dwjz = (TextView) convertView.findViewById(R.id.txt_dwjz);
			mViewHodler.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
			mViewHodler.txt_netChangeRatio = (TextView) convertView.findViewById(R.id.txt_netChangeRatio);
			convertView.setTag(mViewHodler);
		}else{
			mViewHodler = (ViewHodler) convertView.getTag();
		}

		mViewHodler.txt_name.setText(bean.getName());
		mViewHodler.txt_code.setText(bean.getSymbol());
		mViewHodler.txt_dwjz.setText(String.format("%.3f", bean.getDwjz()));
		mViewHodler.txt_date.setText(bean.getDate());
		if (bean.getJzzz() != null && bean.getJzzz() > 0) {
			mViewHodler.txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.red_color));
			mViewHodler.txt_netChangeRatio.setText(String.format("%.2f", bean.getJzzz()) + "%");
		} else if (bean.getJzzz() != null && bean.getJzzz() < 0) {
			mViewHodler.txt_netChangeRatio.setText(String.format("%.2f", bean.getJzzz()) + "%");
			mViewHodler.txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.green_color));
		} else {
			mViewHodler.txt_netChangeRatio.setText("0.000%");
			mViewHodler.txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.black_color));
		}
		
		return convertView;
	}
	
	class ViewHodler{
		TextView txt_name,txt_code,txt_dwjz,txt_date,txt_netChangeRatio;
	}

}
