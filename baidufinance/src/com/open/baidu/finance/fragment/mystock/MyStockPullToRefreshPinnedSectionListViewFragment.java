/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12上午11:36:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.mystock.MyStockAdapter;
import com.open.baidu.finance.bean.mystock.StockBean;
import com.open.baidu.finance.json.mystock.StockJson;
import com.open.baidu.finance.utils.ComparatorCloseType;
import com.open.baidu.finance.utils.ComparatorNetRatioType;
import com.open.baidu.finance.utils.ComparatorNetValueType;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12上午11:36:45
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MyStockPullToRefreshPinnedSectionListViewFragment extends BaseV4Fragment<StockJson, MyStockPullToRefreshPinnedSectionListViewFragment> 
implements OnRefreshListener<ListView>{
	public PullToRefreshPinnedSectionListView mPullToRefreshPinnedSectionListView;
	public MyStockAdapter mMyStockAdapter;
	public List<StockBean> list = new ArrayList<StockBean>();
	public List<StockBean> temptlist = new ArrayList<StockBean>();
	public View headview;

	public static MyStockPullToRefreshPinnedSectionListViewFragment newInstance(String url, boolean isVisibleToUser) {
		MyStockPullToRefreshPinnedSectionListViewFragment fragment = new MyStockPullToRefreshPinnedSectionListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mystock_pulltorefresh_pinnedsection_listview, container, false);
		mPullToRefreshPinnedSectionListView = (PullToRefreshPinnedSectionListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mystock_headview, null);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4MVPPFragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshPinnedSectionListView.getRefreshableView().addHeaderView(headview);
		IndexMarkHeadFragment fragment = IndexMarkHeadFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_mystock_headview, fragment).commit();
		
		StockBean bean = new StockBean();
		bean.setViewType(1);
		list.add(bean);
		mMyStockAdapter = new MyStockAdapter(getActivity(),weakReferenceHandler, list);
		mPullToRefreshPinnedSectionListView.setAdapter(mMyStockAdapter);
		mPullToRefreshPinnedSectionListView.setMode(Mode.PULL_FROM_START);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullToRefreshPinnedSectionListView.setOnRefreshListener(this);
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
			volleyJson(url);
			break;
		case 1:
			//最新价
			closeType(msg.arg1);
			break;
		case 2:
			//涨跌幅
			netRatioType(msg.arg1);
			break;
		case 0:
			allType(msg.arg1);
			break;
		case 4:
			//涨跌额
			netValueType(msg.arg1);
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
	
	@Override
	public void volleyJson(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", "BAIDUID=CF6C53AD63064C72CDA6A1CF7788EC1C:FG=1; BIDUPSID=CF6C53AD63064C72CDA6A1CF7788EC1C; PSTM=1504837961; MCITY=-289%3A; BDUSS=0dkR0YyclhzQTdsdTBnV09mRkFtYnRRYWdUM3BXV2E5d204RUVLeklHdnFxZ0phTVFBQUFBJCQAAAAAAAAAAAEAAAD5apgiZmVuZ3hpYW4wMzgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOod21nqHdtZS; stoken=4f7caf550b1c8aa74fb4b459d6d670d428c747b61bcda1f865b03434c562a47e; locale=zh; PSINO=5; H_PS_PSSID=1434_21118_17001_20929; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_35d1e71f4c913c126b8703586f1d2307=1507532257,1507532966,1507533021,1507692673; Hm_lpvt_35d1e71f4c913c126b8703586f1d2307=1507778418");
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					StockJson result = gson.fromJson(response.toString(), StockJson.class);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, MyStockPullToRefreshPinnedSectionListViewFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(StockJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null){
			try {
				
				list.clear();
				temptlist.clear();
				StockBean bean = new StockBean();
				bean.setViewType(1);
				list.add(bean);
				list.addAll(result.getData().get(0).getData().getGroupList().get(0).getStock());
				temptlist.addAll(result.getData().get(0).getData().getGroupList().get(0).getStock());
				mMyStockAdapter.notifyDataSetChanged();
				
				IndexMarkHeadFragment fragment = (IndexMarkHeadFragment) getChildFragmentManager().findFragmentById(R.id.id_mystock_headview);
				if(fragment!=null){
					fragment.setIndexlist(temptlist);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mPullToRefreshPinnedSectionListView.onRefreshComplete();
	}
	
	/***
	 * 最新价
	 */
	public void closeType(int type){
		switch (type) {
		case 0:
			try {
				StockBean bean = list.get(0);
				bean.setClosetype(type);
				list.clear();
				
				list.add(bean);
				list.addAll(temptlist);
				
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case -1:
			//降序
		case 1:
			//升序
			try {
				StockBean bean = list.get(0);
				bean.setClosetype(type);
				
				list.clear();
				list.addAll(temptlist);
				ComparatorCloseType comparator = new ComparatorCloseType(type);
				Collections.sort(list, comparator);
				
				list.add(0,bean);
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
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
				StockBean bean = list.get(0);
				bean.setNetRatioType(type);
				list.clear();
				
				list.add(bean);
				list.addAll(temptlist);
				
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case -1:
			//降序
		case 1:
			//升序
			try {
				StockBean bean = list.get(0);
				bean.setNetRatioType(type);
				
				list.clear();
				list.addAll(temptlist);
				ComparatorNetRatioType comparator = new ComparatorNetRatioType(type);
				Collections.sort(list, comparator);
				
				list.add(0,bean);
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	
	
	/***
	 * 涨跌额
	 */
	public void netValueType(int type){
		switch (type) {
		case 0:
			try {
				StockBean bean = list.get(0);
				bean.setNetRatioType(type);
				list.clear();
				
				list.add(bean);
				list.addAll(temptlist);
				
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case -1:
			//降序
		case 1:
			//升序
			try {
				StockBean bean = list.get(0);
				bean.setNetRatioType(type);
				
				list.clear();
				list.addAll(temptlist);
				ComparatorNetValueType comparator = new ComparatorNetValueType(type);
				Collections.sort(list, comparator);
				
				list.add(0,bean);
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	
	public void allType(int type){
		switch (type) {
		case 0:
			try {
				StockBean bean = list.get(0);
				bean.setAlltype(type);
				list.clear();
				
				list.add(bean);
				list.addAll(temptlist);
				
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				StockBean bean = list.get(0);
				bean.setAlltype(type);
				
				list.clear();
				//去除非沪深股
				for(StockBean tbean :temptlist){
					if(tbean.getExchange().equalsIgnoreCase("sz") ||
							tbean.getExchange().equalsIgnoreCase("sh")){
						list.add(tbean);
					}
				}
				list.add(0,bean);
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			//港股
			try {
				StockBean bean = list.get(0);
				bean.setAlltype(type);
				
				list.clear();
				for(StockBean tbean :temptlist){
					if(tbean.getExchange().equalsIgnoreCase("hk")){
						list.add(tbean);
					}
				}
				list.add(0,bean);
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			//美股
			try {
				StockBean bean = list.get(0);
				bean.setAlltype(type);
				
				list.clear();
				for(StockBean tbean :temptlist){
					if(tbean.getExchange().equalsIgnoreCase("us")){
						list.add(tbean);
					}
				}
				list.add(0,bean);
				mMyStockAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	/* (non-Javadoc)
	 * @see com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener#onRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		}
	}

	/****
	 * 获取股票信息
	 * @return
	 */
	public List<StockBean> getList() {
		return list;
	}
	
	  
}
