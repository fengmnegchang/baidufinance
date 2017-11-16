/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午2:31:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.mystock;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.android.weak.WeakReferenceHandler;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.kline.StockScrollMarketFragmentActivity;
import com.open.baidu.finance.bean.mystock.SearchStockData;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午2:31:24
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SearchStockAdapter extends CommonAdapter<SearchStockData> {
	private WeakReferenceHandler weakReferenceHandler;
	public SearchStockAdapter(Context mContext,WeakReferenceHandler weakReferenceHandler, List<SearchStockData> list) {
		super(mContext, list);
		// TODO Auto-generated constructor stub
		this.weakReferenceHandler = weakReferenceHandler;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_search_stock, parent, false);
			viewHolder.txt_stock_name = (TextView) convertView.findViewById(R.id.txt_stock_name);
			viewHolder.txt_stock_code = (TextView) convertView.findViewById(R.id.txt_stock_code);
			viewHolder.txt_clean = (TextView) convertView.findViewById(R.id.txt_clean);
			viewHolder.img_add = (ImageView) convertView.findViewById(R.id.img_add);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final SearchStockData bean = (SearchStockData) getItem(position);
		if(bean!=null){
			if(bean.isBottom()){
				viewHolder.txt_clean.setVisibility(View.VISIBLE);
				viewHolder.txt_stock_name.setVisibility(View.GONE);
				viewHolder.txt_stock_code.setVisibility(View.GONE);
				viewHolder.img_add.setVisibility(View.GONE);
			}else{
				viewHolder.txt_clean.setVisibility(View.GONE);
				viewHolder.txt_stock_name.setVisibility(View.VISIBLE);
				viewHolder.txt_stock_code.setVisibility(View.VISIBLE);
				viewHolder.img_add.setVisibility(View.VISIBLE);
			}
			viewHolder.txt_stock_name.setText(bean.getF_symbolName());
			if(bean.getGroupId()!=null){
				viewHolder.txt_stock_code.setText(bean.getF_symbol()+" 已在自选股分组中");
			}else{
				viewHolder.txt_stock_code.setText(bean.getF_symbol());
			}
			viewHolder.img_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					weakReferenceHandler.sendMessage(weakReferenceHandler.obtainMessage(10000, position,0, bean));
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					StockScrollMarketFragmentActivity.startStockScrollMarketFragmentActivity(mContext,bean.getF_exchange()+ bean.getF_symbol(), bean.getF_symbolName(), "");
				}
			});
		}
		return convertView;
	}
	class ViewHolder {
		TextView txt_stock_name,txt_stock_code,txt_clean;
		ImageView img_add;
	}
}
