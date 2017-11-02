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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedHeaderExpandableListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.market.FundTypePinnedHeaderExpandableListAdapter;
import com.open.baidu.finance.bean.market.FundBean;
import com.open.baidu.finance.bean.market.FundTypeBean;
import com.open.baidu.finance.json.market.FundJson;
import com.open.baidu.finance.json.market.FundTypeJson;
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
public class FundTypePinnedHeaderExpandableListViewFragment extends BaseV4Fragment<FundTypeJson, FundTypePinnedHeaderExpandableListViewFragment> implements
		OnRefreshListener<ExpandableListView>, OnHeaderUpdateListener {
	//OnGiveUpTouchEventListener
	public PullToRefreshPinnedHeaderExpandableListView mPullToRefreshExpandableListView;
	public FundTypePinnedHeaderExpandableListAdapter mFundTypePinnedHeaderExpandableListAdapter;
	public List<FundTypeBean> list = new ArrayList<FundTypeBean>();
//	private StickyLayout stickyLayout;
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
//		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_mystock_headview, null);
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
//		 mPullToRefreshExpandableListView.getRefreshableView().addHeaderView(headview);
//		IndexShSZFragment fragment = IndexShSZFragment.newInstance(UrlUtils.INDEX_SH_SZ, true);
//		getChildFragmentManager().beginTransaction().replace(R.id.id_mystock_headview, fragment).commit();

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
			for (int i = 0; i <= 1; i++) {
				FundTypeBean mmbean = new FundTypeBean();
				mmbean.setGroupType(i);
				mmbean.setList(new ArrayList<FundBean>());
				list.add(mmbean);
			}
			stock(UrlUtils.GETFUNDNETDATA_OPEN_FUND, 0, "开放式基金");
			stock(UrlUtils.GETFUNDNETDATA_ETF_JZ_FUND, 1, "ETF基金净值");
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
					response = "{\"list\":"+response+"}";
					System.out.println("response=" + response.toString());
					
					Gson gson = new Gson();
					result = gson.fromJson(response, FundJson.class);

					List<FundBean> slist = new ArrayList<FundBean>();
					if (result.getList().size() > 10) {
						for (int i = 0; i < 10; i++) {
							slist.add(result.getList().get(i));
						}
					}

					list.get(type).getList().clear();
					list.get(type).setList(slist);
					list.get(type).setGroupName(groupName);
					list.get(type).setUrl(href);
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
//		if(groupPosition!=-1){
			final FundTypeBean mMarketShSzBean = (FundTypeBean) mFundTypePinnedHeaderExpandableListAdapter.getGroup(groupPosition);
			txt_name.setText("  " + mMarketShSzBean.getGroupName());
			layout_group2.setBackgroundColor(getActivity().getResources().getColor(R.color.round_solid_color));
			txt_name.setCompoundDrawablesWithIntrinsicBounds(getActivity().getResources().getDrawable(R.drawable.market_down_expand), null,null , null);
			txt_all.setCompoundDrawablesWithIntrinsicBounds(null, null, getActivity().getResources().getDrawable(R.drawable.more), null);
			txt_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				}
			});
//		}else{
//			txt_name.setText("");
//			txt_all.setText("");
//			txt_name.setCompoundDrawablesWithIntrinsicBounds(null, null,null , null);
//			txt_all.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
//			layout_group2.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent_color));
//		}
		
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
