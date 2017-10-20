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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.mystock.MyStockPullToRefreshPinnedSectionListViewFragment;
import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.article.NewsContainerJson;
import com.open.baidu.finance.json.mystock.StockJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;

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
implements OnRefreshListener<ScrollView>,OnClickListener{
	private TextView txt_title,txt_time,txt_news;
	private PullToRefreshScrollView mPullToRefreshScrollView;
	private FrameLayout layout_more;
	//底部
	private ImageView img_msg,img_share,img_love,img_top;
	private TextView txt_msg;
	
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
		img_msg = (ImageView) view.findViewById(R.id.img_msg);
		img_share = (ImageView) view.findViewById(R.id.img_share);
		img_love = (ImageView) view.findViewById(R.id.img_love);
		img_top = (ImageView) view.findViewById(R.id.img_top);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
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
		img_msg.setOnClickListener(this);
		txt_msg.setOnClickListener(this);
		img_share.setOnClickListener(this);
		img_top.setOnClickListener(this);
		img_love.setOnClickListener(this);
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

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_msg:
		case R.id.txt_msg:
			//留言
			break;
		case R.id.img_share:
			//分享
			break;
		case R.id.img_love:
			//收藏
			usercollect(UrlUtils.USERCOLLECT,url.replace(UrlUtils.ARTICLE_URL, ""));
			break;
		case R.id.img_top:
			//置顶
			mPullToRefreshScrollView.getRefreshableView().scrollTo(0, 0);
			break;
		default:
			break;
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
	}
	
	public void usercollect(final String href,final String uid) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent",UrlUtils.userAgent);
		params.put("Referer",UrlUtils.ARTICLE_URL+uid);
		params.put("Cookie", UrlUtils.COOKIE);
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href+uid,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href+uid);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					CommonDataJson result = gson.fromJson(response.toString(), CommonDataJson.class);
					Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, NewsContainerPullScrollFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
	
	/**
	 * 设置字体大小
	 */
	public void setLargeSize(boolean largeSize){
		if(largeSize){
			txt_news.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		}else{
			txt_news.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		}
	}
}
