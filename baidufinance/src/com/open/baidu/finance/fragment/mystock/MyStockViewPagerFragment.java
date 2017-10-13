/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午3:48:01
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.bean.CommonBean;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.MyStockViewPagerFragmentActivity;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午3:48:01
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MyStockViewPagerFragment extends BaseV4Fragment<CommonJson, MyStockViewPagerFragment> implements OnPageChangeListener {
	public ViewPager viewpager;
	public CommonFragmentPagerAdapter mCommonFragmentPagerAdapter;
	public List<Fragment> listFragment = new ArrayList<Fragment>();
	public List<String> titleList = new ArrayList<String>();
	public List<CommonBean> list = new ArrayList<CommonBean>();

	public static MyStockViewPagerFragment newInstance(String url, boolean isVisibleToUser) {
		MyStockViewPagerFragment fragment = new MyStockViewPagerFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mCommonFragmentPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listFragment, titleList);
		viewpager.setAdapter(mCommonFragmentPagerAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		viewpager.setOnPageChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#call()
	 */
	@Override
	public CommonJson call() throws Exception {
		// TODO Auto-generated method stub
		CommonJson mIndexFocusJson = new CommonJson();
		List<CommonBean> dlist = new ArrayList<CommonBean>();
		CommonBean bean = new CommonBean();
		dlist.add(bean);

		bean = new CommonBean();
		dlist.add(bean);

		mIndexFocusJson.setDotlist(dlist);
		return mIndexFocusJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(CommonJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getDotlist());
		for (int i = 0; i < list.size(); i++) {
			switch (i) {
			case 0:
				titleList.add("自选股");
				listFragment.add(MyStockPullToRefreshPinnedSectionListViewFragment.newInstance(UrlUtils.GATHERMYSTOCK, true));
				break;
			case 1:
				titleList.add("美股");
				listFragment.add(MyStockPullToRefreshPinnedSectionListViewFragment.newInstance(UrlUtils.GATHERMYSTOCK_US, false));
				break;
			default:
				break;
			}

		}
		mCommonFragmentPagerAdapter.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		((MyStockViewPagerFragmentActivity) getActivity()).setCenterTitle(titleList.get(arg0));
		position = arg0;
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

}
