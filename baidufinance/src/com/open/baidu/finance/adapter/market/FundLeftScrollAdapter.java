/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2上午10:35:21
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
 * @createTime:2017-11-2上午10:35:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class FundLeftScrollAdapter extends CommonAdapter<FundBean> {

	public FundLeftScrollAdapter(Context mContext, List<FundBean> list) {
		super(mContext, list);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodler mViewHodler;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.adapter_fund_left_scroll_item, parent, false);
			mViewHodler = new ViewHodler();
			mViewHodler.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			mViewHodler.txt_code = (TextView) convertView.findViewById(R.id.txt_code);
			convertView.setTag(mViewHodler);
		}else{
			mViewHodler = (ViewHodler) convertView.getTag();
		}
		FundBean bean = (FundBean) getItem(position);
		if(bean!=null){
			if(bean.getName().length()>6){
				mViewHodler.txt_name.setText(bean.getName().substring(0,6));
			}else{
				mViewHodler.txt_name.setText(bean.getName());
			}
			mViewHodler.txt_code.setText(bean.getSymbol());
		}
		return convertView;
	}
	
	class ViewHodler{
		TextView txt_name,txt_code;
	}

}
