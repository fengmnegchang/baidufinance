/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:54:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.news;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.baidu.finance.adapter.news.QuestionAdapter;
import com.open.baidu.finance.bean.news.QuestionBean;
import com.open.baidu.finance.json.news.ExpertListDataJson;
import com.open.baidu.finance.json.news.QuestionJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:54:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QuestionPullListFragment extends CommonPullToRefreshListFragment<QuestionBean,QuestionJson>{
	private QuestionAdapter mQuestionAdapter;
	
	public static QuestionPullListFragment newInstance(String url, boolean isVisibleToUser) {
		QuestionPullListFragment fragment = new QuestionPullListFragment();
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
		mQuestionAdapter = new QuestionAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mQuestionAdapter);
		mPullToRefreshListView.setMode(Mode.BOTH);
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
			if(pageNo<=1){
				doAsync(this, this, this);
			}else{
				volleyJson(UrlUtils.QUESTION_LIST+pageNo+"&id="+url.replace(UrlUtils.EXPERT_TAB, "").replace("?tab=1", ""));
			}
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public QuestionJson call() throws Exception {
		// TODO Auto-generated method stub
		QuestionJson mAdviserPersonJson = new QuestionJson();
		mAdviserPersonJson.setList(TagNewsJsoupService.parseQuestion(url, pageNo));
		return mAdviserPersonJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(QuestionJson result) {
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
			mQuestionAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	@Override
	public void volleyJson(final String href) {
		// TODO Auto-generated method stub
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent",UrlUtils.userAgent);
//		params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					ExpertListDataJson result = gson.fromJson(response.toString(), ExpertListDataJson.class);
					QuestionJson mAdviserPersonJson = new QuestionJson();
					mAdviserPersonJson.setList(TagNewsJsoupService.parseQuestion(result.getHtml(), pageNo));
					onCallback(mAdviserPersonJson);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, QuestionPullListFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
}