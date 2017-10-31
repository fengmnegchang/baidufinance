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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.hot.ThemeStockDetailListFragmentActivity;
import com.open.baidu.finance.adapter.hot.ThemeStockPinnedSectionListAdapter;
import com.open.baidu.finance.bean.hot.HotConceptBean;
import com.open.baidu.finance.bean.hot.HotStockBean;
import com.open.baidu.finance.bean.hot.ThemeStockBean;
import com.open.baidu.finance.json.hot.HotConceptJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;

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
public class ThemeStockPinnedSectionListFragment extends BaseV4Fragment<HotConceptJson, ThemeStockPinnedSectionListFragment> implements OnRefreshListener<ListView>,OnItemClickListener {
	public PullToRefreshPinnedSectionListView mPullToRefreshPinnedSectionListView;
	private ThemeStockPinnedSectionListAdapter mThemeStockPinnedSectionListAdapter;
	private List<ThemeStockBean> list = new ArrayList<ThemeStockBean>();

	public static ThemeStockPinnedSectionListFragment newInstance(String url, boolean isVisibleToUser) {
		ThemeStockPinnedSectionListFragment fragment = new ThemeStockPinnedSectionListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mystock_pulltorefresh_pinnedsection_listview, container, false);
		mPullToRefreshPinnedSectionListView = (PullToRefreshPinnedSectionListView) view.findViewById(R.id.pull_refresh_list);
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
		mThemeStockPinnedSectionListAdapter = new ThemeStockPinnedSectionListAdapter(getActivity(), list);
		mPullToRefreshPinnedSectionListView.setAdapter(mThemeStockPinnedSectionListAdapter);
		mPullToRefreshPinnedSectionListView.setMode(Mode.PULL_FROM_START);
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
		mPullToRefreshPinnedSectionListView.setOnRefreshListener(this);
		mPullToRefreshPinnedSectionListView.setOnItemClickListener(this);
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
	public HotConceptJson call() throws Exception {
		// TODO Auto-generated method stub
		HotConceptJson mHotConceptJson = new HotConceptJson();
		Date date = new Date();// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (pageNo == 1) {
			calendar.add(Calendar.DATE, 0);
		} else {
			calendar.add(Calendar.DATE, -(pageNo-1));
		}
		calendar.getTime();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = dft.format(calendar.getTime());
		mHotConceptJson.setList(TagNewsJsoupService.parseHot(UrlUtils.SEARCHDATE+currentDate, pageNo));
		return mHotConceptJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(HotConceptJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			Log.i(TAG, "getMode ===" + mPullToRefreshPinnedSectionListView.getCurrentMode());
			if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				ThemeStockBean tbean;
				for(HotConceptBean bean:result.getList()){
					tbean = new ThemeStockBean();
					tbean.setViewType(1);
					tbean.setTime(result.getList().get(0).getTime().replace("发布时间:", ""));
					tbean.setName(bean.getName());
					tbean.setSubject(bean.getSubject());
					tbean.setEvent(bean.getEvent());
					list.add(tbean);
					
					for(HotStockBean subbean:bean.getStocklist()){
						tbean = new ThemeStockBean();
						tbean.setViewType(0);
						tbean.setTime(result.getList().get(0).getTime().replace("发布时间:", ""));
						tbean.setName(bean.getName());
						tbean.setSubject(bean.getSubject());
						tbean.setEvent(bean.getEvent());
						
						tbean.setStockName(subbean.getStockName());
						tbean.setStockCode(subbean.getStockCode());
						tbean.setClose(subbean.getClose());
						tbean.setRate(subbean.getRate());
						tbean.setHref(subbean.getHref());
						list.add(tbean);
					}
				}
				pageNo = 1;
			} else {
				if (result.getList() != null && result.getList().size() > 0) {
					ThemeStockBean tbean;
					for(HotConceptBean bean:result.getList()){
						tbean = new ThemeStockBean();
						tbean.setViewType(1);
						tbean.setTime(result.getList().get(0).getTime().replace("发布时间:", ""));
						tbean.setName(bean.getName());
						tbean.setSubject(bean.getSubject());
						tbean.setEvent(bean.getEvent());
						list.add(tbean);
						
						for(HotStockBean subbean:bean.getStocklist()){
							tbean = new ThemeStockBean();
							tbean.setViewType(0);
							tbean.setTime(result.getList().get(0).getTime().replace("发布时间:", ""));
							tbean.setName(bean.getName());
							tbean.setSubject(bean.getSubject());
							tbean.setEvent(bean.getEvent());
							
							tbean.setStockName(subbean.getStockName());
							tbean.setStockCode(subbean.getStockCode());
							tbean.setClose(subbean.getClose());
							tbean.setRate(subbean.getRate());
							tbean.setHref(subbean.getHref());
							list.add(tbean);
						}
					}
				}
			}
			mThemeStockPinnedSectionListAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshPinnedSectionListView.onRefreshComplete();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener
	 * #onRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(id!=-1&& list!=null && list.get((int)id)!=null){
			ThemeStockDetailListFragmentActivity.startThemeStockDetailListFragmentActivity(getActivity(), list.get((int)id).getHref(),list.get((int)id).getEvent(),list.get((int)id).getName());
		}
	}

}
