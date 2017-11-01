/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1上午10:07:18
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
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
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
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedHeaderExpandableListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.market.PlatePullToRefreshPinnedSectionListViewFragmentActivity;
import com.open.baidu.finance.activity.market.PlateStockPullToRefreshPinnedSectionListViewFragmentActivity;
import com.open.baidu.finance.adapter.market.MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter;
import com.open.baidu.finance.bean.market.MarketShSzBean;
import com.open.baidu.finance.bean.market.PlateBean;
import com.open.baidu.finance.bean.market.PlateStockBean;
import com.open.baidu.finance.json.market.MarketShSzJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-1上午10:07:18
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment extends BaseV4Fragment<MarketShSzJson, MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment> implements
		OnRefreshListener<ExpandableListView>, OnHeaderUpdateListener {
	//OnGiveUpTouchEventListener
	public PullToRefreshPinnedHeaderExpandableListView mPullToRefreshExpandableListView;
	public MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter;
	public List<MarketShSzBean> list = new ArrayList<MarketShSzBean>();
//	private StickyLayout stickyLayout;
	 private View headview;

	public static MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment newInstance(String url, boolean isVisibleToUser) {
		MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment fragment = new MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment();
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
//		stickyLayout = (StickyLayout)view.findViewById(R.id.sticky_layout);
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
		 mPullToRefreshExpandableListView.getRefreshableView().addHeaderView(headview);
		IndexShSZFragment fragment = IndexShSZFragment.newInstance(UrlUtils.INDEX_SH_SZ, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_mystock_headview, fragment).commit();

		mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter = new MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter(getActivity(), list);
		mPullToRefreshExpandableListView.getRefreshableView().setAdapter(mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter);
		mPullToRefreshExpandableListView.setMode(Mode.PULL_FROM_START);
		mPullToRefreshExpandableListView.getRefreshableView().setGroupIndicator(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullToRefreshExpandableListView.setOnRefreshListener(this);
//		stickyLayout.setOnGiveUpTouchEventListener(this);

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
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			list.clear();
			for (int i = 0; i <= 10; i++) {
				MarketShSzBean mmbean = new MarketShSzBean();
				mmbean.setGroupType(i);
				mmbean.setPlist(new ArrayList<PlateBean>());
				mmbean.setSlist(new ArrayList<PlateStockBean>());
				list.add(mmbean);
			}
			plate(UrlUtils.NEWSINAHY, 0, "新浪行业涨幅榜");
			plate(UrlUtils.SWHY, 1, "申万涨幅榜");
			plate(UrlUtils.NEWFLJK_CLASS, 2, "概念涨幅榜");
			plate(UrlUtils.NEWFLJK_AREA, 3, "地域涨幅榜");
			plate(UrlUtils.NEWFLJK_INDUSTRY, 4, "行业涨幅榜");
			stock(UrlUtils.GETHQNODEDATA_CHANGE_PERCENT_0, 5, "涨幅榜");
			stock(UrlUtils.GETHQNODEDATA_CHANGE_PERCENT_1, 6, "跌幅榜");
			stock(UrlUtils.GETHQNODEDATA_NEW_STOCK, 7, "次新股涨幅榜");
			stock(UrlUtils.GETHQNODEDATA_SHFXJS, 8, "风险警示");
			stock(UrlUtils.GETHQNODEDATA_TURNOVERRATIO, 9, "换手率榜");
			stock(UrlUtils.GETHQNODEDATA_AMOOUNT, 10, "成交额");
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener
	 * #onRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshExpandableListView.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshExpandableListView.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}

	public void plate(final String href, final int type, final String groupName) {
		// final Map<String, String> headers = new HashMap<String, String>();
		// headers.put("Content-Type", "gbk");
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				// "lycy,旅游餐饮,29,13.987777777778,0.24259259259259,1.7649277861608,172612988,1872491223,sh600358,9.946949602122,8.290,0.75,国旅联合"
				// ,
				try {
					List<PlateBean> sublist = new ArrayList<PlateBean>();
					PlateBean bean;
					response = response.split("=")[1];
					String codes[] = response.replace("{", "").replace("}", "").replace("\"", "").split(":");
					int j = 0;
					for (int i = 1; i < codes.length; i++) {
						// lycy,旅游餐饮,29,14.067407407407,0.32222222222222,2.3442552274197,225050218,2639273585,sh600358,9.946949602122,8.290,0.75,国旅联合
						// 板块 数量　 平均价　 涨跌额　 涨跌幅　 总成交量(手)　 总成交额(万元)　 领涨股 涨跌幅　
						// 当前价　 涨跌额
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
						sublist.add(bean);
						j++;
						if (j > 5) {
							break;
						}

					}
					MarketShSzBean mmbean = list.get(type);
					mmbean.setGroupName(groupName);
					mmbean.setPlist(sublist);
					mmbean.setUrl(href);
					mmbean.setSlist(new ArrayList<PlateStockBean>());

					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView())
							.setOnHeaderUpdateListener(MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment.this);
					mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment.this) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MarketShSzJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			try {
				mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mPullToRefreshExpandableListView.onRefreshComplete();
	}

	public void expandAll() {
		for (int i = 0; i < mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.getGroupCount(); i++) {
			mPullToRefreshExpandableListView.getRefreshableView().expandGroup(i);
		}
	}

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
					if (mmbean.getSlist().size() > 10) {
						for (int i = 0; i < 10; i++) {
							slist.add(mmbean.getSlist().get(i));
						}
					}

					list.get(type).getSlist().clear();
					list.get(type).setSlist(slist);
					list.get(type).setGroupName(groupName);
					list.get(type).setUrl(href);
					list.get(type).setPlist(new ArrayList<PlateBean>());
					mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, MarketShSzPullToRefreshPinnedHeaderExpandableListViewFragment.this) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView.
	 * OnHeaderUpdateListener#getPinnedHeader()
	 */
	@Override
	public View getPinnedHeader() {
		// TODO Auto-generated method stub
		View headerView = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.adapter_market_shsz_itemtype, null);
		return headerView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView.
	 * OnHeaderUpdateListener#updatePinnedHeader(android.view.View, int)
	 */
	@Override
	public void updatePinnedHeader(View headerView, int groupPosition) {
		// TODO Auto-generated method stub
		TextView txt_name = (TextView) headerView.findViewById(R.id.txt_name);
		RelativeLayout layout_group2 = (RelativeLayout) headerView.findViewById(R.id.layout_group2);
		TextView txt_all = (TextView) headerView.findViewById(R.id.txt_all);
		if(groupPosition!=-1){
			final MarketShSzBean mMarketShSzBean = (MarketShSzBean) mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.getGroup(groupPosition);
			txt_name.setText("  " + mMarketShSzBean.getGroupName());
			layout_group2.setBackgroundColor(getActivity().getResources().getColor(R.color.round_solid_color));
			txt_name.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.market_down_expand), null,null , null);
			txt_all.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.more), null);
			txt_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 if(mMarketShSzBean.getGroupType()<=4){
						 PlatePullToRefreshPinnedSectionListViewFragmentActivity.startPlatePullToRefreshPinnedSectionListViewFragmentActivity(getActivity(), mMarketShSzBean.getUrl(),mMarketShSzBean.getGroupName());
					 }else{
						 PlateStockPullToRefreshPinnedSectionListViewFragmentActivity.startPlateStockPullToRefreshPinnedSectionListViewFragmentActivity(getActivity(), mMarketShSzBean.getUrl(),mMarketShSzBean.getGroupName());
					 }
				}
			});
		}else{
			txt_name.setText("");
			txt_all.setText("");
			txt_name.setCompoundDrawablesWithIntrinsicBounds(null, null,null , null);
			txt_all.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			layout_group2.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent_color));
		}
		
	}

//	/* (non-Javadoc)
//	 * @see com.open.baidu.finance.widget.StickyLayout.OnGiveUpTouchEventListener#giveUpTouchEvent(android.view.MotionEvent)
//	 */
//	@Override
//	public boolean giveUpTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		if (mPullToRefreshExpandableListView.getRefreshableView().getFirstVisiblePosition() == 0) {
//            View view = mPullToRefreshExpandableListView.getRefreshableView().getChildAt(0);
//            if (view != null && view.getTop() >= 0) {
//                return true;
//            }
//        }
//        return false;
//	}

}
