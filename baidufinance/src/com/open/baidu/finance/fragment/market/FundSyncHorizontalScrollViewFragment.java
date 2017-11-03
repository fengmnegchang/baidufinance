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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView;
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
import com.open.baidu.finance.json.market.PlateStockJson;
import com.open.baidu.finance.utils.ComparatorFundRatioType;
import com.open.baidu.finance.utils.UrlUtils;
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
	private String fundName;

	public static FundSyncHorizontalScrollViewFragment newInstance(String url, String fundName,boolean isVisibleToUser) {
		FundSyncHorizontalScrollViewFragment fragment = new FundSyncHorizontalScrollViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.fundName = fundName;
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
			if(fundName.equals("封闭式基金") || "ETF基金行情".equals(fundName) || "LOF基金行情".equals(fundName)){
				fund(url);
			}else if("货币型基金".equals(fundName)){
				ah(url);
			}
			else{
				volleyJson(url);
			}
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
					if("基金预测净值".equals(fundName)){
						for(FundBean bean:result.getList()){
							bean.setDate(bean.getPre_date());
							bean.setDwjz(bean.getPre_nav());
							bean.setJzzz(bean.getAccu_nav());
						}
					}
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
	
	/**
	 * 封闭式基金
	 */
	public void fund(final String href) {
//		final Map<String, String> headers  = new HashMap<String, String>();
//		headers.put("Content-Type", "gbk");
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
//				System.out.println("response=" + response.toString());
				 // {symbol:"sz300409",code:"300409",name:"道氏技术",trade:"46.800",pricechange:"2.500",changepercent:"5.643",buy:"46.800",sell:"46.850",settlement:"44.300",open:"44.310",high:"47.550",low:"44.310",volume:5196120,amount:240773012,ticktime:"15:25:03",per:99.574,pb:7.98,mktcap:1006200,nmc:518511.82968,turnoverratio:4.68993}
				try {
					PlateStockJson result = new PlateStockJson();
					Pattern p = Pattern.compile("([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
					Matcher matcher = p.matcher(response);
					while(matcher.find()){
						String s = matcher.group().replace(":", "-");
						response = response.replace(matcher.group(), s);
					}
					response = response.replace("{", "{\"").replace(",", ",\"").replace(":", "\":").replace("},\"{", "},{");
					response = "{\"list\":"+response+"}";
					System.out.println("response=" + response.toString());
					Gson gson = new Gson();
					result = gson.fromJson(response, PlateStockJson.class);
					
					
					FundJson mFundJson = new FundJson();
					List<FundBean> slist = new ArrayList<FundBean>();
					FundBean fundBean;
					for(int i=0;i<result.getList().size();i++){
						fundBean = new FundBean();
						fundBean.setSymbol(result.getList().get(i).getCode());
						fundBean.setName(result.getList().get(i).getName());
						fundBean.setDate(result.getList().get(i).getTicktime());
						fundBean.setDwjz(result.getList().get(i).getTrade());
						fundBean.setJzzz(result.getList().get(i).getChangepercent());
						fundBean.setJjgm(result.getList().get(i).getAmount());
						fundBean.setZrjz(Double.parseDouble(result.getList().get(i).getSettlement()));
						fundBean.setLjdwjz(result.getList().get(i).getVolume());
						slist.add(fundBean);
					}
					mFundJson.setList(slist);
					onCallback(mFundJson);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, FundSyncHorizontalScrollViewFragment.this){
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
	
	/**
	 * 货币基金
	 */
	public void ah(final String href) {
//		final Map<String, String> headers  = new HashMap<String, String>();
//		headers.put("Content-Type", "gbk");
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
//				System.out.println("response=" + response.toString());
				 // {symbol:"sz300409",code:"300409",name:"道氏技术",trade:"46.800",pricechange:"2.500",changepercent:"5.643",buy:"46.800",sell:"46.850",settlement:"44.300",open:"44.310",high:"47.550",low:"44.310",volume:5196120,amount:240773012,ticktime:"15:25:03",per:99.574,pb:7.98,mktcap:1006200,nmc:518511.82968,turnoverratio:4.68993}
				try {
					PlateStockJson result = new PlateStockJson();
					Pattern p = Pattern.compile("([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
					Matcher matcher = p.matcher(response);
					while(matcher.find()){
						String s = matcher.group().replace(":", "-");
						response = response.replace(matcher.group(), s);
					}
					response = response.replace("{", "{\"").replace(",", ",\"").replace(":", "\":").replace("},\"{", "},{");
					response = "{\"list\":"+response+"}";
					System.out.println("response=" + response.toString());
					Gson gson = new Gson();
					result = gson.fromJson(response, PlateStockJson.class);
					
					StringBuilder codebuffer = new StringBuilder();
					codebuffer.append(UrlUtils.SINA_LIST);
					int size = result.getList().size();
					for(int i=0;i<size;i++){
						codebuffer.append("f_" + result.getList().get(i).getSymbol() + ",");
					}
					String href = codebuffer.toString().substring(0, codebuffer.toString().length() - 1);
					getStockList(href);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, FundSyncHorizontalScrollViewFragment.this){
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

	
	public void getStockList(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					FundJson mFundJson = new FundJson();
					List<FundBean> plist = new ArrayList<FundBean>();
					FundBean fundbean;
					String[] codes = null;
					if (response.contains("var hq_str_f_")) {
						codes = response.split("var hq_str_f_");
						for (int i = 1; i < codes.length; i++) {
							fundbean = new FundBean();
							//var hq_str_f_519508="万家货币A,0.9943,3.722,,2017-11-02,4.70888";
							try {
								String c = codes[i];
								String stockCode = c.split("=")[0];
								fundbean.setSymbol(stockCode);
								String other = c.split("=")[1].replace(";", "").replace("\"", "");

								//519508="万家货币A,0.9943,3.722,,2017-11-02,4.70888";
								fundbean.setName(other.split(",")[0]);
								fundbean.setDate(other.split(",")[4]);
								fundbean.setDwjz(Double.parseDouble(other.split(",")[1]));
								fundbean.setJzzz(Double.parseDouble(other.split(",")[2]));
								fundbean.setJjgm(Double.parseDouble(other.split(",")[5]));
							} catch (Exception e) {
								e.printStackTrace();
							}
							plist.add(fundbean);
						}
					}
					mFundJson.setList(plist);
					onCallback(mFundJson);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, FundSyncHorizontalScrollViewFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
	
	
}
