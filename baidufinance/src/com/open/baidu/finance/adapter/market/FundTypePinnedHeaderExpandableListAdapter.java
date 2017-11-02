/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1上午10:26:50
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.market;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonExpandableListAdapter;
import com.open.android.view.ExpendListView;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.market.FundBean;
import com.open.baidu.finance.bean.market.FundTypeBean;
import com.open.baidu.finance.json.market.FundTypeJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1上午10:26:50
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class FundTypePinnedHeaderExpandableListAdapter extends CommonExpandableListAdapter<FundTypeBean, FundTypeJson> {

	public FundTypePinnedHeaderExpandableListAdapter(Context mContext, List<FundTypeBean> list) {
		super(mContext, list);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean arg1, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupViewHolder mGroupViewHolder;
		final FundTypeBean bean = (FundTypeBean) getGroup(groupPosition);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_market_shsz_itemtype, null);
			mGroupViewHolder = new GroupViewHolder();
			mGroupViewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			mGroupViewHolder.txt_all = (TextView) convertView.findViewById(R.id.txt_all);
			convertView.setTag(mGroupViewHolder);
		} else {
			mGroupViewHolder = (GroupViewHolder) convertView.getTag();
		}
		if (bean != null) {
			mGroupViewHolder.txt_name.setText("  "+bean.getGroupName());
			mGroupViewHolder.txt_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
		}
		return convertView;
	}
	
	public class GroupViewHolder {
		TextView txt_name,txt_all;
	}
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.tencenttv.adapter.CommonExpandableListAdapter#getChildrenCount
	 * (int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (getGroup(groupPosition) != null
				&& ((getGroup(groupPosition).getList() != null && getGroup(groupPosition).getList().size() > 0))) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildViewHolder mChildViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_fund_type_childview, null);
			mChildViewHolder = new ChildViewHolder();
			mChildViewHolder.listview = (ExpendListView) convertView.findViewById(R.id.expend_listview);
			convertView.setTag(mChildViewHolder);
		} else {
			mChildViewHolder = (ChildViewHolder) convertView.getTag();
		}
		mChildViewHolder.list = getGroup(groupPosition).getList();
		mChildViewHolder.mFundExpandListAdapter = new FundExpandListAdapter(mContext, mChildViewHolder.list);
		mChildViewHolder.listview.setAdapter(mChildViewHolder.mFundExpandListAdapter);
		mChildViewHolder.mFundExpandListAdapter.notifyDataSetChanged();
		
		return convertView;
	}
	
	public class ChildViewHolder {
		ExpendListView listview;
		FundExpandListAdapter mFundExpandListAdapter;
		List<FundBean>  list = new ArrayList<FundBean>();
	}

}
