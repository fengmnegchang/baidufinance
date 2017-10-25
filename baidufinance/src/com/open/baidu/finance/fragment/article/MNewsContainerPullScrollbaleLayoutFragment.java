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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.widget.ScrollableHelper.ScrollableContainer;
import com.open.android.widget.ScrollableLayout;
import com.open.android.widget.ScrollableLayout.ScrollLayoutListener;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.article.MNewsCommentPullListFragmentActivity;
import com.open.baidu.finance.bean.MainTabBean;
import com.open.baidu.finance.fragment.news.MFootTagNewsPullListFragment;
import com.open.baidu.finance.fragment.news.MFootTodayNewsPullListFragment;
import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.article.NewsContainerJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;
import com.open.baidu.finance.widget.PullToRefreshScrollableLayout;
import com.open.indicator.TabPageIndicator;

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
public class MNewsContainerPullScrollbaleLayoutFragment extends BaseV4Fragment<NewsContainerJson, MNewsContainerPullScrollbaleLayoutFragment>
implements OnRefreshListener<ScrollableLayout>,OnClickListener,ScrollLayoutListener,OnPageChangeListener{
	private TextView txt_title,txt_time,txt_news;
	public PullToRefreshScrollableLayout mPullToRefreshScrollableLayout;
	//底部
	private ImageView img_msg,img_share,img_love,img_top;
	private TextView txt_msg;
	
	//viewpager
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public CommonFragmentPagerAdapter mRankPagerAdapter;
	public ArrayList<MainTabBean> clist = new ArrayList<MainTabBean>();
	
	public static MNewsContainerPullScrollbaleLayoutFragment newInstance(String url, boolean isVisibleToUser) {
		MNewsContainerPullScrollbaleLayoutFragment fragment = new MNewsContainerPullScrollbaleLayoutFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_container_pullscrollable_layout, container, false);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_time = (TextView) view.findViewById(R.id.txt_time);
		txt_news = (TextView) view.findViewById(R.id.txt_news);
		mPullToRefreshScrollableLayout = (PullToRefreshScrollableLayout) view.findViewById(R.id.pulltorefreshscrollablelayout);
		img_msg = (ImageView) view.findViewById(R.id.img_msg);
		img_share = (ImageView) view.findViewById(R.id.img_share);
		img_love = (ImageView) view.findViewById(R.id.img_love);
		img_top = (ImageView) view.findViewById(R.id.img_top);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshScrollableLayout.setMode(Mode.PULL_FROM_START);
		MainTabBean bean = new MainTabBean();
		bean.setHref(UrlUtils.M_GETTODAYNEWS);
		bean.setTitle("要闻");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.M_GETTAGNEWS_ECONOMY);
		bean.setTitle("宏观");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.M_GETTAGNEWS);
		bean.setTitle("行业");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.M_ORGANIZATION);
		bean.setTitle("机构");
		clist.add(bean);
		
		titleList.clear();

		Fragment fragment;
		for (int i=0;i< clist.size();i++) {
			bean = clist.get(i);
			titleList.add(bean.getTitle());
			if(i==0){
				fragment = MFootTodayNewsPullListFragment.newInstance(bean.getHref(),true);
			}else{
				fragment = MFootTagNewsPullListFragment.newInstance(bean.getHref(),false);
			}
			listRankFragment.add(fragment);
		}
		
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
		mPullToRefreshScrollableLayout.getRefreshableView().getHelper().setCurrentScrollableContainer((ScrollableContainer) listRankFragment.get(0));
		mPullToRefreshScrollableLayout.getRefreshableView().setScrollLayoutListener(this);

	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullToRefreshScrollableLayout.setOnRefreshListener(this);
		img_msg.setOnClickListener(this);
		txt_msg.setOnClickListener(this);
		img_share.setOnClickListener(this);
		img_top.setOnClickListener(this);
		img_love.setOnClickListener(this);
		indicator.setOnPageChangeListener(this);
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
		mPullToRefreshScrollableLayout.onRefreshComplete();
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
	public void onRefresh(PullToRefreshBase<ScrollableLayout> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshScrollableLayout.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshScrollableLayout.getCurrentMode() == Mode.PULL_FROM_END) {
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
			MNewsCommentPullListFragmentActivity.startMNewsCommentPullListFragmentActivity(getActivity(), url.replace(UrlUtils.ARTICLE_URL, ""));
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
			mPullToRefreshScrollableLayout.getRefreshableView().scrollTo(0, 0);
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
		}, MNewsContainerPullScrollbaleLayoutFragment.this);
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

	/* (non-Javadoc)
	 * @see com.open.android.widget.ScrollableLayout.ScrollLayoutListener#onScrollLayout(com.open.android.widget.ScrollableLayout, boolean, int)
	 */
	@Override
	public void onScrollLayout(ScrollableLayout view, boolean isSmoothTop, int scrollDistence) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		try {
			if (mPullToRefreshScrollableLayout.getRefreshableView() != null) {
				mPullToRefreshScrollableLayout.getRefreshableView().getHelper().setCurrentScrollableContainer(((ScrollableContainer) listRankFragment.get(position)));
			}
		} catch (Exception e) {
		}
	}
}