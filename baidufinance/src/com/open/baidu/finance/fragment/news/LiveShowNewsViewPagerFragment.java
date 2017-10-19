/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:23:14
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
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.news.LiveShowNewsAdapter;
import com.open.baidu.finance.bean.news.LiveShowBean;
import com.open.baidu.finance.json.news.LiveShowJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19上午10:23:14
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class LiveShowNewsViewPagerFragment extends BaseV4Fragment<LiveShowJson, LiveShowNewsViewPagerFragment>{
	private ViewPager mViewPager;
	private LiveShowNewsAdapter mLiveShowNewsAdapter;
	private List<LiveShowBean> list = new ArrayList<LiveShowBean>();
	
	public static LiveShowNewsViewPagerFragment newInstance(String url, boolean isVisibleToUser) {
		LiveShowNewsViewPagerFragment fragment = new LiveShowNewsViewPagerFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_live_show_viewpager, container, false);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mLiveShowNewsAdapter = new LiveShowNewsAdapter(getActivity(), list);
		mViewPager.setAdapter(mLiveShowNewsAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
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
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public LiveShowJson call() throws Exception {
		// TODO Auto-generated method stub
		LiveShowJson mLiveShowJson = new LiveShowJson();
		mLiveShowJson.setList(TagNewsJsoupService.parseLiveNews(url, pageNo));
		return mLiveShowJson;
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(LiveShowJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null){
			list.clear();
			list.addAll(result.getList());
			mLiveShowNewsAdapter.notifyDataSetChanged();
		}
	}
}
