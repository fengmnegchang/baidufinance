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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.widget.ScrollableHelper.ScrollableContainer;
import com.open.baidu.finance.activity.article.NewsContainerPullScrollFragmentActivity;
import com.open.baidu.finance.adapter.news.TagNewsAdapter;
import com.open.baidu.finance.bean.news.TagNewsBean;
import com.open.baidu.finance.json.news.GetTodayNewsJson;
import com.open.baidu.finance.json.news.TagNewsDataJson;
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
public class MFootTodayNewsPullListFragment extends CommonPullToRefreshListFragment<TagNewsBean, GetTodayNewsJson> implements ScrollableContainer{
	private TagNewsAdapter mTagNewsAdapter;
	
	public static MFootTodayNewsPullListFragment newInstance(String url, boolean isVisibleToUser) {
		MFootTodayNewsPullListFragment fragment = new MFootTodayNewsPullListFragment();
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
		mTagNewsAdapter = new TagNewsAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mTagNewsAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			volleyJson(url);
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public GetTodayNewsJson call() throws Exception {
		// TODO Auto-generated method stub
		return super.call();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(GetTodayNewsJson result) {
		// TODO Auto-generated method stub
		if(result!=null){
			Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
			if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				list.addAll(result.getData().getTodaynews().getData());
				pageNo = 1;
			} else {
				if (result.getData().getTodaynews().getData() != null && result.getData().getTodaynews().getData().size() > 0) {
					list.addAll(result.getData().getTodaynews().getData());
				}
			}
			mTagNewsAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android.volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
		List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),url, pageNo+"");
		Gson gson = new Gson();
		GetTodayNewsJson mTagNewsDataJson = gson.fromJson(dblist.get(0).getTitle(), GetTodayNewsJson.class);
		onCallback(mTagNewsDataJson);
	}
	
	@Override
	public void volleyJson(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent",UrlUtils.userAgentMoblie);
		params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					GetTodayNewsJson result = gson.fromJson(response.toString(), GetTodayNewsJson.class);
					onCallback(result);
					
					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(response.toString());
					openbean.setDownloadurl("");
					openbean.setImgsrc("");
					openbean.setType(pageNo);
					openbean.setTypename(pageNo+"");
					openbean.setUrl(url);
					OpenDBService.insert(getActivity(), openbean);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, MFootTodayNewsPullListFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
 
	
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(id!=-1 && list!=null && list.get((int)id)!=null){
			NewsContainerPullScrollFragmentActivity.startNewsContainerPullScrollFragmentActivity(getActivity(), UrlUtils.ARTICLE_URL+list.get((int)id).getNid());
		}
	}

	/* (non-Javadoc)
	 * @see com.open.android.widget.ScrollableHelper.ScrollableContainer#getScrollableView()
	 */
	@Override
	public View getScrollableView() {
		// TODO Auto-generated method stub
		return mPullToRefreshListView.getRefreshableView();
	}
}
