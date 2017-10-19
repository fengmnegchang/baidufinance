/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午5:23:13
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.article;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.view.ExpendListView;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.article.NewsContainerPullScrollFragmentActivity;
import com.open.baidu.finance.adapter.article.NewsContainerFootAdapter;
import com.open.baidu.finance.bean.news.TagNewsBean;
import com.open.baidu.finance.fragment.news.SubscribeHeadFragment;
import com.open.baidu.finance.json.news.TagNewsDataModel;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午5:23:13
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class NewsContainerFootExpendListFragment extends BaseV4Fragment<TagNewsDataModel, NewsContainerFootExpendListFragment>
implements OnItemClickListener
{
	public ExpendListView mExpendListView;
	public NewsContainerFootAdapter mNewsContainerFootAdapter;
	private List<TagNewsBean> list = new ArrayList<TagNewsBean>();
	
	
	public static NewsContainerFootExpendListFragment newInstance(String url,  boolean isVisibleToUser) {
		NewsContainerFootExpendListFragment fragment = new NewsContainerFootExpendListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_container_foot_expend_listview, container, false);
		mExpendListView = (ExpendListView) view.findViewById(R.id.expend_listview);
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
		mNewsContainerFootAdapter = new NewsContainerFootAdapter(getActivity(), list);
		mExpendListView.setAdapter(mNewsContainerFootAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mExpendListView.setOnItemClickListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public TagNewsDataModel call() throws Exception {
		// TODO Auto-generated method stub
		TagNewsDataModel mTagNewsDataModel = new TagNewsDataModel();
		mTagNewsDataModel.setTagnews(TagNewsJsoupService.parseNewsContainerFoot(url, pageNo));
		return mTagNewsDataModel;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(TagNewsDataModel result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			list.clear();
			list.addAll(result.getTagnews());
			mNewsContainerFootAdapter.notifyDataSetChanged();
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

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(id!=-1 && list!=null && list.get((int)id)!=null){
			NewsContainerPullScrollFragmentActivity.startNewsContainerPullScrollFragmentActivity(getActivity(), list.get((int)id).getUrl());
		}
	}

	
}
