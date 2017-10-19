/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:23:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobeta.android.dslv.DragSortListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.news.SubscribeEditDragSortAdapter;
import com.open.baidu.finance.bean.news.SubscribeBean;
import com.open.baidu.finance.json.news.SubscribeJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:23:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SubscribeEditDragSortFragment extends BaseV4Fragment<SubscribeJson, SubscribeEditDragSortFragment> implements DragSortListView.DropListener, DragSortListView.RemoveListener {
	public DragSortListView mDragSortListView;
	public List<SubscribeBean> list = new ArrayList<SubscribeBean>();
	public SubscribeEditDragSortAdapter mSubscribeEditDragSortAdapter;
	private View headview,footview;
	
	public static SubscribeEditDragSortFragment newInstance(String url, boolean isVisibleToUser) {
		SubscribeEditDragSortFragment fragment = new SubscribeEditDragSortFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_subscribe_edit_drag_sort_listview, container, false);
		mDragSortListView = (DragSortListView) view.findViewById(R.id.dragsort_listview);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_subscribe_headview, null);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_subscribe_footview, null);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mDragSortListView.addHeaderView(headview);
		mDragSortListView.addFooterView(footview);
		SubscribeHeadFragment hFragment = SubscribeHeadFragment.newInstance(url, weakReferenceHandler, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_subscribe_headview, hFragment).commit();
		
		SubscribeFootFragment fFragment = SubscribeFootFragment.newInstance(url, weakReferenceHandler, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_subscribe_footview, fFragment).commit();
		
		mSubscribeEditDragSortAdapter = new SubscribeEditDragSortAdapter(getActivity(), weakReferenceHandler, list);
		mDragSortListView.setAdapter(mSubscribeEditDragSortAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mDragSortListView.setDropListener(this);
		mDragSortListView.setRemoveListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.mobeta.android.dslv.DragSortListView.RemoveListener#remove(int)
	 */
	@Override
	public void remove(int which) {
		// TODO Auto-generated method stub
		mSubscribeEditDragSortAdapter.removeItem(which);
		mDragSortListView.removeCheckState(which);
	}

	/* (non-Javadoc)
	 * @see com.mobeta.android.dslv.DragSortListView.DropListener#drop(int, int)
	 */
	@Override
	public void drop(int from, int to) {
		// TODO Auto-generated method stub
		if (from != to) {
			SubscribeBean item = (SubscribeBean) mSubscribeEditDragSortAdapter.getItem(from);
			mSubscribeEditDragSortAdapter.removeItem(from);
			mSubscribeEditDragSortAdapter.insertItem(item, to);
			mDragSortListView.moveCheckState(from, to);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message
	 * )
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			break;
		case 9000:
			//新增
			Log.d(TAG, "edit add");
			SubscribeBean bean = (SubscribeBean) msg.obj;
			bean.setCheckType(1);
			list.add(bean);
			mSubscribeEditDragSortAdapter.notifyDataSetChanged();
			
			SubscribeFootFragment fFragment = (SubscribeFootFragment) getChildFragmentManager().findFragmentById(R.id.id_subscribe_footview);
			fFragment.setFootAllVisiable();
			break;
		case 9001:
			//去除
			Log.d(TAG, "edit remove");
			SubscribeBean removebean = (SubscribeBean) msg.obj;
			removebean.setCheckType(0);
			
			SubscribeFootFragment ffFragment = (SubscribeFootFragment) getChildFragmentManager().findFragmentById(R.id.id_subscribe_footview);
			ffFragment.addSubscribe(removebean);
			ffFragment.setFootAllVisiable();
			break;
		default:
			break;
		}
	}
	
}
