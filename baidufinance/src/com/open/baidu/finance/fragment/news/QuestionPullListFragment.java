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
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

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
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.NetWorkUtils;
import com.open.android.utils.ScreenUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.news.QuestionAdapter;
import com.open.baidu.finance.bean.news.QuestionBean;
import com.open.baidu.finance.json.news.ExpertListDataJson;
import com.open.baidu.finance.json.news.HotTiebaTopicJson;
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
	private TextView add_question;
	public PopupWindow popupWindow;
	
	public static QuestionPullListFragment newInstance(String url, boolean isVisibleToUser) {
		QuestionPullListFragment fragment = new QuestionPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_question_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		add_question = (TextView) view.findViewById(R.id.add_question);
		return view;
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
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		add_question.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopupWindow();
			}
		});
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
		if(NetWorkUtils.isNetworkAvailable(getActivity())){
			mAdviserPersonJson.setList(TagNewsJsoupService.parseQuestion(url, pageNo));
			Gson gson = new Gson();
			OpenDBBean openbean = new OpenDBBean();
			openbean.setTitle(gson.toJson(mAdviserPersonJson));
			
			openbean.setDownloadurl("");
			openbean.setImgsrc("");
			openbean.setType(pageNo);
			openbean.setTypename(pageNo+"");
			openbean.setUrl(url);
			OpenDBService.insert(getActivity(), openbean);
		}else{
			List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),url, pageNo+"");
			Gson gson = new Gson();
			mAdviserPersonJson = gson.fromJson(dblist.get(0).getTitle(), QuestionJson.class);
		}
		
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
		}, QuestionPullListFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
	public void showPopupWindow() {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_add_question_popup_window, null);
		final WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), (int) ScreenUtils.getIntToDip(getActivity(), 220));
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);// 设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));

		popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
		Button btn_send = (Button) view.findViewById(R.id.btn_send);
		final EditText edit_add = (EditText) view.findViewById(R.id.edit_add);
		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edit_add.getText().toString().length()>0){
					addQuestion(UrlUtils.USERQUESTIONADD, edit_add.getText().toString());
				}
			}
		});
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// popupWindow隐藏时恢复屏幕正常透明度
				setBackgroundAlpha(1.0f);
			}
		});

	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 *            屏幕透明度0.0-1.0 1表示完全不透明
	 */
	public void setBackgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha;
		getActivity().getWindow().setAttributes(lp);
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android.volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	public void addQuestion(final String href,String question) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		final Map<String,String> headers = new HashMap<String,String>(); 
		headers.put("User-Agent",UrlUtils.userAgent);
		headers.put("Referer",url);
		headers.put("Cookie", UrlUtils.COOKIE);
		/****
		 *from:pc
		os_ver:1
		cuid:xxx
		vv:100
		format:json
		cid:10008
		ask:qq
		token:be30d2f25178b7ff
		timestamp:1508997038711
		 */
		final Map<String, String> params = new HashMap<String, String>();
		params.put("from", "pc");
		params.put("os_ver", "1");
		params.put("cid", url.replace(UrlUtils.EXPERT_TAB, "").replace("?tab=1", ""));
		params.put("vv", "100");
		params.put("format", "json");
		params.put("ask", question);
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
			            //response -> {"errorNo":0,"errorMsg":"success"}
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