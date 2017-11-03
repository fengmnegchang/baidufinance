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

import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.baidu.finance.adapter.news.HotTiebaTopicAdapter;
import com.open.baidu.finance.bean.news.HotTiebaTopicBean;
import com.open.baidu.finance.json.news.HotTiebaTopicJson;
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
public class HotTiebaTopicPullListFragment extends CommonPullToRefreshListFragment<HotTiebaTopicBean, HotTiebaTopicJson> {
	private HotTiebaTopicAdapter mHotTiebaTopicAdapter;

	public static HotTiebaTopicPullListFragment newInstance(String url, boolean isVisibleToUser) {
		HotTiebaTopicPullListFragment fragment = new HotTiebaTopicPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
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
		mHotTiebaTopicAdapter = new HotTiebaTopicAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mHotTiebaTopicAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#
	 * handlerMessage(android.os.Message)
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
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public HotTiebaTopicJson call() throws Exception {
		// TODO Auto-generated method stub
		HotTiebaTopicJson mHotTiebaTopicJson = new HotTiebaTopicJson();
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mHotTiebaTopicJson.setList(TagNewsJsoupService.parseHotTiebaTopic(UrlUtils.GUPIAO_BAIDU, pageNo));
			
			Gson gson = new Gson();
			OpenDBBean openbean = new OpenDBBean();
			openbean.setTitle(gson.toJson(mHotTiebaTopicJson));
			
			openbean.setDownloadurl("");
			openbean.setImgsrc("");
			openbean.setType(pageNo);
			openbean.setTypename(pageNo+"");
			openbean.setUrl(UrlUtils.GUPIAO_BAIDU+"HOT");
			OpenDBService.insert(getActivity(), openbean);
		}else{
			List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),UrlUtils.GUPIAO_BAIDU+"HOT", pageNo+"");
			Gson gson = new Gson();
			mHotTiebaTopicJson = gson.fromJson(dblist.get(0).getTitle(), HotTiebaTopicJson.class);
		}
		return mHotTiebaTopicJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(HotTiebaTopicJson result) {
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
			mHotTiebaTopicAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}

}
