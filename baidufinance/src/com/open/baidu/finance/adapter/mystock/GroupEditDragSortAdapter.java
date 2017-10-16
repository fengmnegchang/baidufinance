/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-16下午3:58:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.mystock;

import java.util.List;

import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.android.weak.WeakReferenceHandler;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.NewGroupNameFragmentActivity;
import com.open.baidu.finance.bean.mystock.GroupBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-16下午3:58:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class GroupEditDragSortAdapter extends CommonAdapter<GroupBean> {
	private WeakReferenceHandler weakReferenceHandler;
	public GroupEditDragSortAdapter(Context mContext,WeakReferenceHandler weakReferenceHandler, List<GroupBean> list) {
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
		final GroupBean bean = (GroupBean) getItem(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_group_edit_drag_sort, parent, false);
			viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
			viewHolder.txt_stockname = (TextView) convertView.findViewById(R.id.txt_stockname);
			
			viewHolder.drag_alarm = (ImageView) convertView.findViewById(R.id.drag_alarm);
			viewHolder.group_top = (ImageView) convertView.findViewById(R.id.group_top);
			viewHolder.drag_handle = (ImageView) convertView.findViewById(R.id.drag_handle);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (bean != null) {
			viewHolder.txt_stockname.setText(bean.getGroup_name());
			viewHolder.group_top.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = weakReferenceHandler.obtainMessage(1000,position,0);
					weakReferenceHandler.sendMessage(msg);
				}
			});
			
			viewHolder.drag_alarm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//重命名
					NewGroupNameFragmentActivity.startNewGroupNameFragmentActivity(mContext, null, bean.getGroup_name());
				}
			});
			
			viewHolder.checkbox.setChecked(bean.isCheck());
			viewHolder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					bean.setCheck(isChecked);
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_stockname;
		ImageView drag_alarm,group_top,drag_handle;
		CheckBox checkbox;

	}
}
