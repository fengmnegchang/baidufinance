/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14上午10:34:42
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.kline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Message;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.widget.ScrollableHelper.ScrollableContainer;
import com.open.baidu.finance.adapter.kline.StockReportDataAdapter;
import com.open.baidu.finance.bean.kline.ReportDataBean;
import com.open.baidu.finance.json.kline.ReportDataJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14上午10:34:42
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockReportDataPullListFragment extends CommonPullToRefreshListFragment<ReportDataBean, ReportDataJson> implements ScrollableContainer {
	private StockReportDataAdapter mStockReportDataAdapter;

	public static StockReportDataPullListFragment newInstance(String url, boolean isVisibleToUser) {
		StockReportDataPullListFragment fragment = new StockReportDataPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#initValues
	 * ()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mStockReportDataAdapter = new StockReportDataAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mStockReportDataAdapter);
		mPullToRefreshListView.setMode(Mode.BOTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			if (pageNo > 1) {
				url = url + "&page_index=" + pageNo;
			}
			volleyJson(url);
			break;
		default:
			break;
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
		List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(), url, pageNo + "");
		Gson gson = new Gson();
		ReportDataJson mReportDataJson = gson.fromJson(dblist.get(0).getTitle(), ReportDataJson.class);
		onCallback(mReportDataJson);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	@Override
	public void volleyJson(final String href) {
		// TODO Auto-generated method stub
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String, String> params = new HashMap<String, String>();
		params.put("User-Agent", UrlUtils.userAgent);
		// params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href, params, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					ReportDataJson result = gson.fromJson(response.toString(), ReportDataJson.class);
					onCallback(result);

					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(gson.toJson(result));
					openbean.setDownloadurl("");
					openbean.setImgsrc("");
					openbean.setType(pageNo);
					openbean.setTypename(pageNo + "");
					openbean.setUrl(url);
					OpenDBService.insert(getActivity(), openbean);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, StockReportDataPullListFragment.this);
		requestQueue.add(jsonObjectRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(ReportDataJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
			if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				list.addAll(result.getReportdata());
				pageNo = 1;
			} else {
				if (result.getReportdata() != null && result.getReportdata().size() > 0) {
					list.addAll(result.getReportdata());
				}
			}
			mStockReportDataAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.widget.ScrollableHelper.ScrollableContainer#
	 * getScrollableView()
	 */
	@Override
	public View getScrollableView() {
		// TODO Auto-generated method stub
		return mPullToRefreshListView.getRefreshableView();
	}

}
