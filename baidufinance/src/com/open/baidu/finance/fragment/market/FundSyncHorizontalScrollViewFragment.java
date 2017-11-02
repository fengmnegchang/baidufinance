/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2上午10:54:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.market;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.market.FundLeftScrollAdapter;
import com.open.baidu.finance.adapter.market.FundRightScrollAdapter;
import com.open.baidu.finance.bean.market.FundBean;
import com.open.baidu.finance.json.market.FundJson;
import com.open.baidu.finance.utils.ComparatorFundRatioType;
import com.open.baidu.finance.widget.SyncHorizontalScrollView;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-2上午10:54:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class FundSyncHorizontalScrollViewFragment extends BaseV4Fragment<FundJson, FundSyncHorizontalScrollViewFragment>
implements OnRefreshListener<ScrollView>
{
	private LinearLayout leftContainerView;
	private ListView leftListView;
	private List<FundBean> list = new ArrayList<FundBean>();
	private List<FundBean> temptlist = new ArrayList<FundBean>();
	private LinearLayout rightContainerView;
	private ListView rightListView;
	private SyncHorizontalScrollView titleHorsv;
	private SyncHorizontalScrollView contentHorsv;
	private FundLeftScrollAdapter mFundLeftScrollAdapter;
	private FundRightScrollAdapter mFundRightScrollAdapter;
	private PullToRefreshScrollView mPullToRefreshLinearLayout;
	private int type;
	private TextView txt_jzzz;

	public static FundSyncHorizontalScrollViewFragment newInstance(String url, boolean isVisibleToUser) {
		FundSyncHorizontalScrollViewFragment fragment = new FundSyncHorizontalScrollViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fund_sycn_scroll, container, false);
		leftContainerView = (LinearLayout) view.findViewById(R.id.left_container);
		leftListView = (ListView) view.findViewById(R.id.left_container_listview);
		rightContainerView = (LinearLayout) view.findViewById(R.id.right_container);
		rightListView = (ListView) view.findViewById(R.id.right_container_listview);
		titleHorsv = (SyncHorizontalScrollView) view.findViewById(R.id.title_horsv);
		contentHorsv = (SyncHorizontalScrollView) view.findViewById(R.id.content_horsv);
		mPullToRefreshLinearLayout = (PullToRefreshScrollView) view.findViewById(R.id.pulltorefreshlinearlayout);
		txt_jzzz = (TextView) view.findViewById(R.id.txt_jzzz);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshLinearLayout.setMode(Mode.BOTH);
		// 设置两个水平控件的联动
		titleHorsv.setScrollView(contentHorsv);
		contentHorsv.setScrollView(titleHorsv);

		// 添加左边内容数据
//		leftContainerView.setBackgroundColor(Color.YELLOW);

		mFundLeftScrollAdapter = new FundLeftScrollAdapter(getActivity(), list);
		leftListView.setAdapter(mFundLeftScrollAdapter);

		setListViewHeightBasedOnChildren(leftListView);
		// 添加右边内容数据
//		rightContainerView.setBackgroundColor(Color.GRAY);

		mFundRightScrollAdapter = new FundRightScrollAdapter(getActivity(), list);
		rightListView.setAdapter(mFundRightScrollAdapter);
		setListViewHeightBasedOnChildren(rightListView);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullToRefreshLinearLayout.setOnRefreshListener(this);
		txt_jzzz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(type==0){
					type = -1;
					txt_jzzz.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.sort_down), null);
				}else if(type==-1){
					type = 1;
					txt_jzzz.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.sort_up), null);
				}else{
					type = 0;
					txt_jzzz.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.portfolio_market), null);
				}
				netRatioType(type);
			}
		});
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			url = url.replace("page="+(pageNo-1), "page="+pageNo);
			volleyJson(url);
			break;
		}
	}
	
	/***
	 * 涨跌幅
	 */
	public void netRatioType(int type){
		switch (type) {
		case 0:
			try {
				list.clear();
				list.addAll(temptlist);
				mFundLeftScrollAdapter.notifyDataSetChanged();
				mFundRightScrollAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case -1:
			//降序
		case 1:
			//升序
			try {
				
				list.clear();
				list.addAll(temptlist);
				ComparatorFundRatioType comparator = new ComparatorFundRatioType(type);
				Collections.sort(list, comparator);
				
				mFundLeftScrollAdapter.notifyDataSetChanged();
				mFundRightScrollAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(FundJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null && result.getList()!=null){
			if (mPullToRefreshLinearLayout.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				temptlist.clear();
				list.addAll(result.getList());
				temptlist.addAll(result.getList());
			}else{
				list.addAll(result.getList());
				temptlist.addAll(result.getList());
			}
			mFundLeftScrollAdapter.notifyDataSetChanged();
			mFundRightScrollAdapter.notifyDataSetChanged();
			setListViewHeightBasedOnChildren(leftListView);
			setListViewHeightBasedOnChildren(rightListView);
			mPullToRefreshLinearLayout.onRefreshComplete();
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
	
	@Override
	public void volleyJson(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				try {
					FundJson result = new FundJson();
					response = response.replace("{", "{\"").replace(",", ",\"").replace(":", "\":").replace("},\"{", "},{");
					response = "{\"list\":"+response+"}";
					System.out.println("response=" + response.toString());
					Gson gson = new Gson();
					result = gson.fromJson(response, FundJson.class);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, FundSyncHorizontalScrollViewFragment.this){
		    @Override
		    protected Response<String> parseNetworkResponse(NetworkResponse response) {
		        String parsed;
		        try {
		            parsed = new String(response.data, "gbk");
		        } catch (UnsupportedEncodingException e) {
		            parsed = new String(response.data);
		        }
		        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
		    }
		};
		requestQueue.add(jsonObjectRequest);
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
		if (mPullToRefreshLinearLayout.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshLinearLayout.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		}
	}
	
	
	
}
