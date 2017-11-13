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
public class PlateStockPinnedSectionListAdapter extends CommonAdapter<PlateStockBean> implements PinnedSectionListAdapter{
	public static final int ITEM_VIEW_TYPE_HEADER = 1;
	private WeakReferenceHandler weakReferenceHandler;
	
	public PlateStockPinnedSectionListAdapter(Context mContext, WeakReferenceHandler weakReferenceHandler,List<PlateStockBean> list) {
		super(mContext, list);
		this.weakReferenceHandler = weakReferenceHandler;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final PlateStockBean bean = (PlateStockBean) getItem(position);
		if(getItemViewType(position)==ITEM_VIEW_TYPE_HEADER){
			if(convertView==null){
				convertView = mInflater.inflate(R.layout.adapter_plate_stock_itemtype, parent, false);
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
			convertView.setOnClickListener(null);
		}else{
			convertView = mInflater.inflate(R.layout.adapter_plate_stock, parent, false);
			TextView txt_stock_name = (TextView) convertView.findViewById(R.id.txt_stock_name); 
			TextView txt_stock_code = (TextView) convertView.findViewById(R.id.txt_stock_code); 
			TextView txt_close = (TextView) convertView.findViewById(R.id.txt_close); 
			TextView txt_netChangeRatio = (TextView) convertView.findViewById(R.id.txt_netChangeRatio); 
			
			txt_stock_name.setText(bean.getName());
			txt_stock_code.setText(bean.getCode());
			txt_close.setText(String.format("%.2f", bean.getTrade()));
			
			if(bean.getChangepercent()!=null && bean.getChangepercent()>0){
				txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.red_color));
			}else if(bean.getChangepercent()!=null &&bean.getChangepercent()<0){
				txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.green_color));
			}else{
				txt_netChangeRatio.setTextColor(mContext.getResources().getColor(R.color.black_color));
			}
			txt_netChangeRatio.setText((bean.getChangepercent()==null?"--":String.format("%.2f", bean.getChangepercent())+"%"));
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					StockScrollMarketFragmentActivity.startStockScrollMarketFragmentActivity(mContext, bean.getSymbol(), bean.getName(), bean.getCode());
				}
			});
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
		return ((PlateStockBean)getItem(position)).getViewType();
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
