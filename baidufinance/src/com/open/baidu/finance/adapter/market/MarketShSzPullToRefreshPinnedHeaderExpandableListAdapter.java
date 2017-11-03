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
import com.open.android.view.ExpendGridView;
import com.open.android.view.ExpendListView;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.market.PlatePullToRefreshPinnedSectionListViewFragmentActivity;
import com.open.baidu.finance.activity.market.PlateStockPullToRefreshPinnedSectionListViewFragmentActivity;
import com.open.baidu.finance.bean.market.MarketShSzBean;
import com.open.baidu.finance.bean.market.PlateBean;
import com.open.baidu.finance.bean.market.PlateStockBean;
import com.open.baidu.finance.json.market.MarketShSzJson;
import com.open.baidu.finance.utils.UrlUtils;

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
public class MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter extends CommonExpandableListAdapter<MarketShSzBean, MarketShSzJson> {

	public MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter(Context mContext, List<MarketShSzBean> list) {
		super(mContext, list);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean arg1, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupViewHolder mGroupViewHolder;
		final MarketShSzBean bean = (MarketShSzBean) getGroup(groupPosition);
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
					 if(bean.getGroupType()<=4){
						 PlatePullToRefreshPinnedSectionListViewFragmentActivity.startPlatePullToRefreshPinnedSectionListViewFragmentActivity(mContext, bean.getUrl(),bean.getGroupName());
					 }else if(bean.getGroupType()<=10){
						 PlateStockPullToRefreshPinnedSectionListViewFragmentActivity.startPlateStockPullToRefreshPinnedSectionListViewFragmentActivity(mContext, UrlUtils.GETHQNODEDATA_NODE+bean.getUrl(),bean.getGroupName());
					 }else{
						 PlateStockPullToRefreshPinnedSectionListViewFragmentActivity.startPlateStockPullToRefreshPinnedSectionListViewFragmentActivity(mContext, bean.getUrl(),bean.getGroupName());
					 }
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
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public MarketShSzBean getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
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
				&& ((getGroup(groupPosition).getSlist() != null && getGroup(groupPosition).getSlist().size() > 0) || (getGroup(groupPosition).getPlist() != null && getGroup(groupPosition)
						.getPlist().size() > 0))) {
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
			convertView = mInflater.inflate(R.layout.adapter_market_shsz_childview, null);
			mChildViewHolder = new ChildViewHolder();
			mChildViewHolder.gridView = (ExpendGridView) convertView.findViewById(R.id.expend_gridview);
			mChildViewHolder.listview = (ExpendListView) convertView.findViewById(R.id.expend_listview);
			convertView.setTag(mChildViewHolder);
		} else {
			mChildViewHolder = (ChildViewHolder) convertView.getTag();
		}

		if(getGroup(groupPosition).getGroupType()<=4){
			mChildViewHolder.gridView.setVisibility(View.VISIBLE);
			mChildViewHolder.listview.setVisibility(View.GONE);
		}else{
			mChildViewHolder.listview.setVisibility(View.VISIBLE);
			mChildViewHolder.gridView.setVisibility(View.GONE);
		}
		mChildViewHolder.plsit = getGroup(groupPosition).getPlist();
		mChildViewHolder.mPlateGridExpandAdapter = new PlateGridExpandAdapter(mContext, mChildViewHolder.plsit);
		mChildViewHolder.gridView.setAdapter(mChildViewHolder.mPlateGridExpandAdapter);
		mChildViewHolder.mPlateGridExpandAdapter.notifyDataSetChanged();

		mChildViewHolder.slist = getGroup(groupPosition).getSlist();
		mChildViewHolder.mPlateStockExpandListAdapter = new PlateStockExpandListAdapter(mContext, mChildViewHolder.slist);
		mChildViewHolder.listview.setAdapter(mChildViewHolder.mPlateStockExpandListAdapter);
		mChildViewHolder.mPlateStockExpandListAdapter.notifyDataSetChanged();
		
		return convertView;
	}
	
	public class ChildViewHolder {
		ExpendGridView gridView;
		PlateGridExpandAdapter mPlateGridExpandAdapter;
		List<PlateBean> plsit = new ArrayList<PlateBean>();

		ExpendListView listview;
		PlateStockExpandListAdapter mPlateStockExpandListAdapter;
		List<PlateStockBean>  slist = new ArrayList<PlateStockBean>();
	}

}
