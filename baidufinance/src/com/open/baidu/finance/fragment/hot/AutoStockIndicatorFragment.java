/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午11:21:47
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.MainTabBean;
import com.open.baidu.finance.json.MainTabJson;
import com.open.baidu.finance.utils.UrlUtils;
import com.open.indicator.TabPageIndicator;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午11:21:47
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class AutoStockIndicatorFragment extends BaseV4Fragment<MainTabJson, AutoStockIndicatorFragment> {
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public CommonFragmentPagerAdapter mRankPagerAdapter;
	public ArrayList<MainTabBean> list = new ArrayList<MainTabBean>();

	public static AutoStockIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		AutoStockIndicatorFragment fragment = new AutoStockIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_indicator_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public MainTabJson call() throws Exception {
		// TODO Auto-generated method stub
		MainTabJson mMainTabJson = new MainTabJson();
		ArrayList<MainTabBean> clist = new ArrayList<MainTabBean>();
		MainTabBean bean = new MainTabBean();
		bean.setHref(UrlUtils.GUPIAO_BAIDU);
		bean.setTitle("消息选股");
		clist.add(bean);

		bean = new MainTabBean();
		bean.setHref(UrlUtils.GETTAGNEWS_ECONOMY);
		bean.setTitle("主题选股");
		clist.add(bean);

		mMainTabJson.setList(clist);
		return mMainTabJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MainTabJson result) {
		// TODO Auto-generated method stub
		if (result == null) {
			return;
		}
		list.clear();
		list.addAll(result.getList());
		titleList.clear();

		Fragment fragment;
		for (int i = 0; i < result.getList().size(); i++) {
			MainTabBean bean = result.getList().get(i);
			titleList.add(bean.getTitle());
			if (i == 0) {
				fragment = MsgStockPinnedSectionListFragment.newInstance(bean.getHref(), true);
			} else {
				fragment = ThemeStockPinnedSectionListFragment.newInstance(bean.getHref(), false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
}