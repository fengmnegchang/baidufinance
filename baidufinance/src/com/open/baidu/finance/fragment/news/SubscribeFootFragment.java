/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:35:58
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.view.ExpendListView;
import com.open.android.weak.WeakReferenceHandler;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.news.SubscribeHeadFootAdapter;
import com.open.baidu.finance.bean.news.SubscribeBean;
import com.open.baidu.finance.json.news.SubscribeJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午2:35:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SubscribeFootFragment extends BaseV4Fragment<SubscribeJson, SubscribeFootFragment> {
	private SubscribeHeadFootAdapter mSubscribeHeadFootAdapter;
	private WeakReferenceHandler weakReferenceHandler;
	public ExpendListView mExpendListView;
	private List<SubscribeBean> list = new ArrayList<SubscribeBean>();
	private TextView txt_footall;

	public static SubscribeFootFragment newInstance(String url, WeakReferenceHandler weakReferenceHandler, boolean isVisibleToUser) {
		SubscribeFootFragment fragment = new SubscribeFootFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.weakReferenceHandler = weakReferenceHandler;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_subscribe_foot_expend_listview, container, false);
		mExpendListView = (ExpendListView) view.findViewById(R.id.expend_listview);
		txt_footall = (TextView) view.findViewById(R.id.txt_footall);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#initValues
	 * ()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mSubscribeHeadFootAdapter = new SubscribeHeadFootAdapter(getActivity(), weakReferenceHandler, list);
		mExpendListView.setAdapter(mSubscribeHeadFootAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public SubscribeJson call() throws Exception {
		// TODO Auto-generated method stub
		SubscribeJson mSubscribeJson = new SubscribeJson();
		List<SubscribeBean> clist = new ArrayList<SubscribeBean>();
		SubscribeBean bean = null;

		String[] array = getResources().getStringArray(R.array.news_edit_subscribe);
		for (int i = 0; i < array.length; i++) {
			bean = new SubscribeBean();
			bean.setKeyName(array[i]);
			bean.setCheckType(0);
			clist.add(bean);
		}
		mSubscribeJson.setList(clist);
		return mSubscribeJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(SubscribeJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			list.clear();
			list.addAll(result.getList());
			mSubscribeHeadFootAdapter.notifyDataSetChanged();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}

	/**
	 * 显示提示
	 */
	public void setFootAllVisiable() {
		if (list.size() == 0) {
			txt_footall.setVisibility(View.VISIBLE);
		} else {
			txt_footall.setVisibility(View.GONE);
		}
	}
	
	
	/**
	 * 新增
	 */
	public void addSubscribe(SubscribeBean bean){
		list.add(bean);
		mSubscribeHeadFootAdapter.notifyDataSetChanged();
	}

}
