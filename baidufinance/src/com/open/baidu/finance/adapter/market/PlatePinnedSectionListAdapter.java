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
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PinnedSectionListView.PinnedSectionListAdapter;
import com.open.android.adapter.CommonAdapter;
import com.open.android.weak.WeakReferenceHandler;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.market.PlateBean;

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
public class PlatePinnedSectionListAdapter extends CommonAdapter<PlateBean> implements PinnedSectionListAdapter{
	public static final int ITEM_VIEW_TYPE_HEADER = 1;
	private WeakReferenceHandler weakReferenceHandler;
	
	public PlatePinnedSectionListAdapter(Context mContext, WeakReferenceHandler weakReferenceHandler,List<PlateBean> list) {
		super(mContext, list);
		this.weakReferenceHandler = weakReferenceHandler;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final PlateBean bean = (PlateBean) getItem(position);
		if(getItemViewType(position)==ITEM_VIEW_TYPE_HEADER){
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.adapter_plate_itemtype, parent, false);
			}
			final TextView txt_netChange = (TextView) convertView.findViewById(R.id.txt_netChange);
			txt_netChange.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(bean.getNetRatioType()==0){
						bean.setNetRatioType(-1);
						txt_netChange.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.sort_down), null);
					}else if(bean.getNetRatioType()==-1){
						bean.setNetRatioType(1);
						txt_netChange.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.sort_up), null);
					}else{
						bean.setNetRatioType(0);
						txt_netChange.setCompoundDrawablesWithIntrinsicBounds(null, null, mContext.getResources().getDrawable(R.drawable.portfolio_market), null);
					}
					Message msg = weakReferenceHandler.obtainMessage();
					//涨跌幅
					msg.what = 2;
					msg.arg1 = bean.getNetRatioType();
					weakReferenceHandler.sendMessage(msg);
				}
			});
		}else{
			convertView = mInflater.inflate(R.layout.adapter_plate, parent, false);
			TextView txt_all = (TextView) convertView.findViewById(R.id.txt_all); 
			TextView txt_netChange = (TextView) convertView.findViewById(R.id.txt_netChange); 
			TextView txt_topstock = (TextView) convertView.findViewById(R.id.txt_topstock); 
			txt_all.setText(bean.getPlateName());
			txt_topstock.setText(bean.getStockName());
			if(bean.getNetChangeRate()>0){
				txt_netChange.setTextColor(mContext.getResources().getColor(R.color.red_color));
			}else if(bean.getNetChangeRate()<0){
				txt_netChange.setTextColor(mContext.getResources().getColor(R.color.green_color));
			}else{
				txt_netChange.setTextColor(mContext.getResources().getColor(R.color.black_color));
			}
			txt_netChange.setText(String.format("%.2f", bean.getNetChangeRate())+"%");
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
		return ((PlateBean)getItem(position)).getViewType();
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
