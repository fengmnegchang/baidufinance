/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午10:29:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.hot.SurveyPinnedSectionListAdapter;
import com.open.baidu.finance.bean.hot.OrganizationSurveyBean;
import com.open.baidu.finance.json.hot.OrganizationSurveyDateJson;
import com.open.baidu.finance.json.hot.OrganizationSurveyJson;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午10:29:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SurveyPinnedSectionListFragment extends BaseV4Fragment<OrganizationSurveyDateJson, SurveyPinnedSectionListFragment> implements OnRefreshListener<ListView> {
	public PullToRefreshPinnedSectionListView mPullToRefreshPinnedSectionListView;
	private SurveyPinnedSectionListAdapter mSurveyPinnedSectionListAdapter;
	private List<OrganizationSurveyBean> list = new ArrayList<OrganizationSurveyBean>();

	public static SurveyPinnedSectionListFragment newInstance(String url, boolean isVisibleToUser) {
		SurveyPinnedSectionListFragment fragment = new SurveyPinnedSectionListFragment();
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
		mSurveyPinnedSectionListAdapter = new SurveyPinnedSectionListAdapter(getActivity(), list);
		mPullToRefreshPinnedSectionListView.setAdapter(mSurveyPinnedSectionListAdapter);
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
	public OrganizationSurveyDateJson call() throws Exception {
		// TODO Auto-generated method stub
		OrganizationSurveyDateJson mHotConceptJson = new OrganizationSurveyDateJson();
		try {
			InputStream is = getActivity().getResources().getAssets().open("survey.json");
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
	        int ch;
	        while ((ch = is.read()) != -1) {
	            bytestream.write(ch);
	        }
	        byte imgdata[] = bytestream.toByteArray();
	        bytestream.close();
	        String data = new String(imgdata);   
	        Gson gson = new Gson();
	        
	        mHotConceptJson = gson.fromJson(data, OrganizationSurveyDateJson.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	public void onCallback(OrganizationSurveyDateJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			Log.i(TAG, "getMode ===" + mPullToRefreshPinnedSectionListView.getCurrentMode());
			if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				 for(OrganizationSurveyJson allBean :result.getList()){
					 	OrganizationSurveyBean bean = new OrganizationSurveyBean();
						bean.setViewType(1);
						bean.setDateTime(allBean.getDateTime());
						list.add(bean);
						
						for(OrganizationSurveyBean subean:allBean.getList()){
							subean.setDateTime(allBean.getDateTime());
							list.add(subean);
						}
				 }
				pageNo = 1;
			} else {
				if (result.getList() != null && result.getList().size() > 0) {
					 for(OrganizationSurveyJson allBean :result.getList()){
						 	OrganizationSurveyBean bean = new OrganizationSurveyBean();
							bean.setViewType(1);
							bean.setDateTime(allBean.getDateTime());
							list.add(bean);
							
							for(OrganizationSurveyBean subean:allBean.getList()){
								subean.setDateTime(allBean.getDateTime());
								list.add(subean);
							}
					 }
				}
			}
			mSurveyPinnedSectionListAdapter.notifyDataSetChanged();
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

}
