/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:20:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.news;

import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.baidu.finance.adapter.news.AdviserPersonAdapter;
import com.open.baidu.finance.bean.news.AdviserPersonBean;
import com.open.baidu.finance.json.news.AdviserPersonJson;
import com.open.baidu.finance.json.news.TagNewsDataJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:20:46
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class AdviserPersonPullListFragment extends CommonPullToRefreshListFragment<AdviserPersonBean,AdviserPersonJson>{
	private AdviserPersonAdapter mAdviserPersonAdapter;
	
	public static AdviserPersonPullListFragment newInstance(String url, boolean isVisibleToUser) {
		AdviserPersonPullListFragment fragment = new AdviserPersonPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mAdviserPersonAdapter = new AdviserPersonAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mAdviserPersonAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public AdviserPersonJson call() throws Exception {
		// TODO Auto-generated method stub
		AdviserPersonJson mAdviserPersonJson = new AdviserPersonJson();
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mAdviserPersonJson.setList(TagNewsJsoupService.parseAdviserPerson(url, 1));
			
			Gson gson = new Gson();
			OpenDBBean openbean = new OpenDBBean();
			openbean.setTitle(gson.toJson(mAdviserPersonJson));
			
			openbean.setDownloadurl("");
			openbean.setImgsrc("");
			openbean.setType(pageNo);
			openbean.setTypename(pageNo+"");
			openbean.setUrl(url+"PERSON");
			OpenDBService.insert(getActivity(), openbean);
		}else{
			List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),url+"PERSON", pageNo+"");
			Gson gson = new Gson();
			mAdviserPersonJson = gson.fromJson(dblist.get(0).getTitle(), AdviserPersonJson.class);
		}
		return mAdviserPersonJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(AdviserPersonJson result) {
		// TODO Auto-generated method stub
		if(result!=null){
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
			mAdviserPersonAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}
}
