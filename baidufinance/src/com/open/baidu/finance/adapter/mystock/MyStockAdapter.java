/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午1:52:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.mystock;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PinnedSectionListView.PinnedSectionListAdapter;
import com.open.android.adapter.CommonAdapter;
import com.open.android.weak.WeakReferenceHandler;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.mystock.StockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午1:52:22
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MyStockAdapter extends CommonAdapter<StockBean> implements PinnedSectionListAdapter{
    public static final int ITEM_VIEW_TYPE_HEADER = 1;
    private WeakReferenceHandler weakReferenceHandler;
	public MyStockAdapter(Context mContext, WeakReferenceHandler weakReferenceHandler,List<StockBean> list) {
		super(mContext, list);
		this.weakReferenceHandler = weakReferenceHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("ResourceAsColor") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final StockBean bean = (StockBean) getItem(position);
		if(getItemViewType(position)==ITEM_VIEW_TYPE_HEADER){
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.adapter_my_stock_itemtype, parent, false);
				TextView txt_all = (TextView) convertView.findViewById(R.id.txt_all);
				TextView txt_close = (TextView) convertView.findViewById(R.id.txt_close);
				TextView txt_netChange = (TextView) convertView.findViewById(R.id.txt_netChange);
				
				txt_close.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(bean.getClosetype()==0){
							bean.setClosetype(-1);
						}else if(bean.getClosetype()==-1){
							bean.setClosetype(1);
						}else{
							bean.setClosetype(0);
						}
						Message msg = weakReferenceHandler.obtainMessage();
						msg.what = 1;
						msg.arg1 = bean.getClosetype();
						weakReferenceHandler.sendMessage(msg);
					}
				});
				
				txt_netChange.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(bean.getNetRatioType()==0){
							bean.setNetRatioType(-1);
						}else if(bean.getNetRatioType()==-1){
							bean.setNetRatioType(1);
						}else{
							bean.setNetRatioType(0);
						}
						Message msg = weakReferenceHandler.obtainMessage();
						msg.what = 2;
						msg.arg1 = bean.getNetRatioType();
						weakReferenceHandler.sendMessage(msg);
					}
				});
				
				txt_all.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(bean.getAlltype()==3){
							bean.setAlltype(0);
						}else{
							bean.setAlltype(bean.getAlltype()+1);
						} 
						Message msg = weakReferenceHandler.obtainMessage();
						msg.what = 0;
						msg.arg1 = bean.getAlltype();
						weakReferenceHandler.sendMessage(msg);
					}
				});
			}
		}else{
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.adapter_my_stock, parent, false);
			}
			TextView txt_exchange = (TextView) convertView.findViewById(R.id.txt_exchange); 
			TextView txt_stock_name = (TextView) convertView.findViewById(R.id.txt_stock_name); 
			TextView txt_stock_code = (TextView) convertView.findViewById(R.id.txt_stock_code); 
			TextView txt_close = (TextView) convertView.findViewById(R.id.txt_close); 
			TextView txt_netChangeRatio = (TextView) convertView.findViewById(R.id.txt_netChangeRatio); 
			
			txt_exchange.setText(bean.getExchange());
			txt_exchange.setBackgroundColor(mContext.getResources().getColor(R.color.white_color));
			if("sz".equalsIgnoreCase(bean.getExchange()) || "sh".equalsIgnoreCase(bean.getExchange())){
				txt_exchange.setVisibility(View.INVISIBLE);
			}else{
				if("hk".equalsIgnoreCase(bean.getExchange())){
					txt_exchange.setBackgroundColor(mContext.getResources().getColor(R.color.hk_color));
				}else if("us".equalsIgnoreCase(bean.getExchange())){
					txt_exchange.setBackgroundColor(mContext.getResources().getColor(R.color.us_color));
				}
				txt_exchange.setVisibility(View.VISIBLE);
			}
			
			txt_stock_name.setText(bean.getStockName());
			txt_stock_code.setText(bean.getStockCode());
			txt_close.setText(String.format("%.2f", bean.getClose()));
			if(bean.getNetChangeRatio()>0){
				txt_netChangeRatio.setBackgroundResource(R.drawable.selector_red_stock_shape);
			}else if(bean.getNetChangeRatio()<0){
				txt_netChangeRatio.setBackgroundResource(R.drawable.selector_green_stock_shape);
			}else{
				txt_netChangeRatio.setBackgroundResource(R.drawable.selector_gray_stock_shape);
			}
			txt_netChangeRatio.setText(String.format("%.2f", bean.getNetChangeRatio())+"%");
		}
		return convertView;
	}

 

	/* (non-Javadoc)
	 * @see com.handmark.pulltorefresh.library.PinnedSectionListView.PinnedSectionListAdapter#isItemViewTypePinned(int)
	 */
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return viewType == ITEM_VIEW_TYPE_HEADER;
	}
	
	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return ((StockBean)getItem(position)).getViewType();
	}
	
	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
