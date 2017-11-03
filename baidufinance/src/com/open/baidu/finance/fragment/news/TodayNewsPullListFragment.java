/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午4:57:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.news;

import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.andenginetask.CallEarliest;
import com.open.andenginetask.Callable;
import com.open.andenginetask.Callback;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.article.NewsContainerPullScrollFragmentActivity;
import com.open.baidu.finance.activity.news.HotTiebaTopicPullListFragmentActivity;
import com.open.baidu.finance.adapter.news.TagNewsAdapter;
import com.open.baidu.finance.bean.news.TagNewsBean;
import com.open.baidu.finance.json.news.HotTiebaTopicJson;
import com.open.baidu.finance.json.news.TagNewsDataJson;
import com.open.baidu.finance.json.news.TagNewsDataModel;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午4:57:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class TodayNewsPullListFragment extends CommonPullToRefreshListFragment<TagNewsBean, TagNewsDataJson>
implements OnClickListener{
	private TagNewsAdapter mTagNewsAdapter;
	private View headview;
	private TextView txt_hotall,txt_topic;
	
	public static TodayNewsPullListFragment newInstance(String url, boolean isVisibleToUser) {
		TodayNewsPullListFragment fragment = new TodayNewsPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_today_viewpager_headview, null);
		txt_hotall = (TextView) headview.findViewById(R.id.txt_hotall);
		txt_topic = (TextView) headview.findViewById(R.id.txt_topic);
		return view;
	}

	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mPullToRefreshListView.getRefreshableView().addHeaderView(headview);
		LiveShowNewsViewPagerFragment fragment = LiveShowNewsViewPagerFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_today_viewpager_headview, fragment).commit();
		
		mTagNewsAdapter = new TagNewsAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mTagNewsAdapter);
		
		
		doAsync(new CallEarliest<HotTiebaTopicJson>() {
			@Override
			public void onCallEarliest() throws Exception {
				// TODO Auto-generated method stub
				
			}
		}, new Callable<HotTiebaTopicJson>(){
			@Override
			public HotTiebaTopicJson call() throws Exception {
				// TODO Auto-generated method stub
				HotTiebaTopicJson mHotTiebaTopicJson = new HotTiebaTopicJson();
				mHotTiebaTopicJson.setList(TagNewsJsoupService.parseHotTiebaTopic(UrlUtils.GUPIAO_BAIDU, pageNo));
				return mHotTiebaTopicJson;
			}
			
		} , new Callback<HotTiebaTopicJson>(){

			@Override
			public void onCallback(HotTiebaTopicJson result) {
				// TODO Auto-generated method stub
				if (result != null) {
					try {
						txt_topic.setText(result.getList().get(0).getTitle());
						txt_topic.setTag(result.getList().get(0).getHref());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
			
		});
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		txt_hotall.setOnClickListener(this);
		txt_topic.setOnClickListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#handlerMessage(android.os.Message)
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
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public TagNewsDataJson call() throws Exception {
		// TODO Auto-generated method stub
		TagNewsDataJson mTagNewsDataJson = new TagNewsDataJson();
		TagNewsDataModel mTagNewsDataModel = new  TagNewsDataModel();
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mTagNewsDataModel.setTagnews(TagNewsJsoupService.parseTodayNews(UrlUtils.GUPIAO_BAIDU, pageNo));
			
			Gson gson = new Gson();
			OpenDBBean openbean = new OpenDBBean();
			openbean.setTitle(gson.toJson(mTagNewsDataModel));
			
			openbean.setDownloadurl("");
			openbean.setImgsrc("");
			openbean.setType(pageNo);
			openbean.setTypename(pageNo+"");
			openbean.setUrl(UrlUtils.GUPIAO_BAIDU+"TODAY");
			OpenDBService.insert(getActivity(), openbean);
		}else{
			List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),UrlUtils.GUPIAO_BAIDU+"TODAY", pageNo+"");
			Gson gson = new Gson();
			mTagNewsDataModel = gson.fromJson(dblist.get(0).getTitle(), TagNewsDataModel.class);
		}
		
		mTagNewsDataJson.setData(mTagNewsDataModel);
		return mTagNewsDataJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(TagNewsDataJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null){
			Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
			if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				list.addAll(result.getData().getTagnews());
				pageNo = 1;
			} else {
				if (result.getData().getTagnews() != null && result.getData().getTagnews().size() > 0) {
					list.addAll(result.getData().getTagnews());
				}
			}
			mTagNewsAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_hotall:
			HotTiebaTopicPullListFragmentActivity.startHotTiebaTopicPullListFragmentActivity(getActivity(), null);
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
