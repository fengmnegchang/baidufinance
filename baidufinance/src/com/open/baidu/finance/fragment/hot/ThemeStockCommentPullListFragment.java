/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-30下午3:02:47
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NormalPostRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.article.EmojiBean;
import com.open.baidu.finance.fragment.article.MNewsCommentPullListFragment;
import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.article.EmojiJson;
import com.open.baidu.finance.json.article.GetCommentListJson;
import com.open.baidu.finance.json.article.GetCommentListModel;
import com.open.baidu.finance.json.hot.VotetoConceptDataJson;
import com.open.baidu.finance.json.news.ExpertListDataJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-30下午3:02:47
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class ThemeStockCommentPullListFragment extends MNewsCommentPullListFragment {
	private View headview;
	private TextView txt_up_num, txt_down_num;

	public static ThemeStockCommentPullListFragment newInstance(String url, boolean isVisibleToUser) {
		ThemeStockCommentPullListFragment fragment = new ThemeStockCommentPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_comment_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		edit_search = (TextView) view.findViewById(R.id.edit_search);
		txt_nocomment = (TextView) view.findViewById(R.id.txt_nocomment);
		img_nocommnets = (ImageView) view.findViewById(R.id.img_nocommnets);

		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_headview_theme_stock_comment, null);
		txt_up_num = (TextView) headview.findViewById(R.id.txt_up_num);
		txt_down_num = (TextView) headview.findViewById(R.id.txt_down_num);
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
		mPullToRefreshListView.getRefreshableView().addHeaderView(headview);
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshListView.setMode(Mode.BOTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.baidu.finance.fragment.article.MNewsCommentPullListFragment#
	 * bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		txt_up_num.setOnClickListener(this);
		txt_down_num.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.baidu.finance.fragment.article.MNewsCommentPullListFragment#
	 * call()
	 */
	@Override
	public GetCommentListJson call() throws Exception {
		// TODO Auto-generated method stub
		GetCommentListJson mGetCommentListJson = new GetCommentListJson();
		GetCommentListModel mGetCommentListModel = new GetCommentListModel();
		mGetCommentListModel.setComments(TagNewsJsoupService.parseThmemeComment(url, pageNo));
		mGetCommentListJson.setData(mGetCommentListModel);
		return mGetCommentListJson;
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
			if (pageNo == 1) {
				doAsync(this, this, this);
				try {
					String fileNames[] = getActivity().getAssets().list("emoji");
					EmojiBean emojiBean;
					List<EmojiBean> elist = new ArrayList<EmojiBean>();
					for (int i = 0; i < fileNames.length; i++) {
						emojiBean = new EmojiBean();
						emojiBean.setFaceName(fileNames[i]);
						elist.add(emojiBean);

						if (i == 17 || i == 35 || i == fileNames.length - 1) {
							EmojiJson mEmojiJson = new EmojiJson();
							mEmojiJson.setList(elist);
							emojilist.add(mEmojiJson);
							elist = new ArrayList<EmojiBean>();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				try {
					date = formatter.parse(list.get(list.size() - 1).getDateTimeStr() + ":00");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// 1441000491900&timestamp=1509346513396&sid=1000313
				volleyJson(UrlUtils.COMMENTLIST + date.getTime() + "&timestamp=" + new Date().getTime() + "&sid=" + url.replace(UrlUtils.CONCEPT, "").replace(".html", ""));
			}
			break;
		case 10000:
			//评论
			createComment(UrlUtils.CREATECOMMENT, (String)msg.obj);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}

	@Override
	public void volleyJson(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String, String> params = new HashMap<String, String>();
		params.put("User-Agent", UrlUtils.userAgent);
		// params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href, params, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					ExpertListDataJson result = gson.fromJson(response.toString(), ExpertListDataJson.class);

					GetCommentListJson mGetCommentListJson = new GetCommentListJson();
					GetCommentListModel mGetCommentListModel = new GetCommentListModel();
					mGetCommentListModel.setComments(TagNewsJsoupService.parseThmemeComment(result.getHtml(), pageNo));
					mGetCommentListJson.setData(mGetCommentListModel);

					onCallback(mGetCommentListJson);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, ThemeStockCommentPullListFragment.this);
		requestQueue.add(jsonObjectRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.baidu.finance.fragment.article.MNewsCommentPullListFragment#
	 * onCallback(com.open.baidu.finance.json.article.GetCommentListJson)
	 */
	@Override
	public void onCallback(GetCommentListJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		try {
			txt_up_num.setText(result.getData().getComments().get(0).getUpNum());
			txt_down_num.setText(result.getData().getComments().get(0).getDownNum());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.baidu.finance.fragment.article.MNewsCommentPullListFragment#
	 * onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.txt_down_num:
			votetoconcept(UrlUtils.VOTETOCONCEPT, 2);
			break;
		case R.id.txt_up_num:
			votetoconcept(UrlUtils.VOTETOCONCEPT, 1);
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	public void votetoconcept(final String href,int like) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		final Map<String,String> headers = new HashMap<String,String>(); 
		headers.put("User-Agent",UrlUtils.userAgent);
		headers.put("Referer",url);
		headers.put("Cookie", UrlUtils.COOKIE);
		
		final Map<String, String> params = new HashMap<String, String>();
		params.put("from", "pc");
		params.put("os_ver", "1");
		params.put("cuid", "xxx");
		params.put("vv", "2.3");
		params.put("conceptid", url.replace(UrlUtils.CONCEPT, "").replace(".html", ""));
		params.put("format", "json");
		params.put("like", like+"");
		params.put("token", UrlUtils.TOKEN);
		params.put("timestamp", System.currentTimeMillis()+"");

		Log.d(TAG, "href=="+href);
		Log.d(TAG, "headers=="+headers);
		Log.d(TAG, "params=="+params);
		Request<JSONObject> request = new NormalPostRequest(href,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			            Log.d(TAG, "response -> " + response.toString());
			            //{"errorNo":0,"errorMsg":"SUCCESS","data":{"conceptId":1000749,"likeNum":"16","dislikeNum":"8","updatetime":1509351573550}}
			            Gson gson = new Gson();
			            VotetoConceptDataJson json = gson.fromJson(response.toString(), VotetoConceptDataJson.class);
			            if(json!=null){
			            	txt_up_num.setText(json.getData().getLikeNum());
			    			txt_down_num.setText(json.getData().getDislikeNum());
			            	Toast.makeText(getActivity(), json.getErrorMsg(), Toast.LENGTH_LONG).show();
			            }
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			            Log.e(TAG, error.getMessage(), error);
			        }
			    }, headers,params);

		requestQueue.add(request);
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	public void createComment(final String href,String content) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		final Map<String,String> headers = new HashMap<String,String>(); 
		headers.put("User-Agent",UrlUtils.userAgent);
		headers.put("Referer",url);
		headers.put("Cookie", UrlUtils.COOKIE);
		
		final Map<String, String> params = new HashMap<String, String>();
		params.put("from", "pc");
		params.put("os_ver", "1");
		params.put("cuid", "xxx");
		params.put("vv", "2.3");
		params.put("sid", url.replace(UrlUtils.CONCEPT, "").replace(".html", ""));
		params.put("format", "json");
		params.put("uname", UrlUtils.UNAME);
		params.put("stype", "2");
		params.put("content", content);
		params.put("token", UrlUtils.TOKEN);
		params.put("timestamp", System.currentTimeMillis()+"");

		Log.d(TAG, "href=="+href);
		Log.d(TAG, "headers=="+headers);
		Log.d(TAG, "params=="+params);
		Request<JSONObject> request = new NormalPostRequest(href,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			            Log.d(TAG, "response -> " + response.toString());
			            //{"errorNo":0,"errorMsg":"SUCCESS","data":{"commentid":"58041420115093523634927","timestamp":1509352364627}}
			            Gson gson = new Gson();
			            CommonDataJson json = gson.fromJson(response.toString(), CommonDataJson.class);
			            if(json!=null){
			            	Toast.makeText(getActivity(), json.getErrorMsg(), Toast.LENGTH_LONG).show();
			            }
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			            Log.e(TAG, error.getMessage(), error);
			        }
			    }, headers,params);

		requestQueue.add(request);
	}
}
