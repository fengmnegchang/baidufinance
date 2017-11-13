/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-3下午1:58:23
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.market;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedHeaderExpandableListView;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.market.MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter;
import com.open.baidu.finance.bean.market.MarketShSzBean;
import com.open.baidu.finance.bean.market.PlateBean;
import com.open.baidu.finance.bean.market.PlateStockBean;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-3下午1:58:23
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class USPinnedHeaderExpandableListViewFragment extends SHHongKongPinnedHeaderExpandableListViewFragment {
	public static USPinnedHeaderExpandableListViewFragment newInstance(String url, boolean isVisibleToUser) {
		USPinnedHeaderExpandableListViewFragment fragment = new USPinnedHeaderExpandableListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_market_sh_sz_pulltorefresh_pinnedheader_expandable_listview, container, false);
		mPullToRefreshExpandableListView = (PullToRefreshPinnedHeaderExpandableListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mystock_headview, null);
		// stickyLayout = (StickyLayout)view.findViewById(R.id.sticky_layout);
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
		mPullToRefreshExpandableListView.getRefreshableView().addHeaderView(headview);
		IndexShSZFragment fragment = IndexShSZFragment.newInstance(UrlUtils.INDEX_US, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_mystock_headview, fragment).commit();

		mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter = new MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter(getActivity(), list);
		mPullToRefreshExpandableListView.getRefreshableView().setAdapter(mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter);
		mPullToRefreshExpandableListView.setMode(Mode.PULL_FROM_START);
		mPullToRefreshExpandableListView.getRefreshableView().setGroupIndicator(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message
	 * )
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			list.clear();
			for (int i = 50; i <= 56; i++) {
				MarketShSzBean mmbean = new MarketShSzBean();
				mmbean.setGroupType(i);
				mmbean.setPlist(new ArrayList<PlateBean>());
				mmbean.setSlist(new ArrayList<PlateStockBean>());
				list.add(mmbean);
			}
			 stock(UrlUtils.GETUSLIST_CHINA_US, 50, "中国概念股",50);
			 stock(UrlUtils.GETUSLIST_TECT_US, 51, "科技类",50);
			 stock(UrlUtils.GETUSLIST_FINANCE_US, 52, "金融类",50);
			 stock(UrlUtils.GETUSLIST_SALES_US, 53, "制造零售类",50);
			 stock(UrlUtils.GETUSLIST_AUTO_US, 54, "汽车能源类",50);
			 stock(UrlUtils.GETUSLIST_MEIDA_US, 55, "媒体类",50);
			 stock(UrlUtils.GETUSLIST_YYSP_US, 56, "医药食品类",50);
			break;
		}
	}

	@Override
	public void stock(final String href, final int type, final String groupName,final int num) {
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
					List<PlateStockBean> slist = new ArrayList<PlateStockBean>();
					int size = mmbean.getSlist().size();
					if (mmbean.getSlist().size() > 10) {
						size = 10;
					}
					for (int i = 0; i < size; i++) {
						slist.add(mmbean.getSlist().get(i));
						codebuffer.append("gb_" + mmbean.getSlist().get(i).getSymbol().toLowerCase() + ",");
					}

					list.get(type - num).getSlist().clear();
					list.get(type - num).setSlist(slist);
					list.get(type - num).setGroupName(groupName);
					list.get(type - num).setUrl(href);
					list.get(type - num).setPlist(new ArrayList<PlateBean>());

					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(USPinnedHeaderExpandableListViewFragment.this);
					mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();

					String href2 = codebuffer.toString().substring(0, codebuffer.toString().length() - 1);
					getStockList(href2, type,num);
					
					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(href2);
					
					openbean.setDownloadurl("");
					openbean.setImgsrc("");
					openbean.setType(pageNo);
					openbean.setTypename(pageNo+"");
					openbean.setUrl(href+type+"US_P");
					OpenDBService.insert(getActivity(), openbean);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),href+type+"US_P", pageNo+"");
				getStockList(dblist.get(0).getTitle(), type,num);
			}
		}) {
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

	public void getStockList(final String href, final int type,final int num) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					List<PlateStockBean> plist = new ArrayList<PlateStockBean>();
					PlateStockBean bean;
					String[] codes = null;
					if (response.contains("var hq_str_gb_")) {
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
								bean.setSymbol("us"+stockCode.toUpperCase());
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

						list.get(type - num).getSlist().clear();
						list.get(type - num).setSlist(plist);

						((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(USPinnedHeaderExpandableListViewFragment.this);
						mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
						mPullToRefreshExpandableListView.onRefreshComplete();
						expandAll();
						
						Gson gson = new Gson();
						OpenDBBean openbean = new OpenDBBean();
						openbean.setTitle(gson.toJson(list.get(type - num)));
						
						openbean.setDownloadurl("");
						openbean.setImgsrc("");
						openbean.setType(pageNo);
						openbean.setTypename(pageNo+"");
						openbean.setUrl(href+type);
						OpenDBService.insert(getActivity(), openbean);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),href+type, pageNo+"");
				Gson gson = new Gson();
				MarketShSzBean mMarketShSzBean = gson.fromJson(dblist.get(0).getTitle(), MarketShSzBean.class);
				list.set(type - num,mMarketShSzBean); 
				
				((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(USPinnedHeaderExpandableListViewFragment.this);
				mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
				mPullToRefreshExpandableListView.onRefreshComplete();
				expandAll();
			}
		});
		requestQueue.add(jsonObjectRequest);
	}
}
