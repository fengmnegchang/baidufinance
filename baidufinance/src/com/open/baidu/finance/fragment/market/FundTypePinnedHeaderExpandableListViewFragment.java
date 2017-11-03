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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView.OnHeaderUpdateListener;
import com.handmark.pulltorefresh.library.PinnedHeaderExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedHeaderExpandableListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.market.FundSyncHorizontalScrollViewFragmentActivity;
import com.open.baidu.finance.adapter.market.FundTypePinnedHeaderExpandableListAdapter;
import com.open.baidu.finance.bean.market.FundBean;
import com.open.baidu.finance.bean.market.FundTypeBean;
import com.open.baidu.finance.bean.market.PlateStockBean;
import com.open.baidu.finance.json.market.FundJson;
import com.open.baidu.finance.json.market.FundTypeJson;
import com.open.baidu.finance.json.market.PlateStockJson;
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
public class FundTypePinnedHeaderExpandableListViewFragment extends BaseV4Fragment<FundTypeJson, FundTypePinnedHeaderExpandableListViewFragment> implements OnRefreshListener<ExpandableListView>,
		OnHeaderUpdateListener {
	// OnGiveUpTouchEventListener
	public PullToRefreshPinnedHeaderExpandableListView mPullToRefreshExpandableListView;
	public FundTypePinnedHeaderExpandableListAdapter mFundTypePinnedHeaderExpandableListAdapter;
	public List<FundTypeBean> list = new ArrayList<FundTypeBean>();
	// private StickyLayout stickyLayout;
	private View headview;

	public static FundTypePinnedHeaderExpandableListViewFragment newInstance(String url, boolean isVisibleToUser) {
		FundTypePinnedHeaderExpandableListViewFragment fragment = new FundTypePinnedHeaderExpandableListViewFragment();
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
		super.initValues();
		mPullToRefreshExpandableListView.getRefreshableView().addHeaderView(headview);
		IndexShSZFragment fragment = IndexShSZFragment.newInstance(UrlUtils.INDEX_SH_SZ_FUND, true);
		getChildFragmentManager().beginTransaction().replace(R.id.id_mystock_headview, fragment).commit();

		mFundTypePinnedHeaderExpandableListAdapter = new FundTypePinnedHeaderExpandableListAdapter(getActivity(), list);
		mPullToRefreshExpandableListView.getRefreshableView().setAdapter(mFundTypePinnedHeaderExpandableListAdapter);
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
			for (int i = 0; i <= 6; i++) {
				FundTypeBean mmbean = new FundTypeBean();
				mmbean.setGroupType(i);
				mmbean.setList(new ArrayList<FundBean>());
				list.add(mmbean);
			}
			fund(UrlUtils.GETHQNODEDATASIMPLE_CLOSE_FUND, 0, "封闭式基金");
			stock(UrlUtils.GETFUNDNETDATA_OPEN_FUND, 1, "开放式基金");
			stock(UrlUtils.GETFUNDNETDATA_ETF_JZ_FUND, 2, "ETF基金净值");
			fund(UrlUtils.GETHQNODEDATASIMPLE_ETF_HQ_FUND, 3, "ETF基金行情");
			fund(UrlUtils.GETHQNODEDATASIMPLE_LOF_HQ_FUND, 4, "LOF基金行情");
			stock(UrlUtils.GETFUNDPREVDATA, 5, "基金预测净值");
			ah(UrlUtils.GETNAMELIST_MONEY_FUND, 6, "货币型基金");
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

	public void expandAll() {
		for (int i = 0; i < mFundTypePinnedHeaderExpandableListAdapter.getGroupCount(); i++) {
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
					FundJson result = new FundJson();
					response = response.replace("{", "{\"").replace(",", ",\"").replace(":", "\":").replace("},\"{", "},{");
					response = "{\"list\":" + response + "}";
					System.out.println("response=" + response.toString());

					Gson gson = new Gson();
					result = gson.fromJson(response, FundJson.class);

					List<FundBean> slist = new ArrayList<FundBean>();
					if("基金预测净值".equals(groupName)){
						int size = result.getList().size();
						if (result.getList().size() > 10) {
							size = 10;
						} 
						FundBean fundbean;
						for(int i=0;i<size;i++){
							fundbean = result.getList().get(i);
							fundbean.setDate(fundbean.getPre_date());
							fundbean.setDwjz(fundbean.getPre_nav());
							fundbean.setJzzz(fundbean.getAccu_nav());
							slist.add(fundbean);
						}
					}else{
						if (result.getList().size() > 10) {
							for (int i = 0; i < 10; i++) {
								slist.add(result.getList().get(i));
							}
						}else{
							slist.addAll(result.getList());
						}
					}
					

					list.get(type).getList().clear();
					list.get(type).setList(slist);
					list.get(type).setGroupName(groupName);
					list.get(type).setUrl(href);
					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(FundTypePinnedHeaderExpandableListViewFragment.this);
					mFundTypePinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, FundTypePinnedHeaderExpandableListViewFragment.this) {
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
			final FundTypeBean mMarketShSzBean = (FundTypeBean) mFundTypePinnedHeaderExpandableListAdapter.getGroup(groupPosition);
			txt_name.setText("  " + mMarketShSzBean.getGroupName());
			layout_group2.setBackgroundColor(getActivity().getResources().getColor(R.color.round_solid_color));
			txt_name.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.market_down_expand), null, null, null);
			txt_all.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.more), null);
			txt_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					FundSyncHorizontalScrollViewFragmentActivity.startFundSyncHorizontalScrollViewFragmentActivity(getActivity(), mMarketShSzBean.getUrl(),mMarketShSzBean.getGroupName());
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
	
	/**
	 * 封闭式基金
	 */
	public void fund(final String href, final int type, final String groupName) {
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
					
					
					List<FundBean> slist = new ArrayList<FundBean>();
					int size = result.getList().size();
					if (result.getList().size() > 10) {
						size = 10;
					}
					FundBean fundBean;
					for(int i=0;i<size;i++){
						fundBean = new FundBean();
						fundBean.setSymbol(result.getList().get(i).getCode());
						fundBean.setName(result.getList().get(i).getName());
						fundBean.setDate(result.getList().get(i).getTicktime());
						fundBean.setDwjz(result.getList().get(i).getTrade());
						fundBean.setJzzz(result.getList().get(i).getChangepercent());
						slist.add(fundBean);
					}
					list.get(type).getList().clear();
					list.get(type).setList(slist);
					list.get(type).setGroupName(groupName);
					list.get(type).setUrl(href);
					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(FundTypePinnedHeaderExpandableListViewFragment.this);
					mFundTypePinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, FundTypePinnedHeaderExpandableListViewFragment.this){
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
	
	
	/**
	 * 货币基金
	 */
	public void ah(final String href, final int type, final String groupName) {
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
					List<FundBean> slist = new ArrayList<FundBean>();
					int size = result.getList().size();
					if (result.getList().size() > 10) {
						size = 10;
					}
					FundBean fundBean;
					for(int i=0;i<size;i++){
						fundBean = new FundBean();
						fundBean.setSymbol(result.getList().get(i).getSymbol());
						slist.add(fundBean);
						codebuffer.append("f_" + fundBean.getSymbol() + ",");
					}
					list.get(type).getList().clear();
					list.get(type).setList(slist);
					list.get(type).setGroupName(groupName);
					list.get(type).setUrl(href);
					((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(FundTypePinnedHeaderExpandableListViewFragment.this);
					mFundTypePinnedHeaderExpandableListAdapter.notifyDataSetChanged();
					mPullToRefreshExpandableListView.onRefreshComplete();
					expandAll();
					
					String href = codebuffer.toString().substring(0, codebuffer.toString().length() - 1);
					getStockList(href, type);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, FundTypePinnedHeaderExpandableListViewFragment.this){
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

	
	public void getStockList(final String href, final int type) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
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
							} catch (Exception e) {
								e.printStackTrace();
							}
							plist.add(fundbean);
						}
						list.get(type).getList().clear();
						list.get(type).setList(plist);
						((PinnedHeaderExpandableListView) mPullToRefreshExpandableListView.getRefreshableView()).setOnHeaderUpdateListener(FundTypePinnedHeaderExpandableListViewFragment.this);
						mFundTypePinnedHeaderExpandableListAdapter.notifyDataSetChanged();
						mPullToRefreshExpandableListView.onRefreshComplete();
						expandAll();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, FundTypePinnedHeaderExpandableListViewFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
}
