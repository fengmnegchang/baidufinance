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
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedHeaderExpandableListView;
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
public class USIndexPinnedHeaderExpandableListViewFragment extends SHHongKongPinnedHeaderExpandableListViewFragment {
	public static USIndexPinnedHeaderExpandableListViewFragment newInstance(String url, boolean isVisibleToUser) {
		USIndexPinnedHeaderExpandableListViewFragment fragment = new USIndexPinnedHeaderExpandableListViewFragment();
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
//		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mystock_headview, null);
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
//		mPullToRefreshExpandableListView.getRefreshableView().addHeaderView(headview);
//		IndexShSZFragment fragment = IndexShSZFragment.newInstance(UrlUtils.INDEX_US, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.id_mystock_headview, fragment).commit();

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
			for (int i = 60; i <= 64; i++) {
				MarketShSzBean mmbean = new MarketShSzBean();
				mmbean.setGroupType(i);
				mmbean.setPlist(new ArrayList<PlateBean>());
				mmbean.setSlist(new ArrayList<PlateStockBean>());
				list.add(mmbean);
			}
			 stock(UrlUtils.GETGLOBALINDEX_AMERICA_GLOBAL, 60, "美洲",60);
			 stock(UrlUtils.GETGLOBALINDEX_EURO_GLOBAL, 61, "欧洲",60);
			 stock(UrlUtils.GETGLOBALINDEX_ASIA_GLOBAL, 62, "亚洲",60);
			 stock(UrlUtils.GETGLOBALINDEX_AFRICA_GLOBAL, 63, "非洲",60);
			 stock(UrlUtils.GETGLOBALINDEX_OCEANIA_GLOBAL, 64, "大洋洲",60);
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
						codebuffer.append("b_" + mmbean.getSlist().get(i).getSymbol().toUpperCase() + ",");
					}

					list.get(type - num).getSlist().clear();
					list.get(type - num).setSlist(slist);
					list.get(type - num).setGroupName(groupName);
					list.get(type - num).setUrl(href);
					list.get(type - num).setPlist(new ArrayList<PlateBean>());

					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(USIndexPinnedHeaderExpandableListViewFragment.this);
					mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();

					String href = codebuffer.toString().substring(0, codebuffer.toString().length() - 1);
					getStockList(href, type,num);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, USIndexPinnedHeaderExpandableListViewFragment.this) {
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
	@Override
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
					if (response.contains("var hq_str_b_")) {
						codes = response.split("var hq_str_b_");
						for (int i = 1; i < codes.length; i++) {
							bean = new PlateStockBean();
							//var hq_str_b_AS30="澳交所普通股指数 ,6030.32,28.17,0.47,2:34,14:34:00";
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

						((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(USIndexPinnedHeaderExpandableListViewFragment.this);
						mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
						mPullToRefreshExpandableListView.onRefreshComplete();
						expandAll();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, USIndexPinnedHeaderExpandableListViewFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
}
