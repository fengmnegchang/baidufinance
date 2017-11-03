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

import android.os.Message;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView;
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
public class SZHongKongPinnedHeaderExpandableListViewFragment extends SHHongKongPinnedHeaderExpandableListViewFragment{
	public static SZHongKongPinnedHeaderExpandableListViewFragment newInstance(String url, boolean isVisibleToUser) {
		SZHongKongPinnedHeaderExpandableListViewFragment fragment = new SZHongKongPinnedHeaderExpandableListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
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
			for (int i = 40; i <= 41; i++) {
				MarketShSzBean mmbean = new MarketShSzBean();
				mmbean.setGroupType(i);
				mmbean.setPlist(new ArrayList<PlateBean>());
				mmbean.setSlist(new ArrayList<PlateStockBean>());
				list.add(mmbean);
			}
			stock(UrlUtils.GETHQNODEDATA_SGT_SZ, 40, "深股通");
			stock(UrlUtils.GETHQNODEDATA_SGT_HK, 41, "港股通");
			break;
		}
	}
	
	@Override
	public void stock(final String href, final int type, final String groupName) {
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

					List<PlateStockBean> slist = new ArrayList<PlateStockBean>();
					int size = mmbean.getSlist().size();
					if (mmbean.getSlist().size() > 10) {
						size = 10;
					}
					for (int i = 0; i < size; i++) {
						if(type==41){
							mmbean.getSlist().get(i).setTrade(mmbean.getSlist().get(i).getLasttrade());
						}
						slist.add(mmbean.getSlist().get(i));
					}

					list.get(type-40).getSlist().clear();
					list.get(type-40).setSlist(slist);
					list.get(type-40).setGroupName(groupName);
					list.get(type-40).setUrl(href);
					list.get(type-40).setPlist(new ArrayList<PlateBean>());
					
					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView())
					.setOnHeaderUpdateListener(SZHongKongPinnedHeaderExpandableListViewFragment.this);
					mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, SZHongKongPinnedHeaderExpandableListViewFragment.this) {
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
}
