/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:30:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.article;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.json.article.NewsContainerJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:30:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class NewsContainerPullScrollFragment extends BaseV4Fragment<NewsContainerJson, NewsContainerPullScrollFragment>
implements OnRefreshListener<ScrollView>{
	private TextView txt_title,txt_time,txt_news;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private FrameLayout layout_more;
	
	public static NewsContainerPullScrollFragment newInstance(String url, boolean isVisibleToUser) {
		NewsContainerPullScrollFragment fragment = new NewsContainerPullScrollFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_container_pullscroll, container, false);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_time = (TextView) view.findViewById(R.id.txt_time);
		txt_news = (TextView) view.findViewById(R.id.txt_news);
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pulltorefreshscrollview);
		layout_more = (FrameLayout) view.findViewById(R.id.layout_more);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshScrollView.setMode(Mode.PULL_FROM_START);
		
		NewsContainerFootExpendListFragment fragment = NewsContainerFootExpendListFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_more, fragment).commit();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullToRefreshScrollView.setOnRefreshListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public NewsContainerJson call() throws Exception {
		// TODO Auto-generated method stub
		NewsContainerJson mNewsContainerJson = new NewsContainerJson();
		mNewsContainerJson.setList(TagNewsJsoupService.parseArticle(url, pageNo));
		return mNewsContainerJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(NewsContainerJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null && result.getList()!=null){
			txt_title.setText(result.getList().get(0).getTitle());
			txt_time.setText(result.getList().get(0).getTime());
			txt_news.setText(result.getList().get(0).getNewsbox());
		}
		mPullToRefreshScrollView.onRefreshComplete();
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
	 * @see com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener#onRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		}
	}
}
