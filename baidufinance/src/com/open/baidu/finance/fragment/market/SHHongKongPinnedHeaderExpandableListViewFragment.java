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
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView;
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedHeaderExpandableListView;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.market.PlatePullToRefreshPinnedSectionListViewFragmentActivity;
import com.open.baidu.finance.activity.market.PlateStockPullToRefreshPinnedSectionListViewFragmentActivity;
import com.open.baidu.finance.adapter.market.MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter;
import com.open.baidu.finance.bean.market.FundBean;
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
public class SHHongKongPinnedHeaderExpandableListViewFragment extends BaseV4Fragment<MarketShSzJson, SHHongKongPinnedHeaderExpandableListViewFragment> implements
		OnRefreshListener<ExpandableListView>, OnHeaderUpdateListener {
	// OnGiveUpTouchEventListener
	public PullToRefreshPinnedHeaderExpandableListView mPullToRefreshExpandableListView;
	public MarketShSzPullToRefreshPinnedHeaderExpandableListAdapter mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter;
	public List<MarketShSzBean> list = new ArrayList<MarketShSzBean>();
	// private StickyLayout stickyLayout;
	public View headview;

	public static SHHongKongPinnedHeaderExpandableListViewFragment newInstance(String url, boolean isVisibleToUser) {
		SHHongKongPinnedHeaderExpandableListViewFragment fragment = new SHHongKongPinnedHeaderExpandableListViewFragment();
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
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_sh_hongkong_head, null);
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
		super.initValues();

		mPullToRefreshExpandableListView.getRefreshableView().addHeaderView(headview);

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
		// stickyLayout.setOnGiveUpTouchEventListener(this);

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
			for (int i = 30; i <= 32; i++) {
				MarketShSzBean mmbean = new MarketShSzBean();
				mmbean.setGroupType(i);
				mmbean.setPlist(new ArrayList<PlateBean>());
				mmbean.setSlist(new ArrayList<PlateStockBean>());
				list.add(mmbean);
			}
			stock(UrlUtils.GETHQNODEDATA_HGT_SH, 30, "沪股通",30);
			stock(UrlUtils.GETHQNODEDATA_HGT_HK, 31, "港股通",30);
			ah(UrlUtils.GETANHDATA_HGT_AH, 32, "A+H",30);
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
					List<PlateStockBean> slist = new ArrayList<PlateStockBean>();
					int size = mmbean.getSlist().size();
					if (mmbean.getSlist().size() > 10) {
						size = 10;
					}
					for (int i = 0; i < size; i++) {
						if (type == 31) {
							mmbean.getSlist().get(i).setTrade(mmbean.getSlist().get(i).getLasttrade());
						}
						slist.add(mmbean.getSlist().get(i));
					}

					list.get(type - num).getSlist().clear();
					list.get(type - num).setSlist(slist);
					list.get(type - num).setGroupName(groupName);
					list.get(type - num).setUrl(href);
					list.get(type - num).setPlist(new ArrayList<PlateBean>());

					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(SHHongKongPinnedHeaderExpandableListViewFragment.this);
					mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();
					
					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(gson.toJson(list.get(type - num)));
					
					openbean.setDownloadurl("");
					openbean.setImgsrc("");
					openbean.setType(pageNo);
					openbean.setTypename(pageNo+"");
					openbean.setUrl(href+type);
					OpenDBService.insert(getActivity(), openbean);
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
				((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(SHHongKongPinnedHeaderExpandableListViewFragment.this);
				mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
				mPullToRefreshExpandableListView.onRefreshComplete();
				expandAll();
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
		if (groupPosition != -1) {
			final MarketShSzBean mMarketShSzBean = (MarketShSzBean) mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.getGroup(groupPosition);
			txt_name.setText("  " + mMarketShSzBean.getGroupName());
			layout_group2.setBackgroundColor(getActivity().getResources().getColor(R.color.round_solid_color));
			txt_name.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.market_down_expand), null, null, null);
			txt_all.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.more), null);
			txt_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PlateStockPullToRefreshPinnedSectionListViewFragmentActivity.startPlateStockPullToRefreshPinnedSectionListViewFragmentActivity(getActivity(), mMarketShSzBean.getUrl(),
							mMarketShSzBean.getGroupName());
				}
			});
		} else {
			txt_name.setText("");
			txt_all.setText("");
			txt_name.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			txt_all.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			layout_group2.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent_color));
		}

	}

	// /* (non-Javadoc)
	// * @see
	// com.open.baidu.finance.widget.StickyLayout.OnGiveUpTouchEventListener#giveUpTouchEvent(android.view.MotionEvent)
	// */
	// @Override
	// public boolean giveUpTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// if
	// (mPullToRefreshExpandableListView.getRefreshableView().getFirstVisiblePosition()
	// == 0) {
	// View view =
	// mPullToRefreshExpandableListView.getRefreshableView().getChildAt(0);
	// if (view != null && view.getTop() >= 0) {
	// return true;
	// }
	// }
	// return false;
	// }
	
	public void ah(final String href, final int type, final String groupName,final int num) {
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
						codebuffer.append( mmbean.getSlist().get(i).getA() + ","+"hk"+mmbean.getSlist().get(i).getH()+",");
					}
					list.get(type-num).getSlist().clear();
					list.get(type-num).setSlist(slist);
					list.get(type-num).setGroupName(groupName);
					list.get(type-num).setUrl(href);
					list.get(type-num).setPlist(new ArrayList<PlateBean>());
					
					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView())
					.setOnHeaderUpdateListener(SHHongKongPinnedHeaderExpandableListViewFragment.this);
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
					openbean.setUrl(href+type+"SH_HONGKONG");
					OpenDBService.insert(getActivity(), openbean);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),href+type+"SH_HONGKONG", pageNo+"");
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
					if (response.contains("var hq_str_")) {
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

						list.get(type - num).getSlist().clear();
						list.get(type - num).setSlist(plist);

						((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(SHHongKongPinnedHeaderExpandableListViewFragment.this);
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
				
				((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(SHHongKongPinnedHeaderExpandableListViewFragment.this);
				mMarketShSzPullToRefreshPinnedHeaderExpandableListAdapter.notifyDataSetChanged();
				mPullToRefreshExpandableListView.onRefreshComplete();
				expandAll();
			}
		});
		requestQueue.add(jsonObjectRequest);
	}

}
