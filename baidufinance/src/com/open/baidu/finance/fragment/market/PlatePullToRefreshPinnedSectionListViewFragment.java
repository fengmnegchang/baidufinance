/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:43:21
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.market.PlateStockPullToRefreshPinnedSectionListViewFragmentActivity;
import com.open.baidu.finance.adapter.market.PlatePinnedSectionListAdapter;
import com.open.baidu.finance.bean.market.IndexBean;
import com.open.baidu.finance.bean.market.PlateBean;
import com.open.baidu.finance.json.market.PlateJson;
import com.open.baidu.finance.utils.ComparatorPlateRatioType;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31下午2:43:21
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class PlatePullToRefreshPinnedSectionListViewFragment extends BaseV4Fragment<PlateJson,PlatePullToRefreshPinnedSectionListViewFragment>
implements OnRefreshListener<ListView>,OnItemClickListener{
	public PullToRefreshPinnedSectionListView mPullToRefreshPinnedSectionListView;
	public PlatePinnedSectionListAdapter mPlatePinnedSectionListAdapter;
	private List<PlateBean> list = new ArrayList<PlateBean>();
	public List<PlateBean> temptlist = new ArrayList<PlateBean>();
	
	public static PlatePullToRefreshPinnedSectionListViewFragment newInstance(String url, boolean isVisibleToUser) {
		PlatePullToRefreshPinnedSectionListViewFragment fragment = new PlatePullToRefreshPinnedSectionListViewFragment();
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
		PlateBean bean = new PlateBean();
		bean.setViewType(1);
		list.add(bean);
		mPlatePinnedSectionListAdapter = new PlatePinnedSectionListAdapter(getActivity(),weakReferenceHandler, list);
		mPullToRefreshPinnedSectionListView.setAdapter(mPlatePinnedSectionListAdapter);
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
		mPullToRefreshPinnedSectionListView.setOnItemClickListener(this);
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
		case 2:
			//涨跌幅
			netRatioType(msg.arg1);
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
				PlateBean bean = list.get(0);
				bean.setNetRatioType(type);
				list.clear();
				
				list.add(bean);
				list.addAll(temptlist);
				
				mPlatePinnedSectionListAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case -1:
			//降序
		case 1:
			//升序
			try {
				PlateBean bean = list.get(0);
				bean.setNetRatioType(type);
				
				list.clear();
				list.addAll(temptlist);
				ComparatorPlateRatioType comparator = new ComparatorPlateRatioType(type);
				Collections.sort(list, comparator);
				
				list.add(0,bean);
				mPlatePinnedSectionListAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
	@Override
	public void volleyJson(final String href) {
//		final Map<String, String> headers  = new HashMap<String, String>();
//		headers.put("Content-Type", "gbk");
		
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				//"lycy,旅游餐饮,29,13.987777777778,0.24259259259259,1.7649277861608,172612988,1872491223,sh600358,9.946949602122,8.290,0.75,国旅联合"
				 // ,
				try {
					PlateJson result = new PlateJson();
					List<PlateBean> list = new ArrayList<PlateBean>();
					PlateBean bean;
					response = response.split("=")[1];
					String codes[] = response.replace("{", "").replace("}", "").replace("\"", "").split(":");
					for(int i=1;i<codes.length;i++){
						//lycy,旅游餐饮,29,14.067407407407,0.32222222222222,2.3442552274197,225050218,2639273585,sh600358,9.946949602122,8.290,0.75,国旅联合
						//板块 数量　 平均价　 涨跌额　 涨跌幅　 总成交量(手)　 总成交额(万元)　 领涨股 涨跌幅　 当前价　 涨跌额
						bean = new PlateBean();
						try {
							String other = codes[i];
							bean.setPlateSimpleCode(other.split(",")[0]);
							bean.setPlateName(other.split(",")[1]);
							bean.setNum(Integer.parseInt(other.split(",")[2]));
							bean.setAgvprice(Double.parseDouble(other.split(",")[3]));
							bean.setNetChange(Double.parseDouble(other.split(",")[4]));
							bean.setNetChangeRate(Double.parseDouble(other.split(",")[5]));
							bean.setTotalvolume(Long.parseLong(other.split(",")[6]));
							bean.setTotalvolumeMoney(Long.parseLong(other.split(",")[7]));
							bean.setStockCode(other.split(",")[8]);
							bean.setStockNetChnageRate(Double.parseDouble(other.split(",")[9]));
							bean.setStockClose(Double.parseDouble(other.split(",")[10]));
							bean.setStockNetChnage(Double.parseDouble(other.split(",")[11]));
							bean.setStockName(other.split(",")[12]);
						} catch (Exception e) {
							e.printStackTrace();
						}
						list.add(bean);
					}
					result.setList(list);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, PlatePullToRefreshPinnedSectionListViewFragment.this){
//		    @Override
//		    public Map<String, String> getHeaders() throws AuthFailureError {
//		        return headers;
//		    }
		    
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
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(PlateJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null){
			try {
				
				list.clear();
				temptlist.clear();
				PlateBean bean = new PlateBean();
				bean.setViewType(1);
				list.add(bean);
				list.addAll(result.getList());
				temptlist.addAll(result.getList());
				
				mPlatePinnedSectionListAdapter.notifyDataSetChanged();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mPullToRefreshPinnedSectionListView.onRefreshComplete();
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

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(id!=-1 && list!=null && list.get((int)id)!=null){
			PlateStockPullToRefreshPinnedSectionListViewFragmentActivity.startPlateStockPullToRefreshPinnedSectionListViewFragmentActivity(getActivity(), UrlUtils.GETHQNODEDATA_NODE+list.get((int)id).getPlateSimpleCode(),list.get((int)id).getPlateName());
		}
	}
}
