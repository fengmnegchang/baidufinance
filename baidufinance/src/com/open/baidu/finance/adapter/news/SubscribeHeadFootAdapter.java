/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:18:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.news;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.android.weak.WeakReferenceHandler;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.news.SubscribeBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:18:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SubscribeHeadFootAdapter extends CommonAdapter<SubscribeBean> {
	private WeakReferenceHandler weakReferenceHandler;
	
	public SubscribeHeadFootAdapter(Context mContext, WeakReferenceHandler weakReferenceHandler, List<SubscribeBean> list) {
		super(mContext, list);
		this.weakReferenceHandler = weakReferenceHandler;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final SubscribeBean bean = (SubscribeBean) getItem(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_subscribe_head_foot_drag_sort, parent, false);
			viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			viewHolder.txt_stockname = (TextView) convertView.findViewById(R.id.txt_stockname);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (bean != null) {
			viewHolder.txt_stockname.setText(bean.getKeyName());
			if(bean.getCheckType()==-1){
				viewHolder.checkbox.setEnabled(false);
			}else if(bean.getCheckType()==0){
				viewHolder.checkbox.setEnabled(true);
				viewHolder.checkbox.setChecked(false);
			}else if(bean.getCheckType()==0){
				viewHolder.checkbox.setEnabled(true);
				viewHolder.checkbox.setChecked(true);
			}
			viewHolder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					 if(isChecked){
						 weakReferenceHandler.sendMessage(weakReferenceHandler.obtainMessage(9000, position, 0, bean));
						 removeItem(position);
					 }
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_stockname;
		CheckBox checkbox;
	}

}
