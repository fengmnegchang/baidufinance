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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.market.PlateStockPinnedSectionListAdapter;
import com.open.baidu.finance.bean.market.MarketShSzBean;
import com.open.baidu.finance.bean.market.PlateBean;
import com.open.baidu.finance.bean.market.PlateStockBean;
import com.open.baidu.finance.json.market.PlateStockJson;
import com.open.baidu.finance.utils.ComparatorPlateStockRatioType;
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
public class PlateStockPullToRefreshPinnedSectionListViewFragment extends BaseV4Fragment<PlateStockJson,PlateStockPullToRefreshPinnedSectionListViewFragment>
implements OnRefreshListener<ListView>{
	public PullToRefreshPinnedSectionListView mPullToRefreshPinnedSectionListView;
	public PlateStockPinnedSectionListAdapter mPlateStockPinnedSectionListAdapter;
	private List<PlateStockBean> list = new ArrayList<PlateStockBean>();
	public List<PlateStockBean> temptlist = new ArrayList<PlateStockBean>();
	private String plateName;
	
	public static PlateStockPullToRefreshPinnedSectionListViewFragment newInstance(String url, String plateName,boolean isVisibleToUser) {
		PlateStockPullToRefreshPinnedSectionListViewFragment fragment = new PlateStockPullToRefreshPinnedSectionListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.plateName = plateName;
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
		PlateStockBean bean = new PlateStockBean();
		bean.setViewType(1);
		list.add(bean);
		mPlateStockPinnedSectionListAdapter = new PlateStockPinnedSectionListAdapter(getActivity(),weakReferenceHandler, list);
		mPullToRefreshPinnedSectionListView.setAdapter(mPlateStockPinnedSectionListAdapter);
		mPullToRefreshPinnedSectionListView.setMode(Mode.BOTH);
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
			url = url.replace("page="+(pageNo-1), "page="+pageNo);
			if("A+H".equals(plateName)){
				ah(url);
			}else{
				if(url.contains("getUSList") || url.contains("getGlobalIndex")){
					ah(url);
				}else{
					volleyJson(url);
				}
			}
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
				PlateStockBean bean = list.get(0);
				bean.setNetRatioType(type);
				list.clear();
				
				list.add(bean);
				list.addAll(temptlist);
				
				mPlateStockPinnedSectionListAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case -1:
			//降序
		case 1:
			//升序
			try {
				PlateStockBean bean = list.get(0);
				bean.setNetRatioType(type);
				
				list.clear();
				list.addAll(temptlist);
				ComparatorPlateStockRatioType comparator = new ComparatorPlateStockRatioType(type);
				Collections.sort(list, comparator);
				
				list.add(0,bean);
				mPlateStockPinnedSectionListAdapter.notifyDataSetChanged();
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
					for (int i = 0; i < result.getList().size(); i++) {
						PlateStockBean bean = result.getList().get(i);
						if(bean.getLasttrade()!=0){
							bean.setTrade(bean.getLasttrade());
						}
						if("ADR".equals(plateName)){
							bean.setName(bean.getChname());
							bean.setTrade(bean.getLast());
							bean.setChangepercent(bean.getPchg());
						}
					}
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, PlateStockPullToRefreshPinnedSectionListViewFragment.this){
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
	public void onCallback(PlateStockJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null){
			try {
				if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_START) {
					list.clear();
					temptlist.clear();
					PlateStockBean bean = new PlateStockBean();
					bean.setViewType(1);
					list.add(bean);
					list.addAll(result.getList());
					temptlist.addAll(result.getList());
				}else{
					list.addAll(result.getList());
					temptlist.addAll(result.getList());
				}
				mPlateStockPinnedSectionListAdapter.notifyDataSetChanged();
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
	
	
	public void ah(final String href) {
		// final Map<String, String> headers = new HashMap<String, String>();
		// headers.put("Content-Type", "gbk");
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				// System.out.println("response=" + response.toString());
				// {symbol:"sz300409",code:"300409",name:"道氏技术",trade:"46.800",pricechange:"2.500",changepercent:"5.643",buy:"46.800",sell:"46.850",settlement:"44.300",open:"44.310",high:"47.550",low:"44.310",volume:5196120,amount:240773012,ticktime:"15:25:03",per:99.574,pb:7.98,mktcap:1006200,nmc:518511.82968,turnoverratio:4.68993}
				try {
					Pattern p = Pattern.compile("([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
					Matcher matcher = p.matcher(response);
					while (matcher.find()) {
						String s = matcher.group().replace(":", "-");
						response = response.replace(matcher.group(), s);
					}
					response = response.replace("{", "{\"").replace(",", ",\"").replace(":", "\":").replace("},\"{", "},{");
					response = "{\"slist\":" + response + "}";
					System.out.println("response=" + response.toString());
					Gson gson = new Gson();
					MarketShSzBean mmbean = gson.fromJson(response, MarketShSzBean.class);
					
					StringBuilder codebuffer = new StringBuilder();
					codebuffer.append(UrlUtils.SINA_LIST);
					int size = mmbean.getSlist().size();
					for (int i = 0; i < size; i++) {
						if(url.contains("getUSList")){
							codebuffer.append("gb_" + mmbean.getSlist().get(i).getSymbol().toLowerCase() + ",");
						}else if(url.contains("getGlobalIndex")){
							codebuffer.append("b_" + mmbean.getSlist().get(i).getSymbol().toUpperCase() + ",");
						}else {
							codebuffer.append(mmbean.getSlist().get(i).getA() + ","+"hk"+mmbean.getSlist().get(i).getH()+",");
						}
					}
					String href = codebuffer.toString().substring(0, codebuffer.toString().length() - 1);
					getStockList(href);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, PlateStockPullToRefreshPinnedSectionListViewFragment.this) {
			// @Override
			// public Map<String, String> getHeaders() throws AuthFailureError {
			// return headers;
			// }

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
					PlateStockJson result = new PlateStockJson();
					List<PlateStockBean> plist = new ArrayList<PlateStockBean>();
					PlateStockBean bean;
					String[] codes = null;
					
					if (response.contains("var hq_str_b_")) {
						codes = response.split("var hq_str_b_");
						////var hq_str_b_AS30="澳交所普通股指数 ,6030.32,28.17,0.47,2:34,14:34:00";
						for (int i = 1; i < codes.length; i++) {
							bean = new PlateStockBean();
							try {
								String c = codes[i];
								String stockCode = c.split("=")[0];
								bean.setSymbol(stockCode);
								String other = c.split("=")[1].replace(";", "").replace("\"", "");
								
								//AS30="澳交所普通股指数 ,6030.32,28.17,0.47,2:34,14:34:00";
								bean.setName(other.split(",")[0]);
								bean.setTrade(Double.parseDouble(other.split(",")[1]));
								bean.setPricechange(other.split(",")[2]);
								bean.setChangepercent(Double.parseDouble(other.split(",")[3]));
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							plist.add(bean);
						}
					}else  if (response.contains("var hq_str_")) {
						codes = response.split("var hq_str_");
						for (int i = 1; i < codes.length; i++) {
							//var hq_str_sh600011="华能国际,6.740,6.740,6.670,6.750,6.650,6.660,6.680,6994301,46801632.000,147800,6.660,190199,6.650,68300,6.640,37000,6.630,37300,6.620,4700,6.680,26200,6.690,69400,6.700,78300,6.710,84500,6.720,2017-11-03,15:00:00,00";
							//var hq_str_hk00902="HUANENG POWER,华能国际电力股份,5.190,5.200,5.250,5.170,5.190,-0.010,-0.192,5.190,5.200,145981304,28033643,324.375,5.588,6.250,4.600,2017/11/03,14:57";
							bean = new PlateStockBean();
							try {
								if(i%2==1){
									String c = codes[i];
									String stockCode = c.split("=")[0];
									bean.setSymbol(stockCode);
									String other = c.split("=")[1].replace(";", "").replace("\"", "");
									bean.setName(other.split(",")[0]);
									bean.setTrade(Double.parseDouble(other.split(",")[1]));
//									bean.setPricechange(other.split(",")[4]);
//									bean.setChangepercent(Double.parseDouble(other.split(",")[2]));
								}else{
									String c = codes[i];
									String stockCode = c.split("=")[0];
									bean.setSymbol(stockCode);
									String other = c.split("=")[1].replace(";", "").replace("\"", "");
									bean.setName(other.split(",")[1]);
									bean.setTrade(Double.parseDouble(other.split(",")[2]));
									bean.setPricechange(other.split(",")[7]);
									bean.setChangepercent(Double.parseDouble(other.split(",")[8]));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							plist.add(bean);
						}
					}else if (response.contains("var hq_str_gb_")) {
						codes = response.split("var hq_str_gb_");
						for (int i = 1; i < codes.length; i++) {
							// var
							// hq_str_gb_dji="道琼斯,23516.2598,0.35,2017-11-03 05:29:01,81.2500,23463.2402,23531.3809,23350.9805,23517.7109,17883.5605,0,28940475,0,0.00,0.0,0.00,0.00,0.00,0.00,0,0.00,0.0000,0.00,0.00,,Nov 02 05:28PM EDT,23435.0098,0.00";
							// var
							// hq_str_gb_ixic="纳斯达克,6714.9429,-0.02,2017-11-03 05:40:01,-1.5903,6709.3906,6719.9695,6677.5475,6759.6602,5034.4102,1966526456,1733257097,0,0.00,--,0.00,0.00,0.00,0.00,0,0.00,0.0000,0.00,0.00,,Nov 02 05:16PM EDT,6716.5332,0.00";
							// var
							// hq_str_gb_inx="标普指数,2579.8501,0.02,2017-11-03 05:29:01,0.4900,2579.4600,2581.1101,2566.1699,2588.3999,2083.7900,0,330562995,0,0.00,0.0,0.00,0.00,0.00,0.00,0,0.00,0.0000,0.00,0.00,,Nov 02 05:28PM EDT,2579.3601,0.00";
							bean = new PlateStockBean();
							try {
								String c = codes[i];
								String stockCode = c.split("=")[0];
								bean.setSymbol(stockCode);
								String other = c.split("=")[1].replace(";", "").replace("\"", "");

								// ati=阿利根尼,25.6700,2.19,2017-11-03
								// 08:16:52,0.5500,26.0000,26.5900,25.5600,26.5900,13.2900,2057988,1962884,2794949600,-5.97,--,0.00,0.00,0.00,0.00,108880000,114.00,25.6000,-0.27,-0.07,Nov
								// 02 08:00PM EDT,Nov 02 04:02PM
								// EDT,25.1200,13977.00
								bean.setName(other.split(",")[0]);
								bean.setTrade(Double.parseDouble(other.split(",")[1]));
								bean.setPricechange(other.split(",")[4]);
								bean.setChangepercent(Double.parseDouble(other.split(",")[2]));
								// bean.setVolume(Long.parseLong(other.split(",")[10]));
								// bean.setVolumeMoney(Long.parseLong(other.split(",")[5].replace("\"",
								// "")));
							} catch (Exception e) {
								e.printStackTrace();
							}
							plist.add(bean);
						}
					}  
					result.setList(plist);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, PlateStockPullToRefreshPinnedSectionListViewFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
}
