/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午4:41:25
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.hot.ThemeStockDetailListAdapter;
import com.open.baidu.finance.bean.hot.HotStockBean;
import com.open.baidu.finance.json.hot.HotStockJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午4:41:25
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class ThemeStockDetailListFragment extends CommonPullToRefreshListFragment<HotStockBean, HotStockJson> {
	private ThemeStockDetailListAdapter mThemeStockDetailListAdapter;
	private View headview,footview;

	public static ThemeStockDetailListFragment newInstance(String url, boolean isVisibleToUser) {
		ThemeStockDetailListFragment fragment = new ThemeStockDetailListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_theme_stock_detail_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_themestock_detail_headview,null);
		footview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_themestock_detail_footview,null);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4MVPPFragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshListView.getRefreshableView().addHeaderView(headview);
		mPullToRefreshListView.getRefreshableView().addFooterView(footview);
		FollowLineChartFragment fragment = FollowLineChartFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_line, fragment).commit();
		
		mThemeStockDetailListAdapter = new ThemeStockDetailListAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mThemeStockDetailListAdapter);
		mPullToRefreshListView.setMode(Mode.PULL_FROM_START);
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
		mPullToRefreshListView.setOnRefreshListener(this);
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
			doAsync(this, this, this);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public HotStockJson call() throws Exception {
		// TODO Auto-generated method stub
		HotStockJson mHotStockJson = new HotStockJson();
		mHotStockJson.setList(TagNewsJsoupService.parseThmeme(url, pageNo));
		return mHotStockJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(HotStockJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
			if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				list.addAll(result.getList());
				pageNo = 1;
			} else {
				if (result.getList() != null && result.getList().size() > 0) {
					list.addAll(result.getList());
				}
			}
			mThemeStockDetailListAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}

}
