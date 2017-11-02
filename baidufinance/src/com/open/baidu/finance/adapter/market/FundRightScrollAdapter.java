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
public class FundRightScrollAdapter extends CommonAdapter<FundBean> {

	public FundRightScrollAdapter(Context mContext, List<FundBean> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_fund_right_scroll_item, parent, false);
			mViewHodler = new ViewHodler();
			mViewHodler.txt_dwjz = (TextView) convertView.findViewById(R.id.txt_dwjz);
			mViewHodler.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
			mViewHodler.txt_ljdwjz = (TextView) convertView.findViewById(R.id.txt_ljdwjz);
			mViewHodler.txt_zrjz = (TextView) convertView.findViewById(R.id.txt_zrjz);
			mViewHodler.txt_jzzz = (TextView) convertView.findViewById(R.id.txt_jzzz);
			mViewHodler.txt_jjgm = (TextView) convertView.findViewById(R.id.txt_jjgm);
			convertView.setTag(mViewHodler);
		}else{
			mViewHodler = (ViewHodler) convertView.getTag();
		}
		FundBean bean = (FundBean) getItem(position);
		if(bean!=null){
			mViewHodler.txt_dwjz.setText(bean.getDwjz()+"");
			mViewHodler.txt_ljdwjz.setText(bean.getLjdwjz()+"");
			mViewHodler.txt_zrjz.setText(bean.getZrjz()+"");
			mViewHodler.txt_jzzz.setText(String.format("%.3f", bean.getJzzz())+"");
			mViewHodler.txt_jjgm.setText(String.format("%.3f", bean.getJjgm())+"");
			mViewHodler.txt_date.setText(bean.getDate());
		}
		return convertView;
	}
	
	class ViewHodler{
		TextView txt_dwjz,txt_ljdwjz,txt_zrjz,txt_jzzz,txt_jjgm,txt_date;
	}

}
