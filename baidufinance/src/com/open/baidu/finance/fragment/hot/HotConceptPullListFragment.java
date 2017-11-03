/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:48:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import java.util.List;

import android.util.Log;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.baidu.finance.adapter.hot.HotConceptAdapter;
import com.open.baidu.finance.bean.hot.HotConceptBean;
import com.open.baidu.finance.json.hot.HotConceptJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午3:48:05
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotConceptPullListFragment extends CommonPullToRefreshListFragment<HotConceptBean,HotConceptJson>{
	private HotConceptAdapter mHotConceptAdapter;
	
	public static HotConceptPullListFragment newInstance(String url, boolean isVisibleToUser) {
		HotConceptPullListFragment fragment = new HotConceptPullListFragment();
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
		mHotConceptAdapter = new HotConceptAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mHotConceptAdapter);
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public HotConceptJson call() throws Exception {
		// TODO Auto-generated method stub
		HotConceptJson mHotConceptJson= new HotConceptJson();
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mHotConceptJson.setList(TagNewsJsoupService.parseHot(url, pageNo));
			Gson gson = new Gson();
			System.out.println(gson.toJson(mHotConceptJson));
			OpenDBBean openbean = new OpenDBBean();
			openbean.setTitle(gson.toJson(mHotConceptJson));
			
			openbean.setDownloadurl("");
			openbean.setImgsrc("");
			openbean.setType(pageNo);
			openbean.setTypename(pageNo+"");
			openbean.setUrl(url);
			OpenDBService.insert(getActivity(), openbean);
		}else{
			List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),url, pageNo+"");
			Gson gson = new Gson();
			mHotConceptJson = gson.fromJson(dblist.get(0).getTitle(), HotConceptJson.class);
		}
		return mHotConceptJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(HotConceptJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
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
			mHotConceptAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}
}
