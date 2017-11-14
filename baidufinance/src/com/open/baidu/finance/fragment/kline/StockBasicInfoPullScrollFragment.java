/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14上午10:44:55
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

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.kline.StockScrollMarketFragmentActivity;
import com.open.baidu.finance.bean.kline.CompanyProfilesBean;
import com.open.baidu.finance.bean.kline.StockBasicInfoBean;
import com.open.baidu.finance.bean.kline.StockBasicInfoBean.RelatedConcept;
import com.open.baidu.finance.bean.kline.StockBasicInfoBean.StockBasicInfoExt30.Events;
import com.open.baidu.finance.bean.kline.StockBasicInfoBean.StockBasicInfoExt30.NetProfit;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14上午10:44:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockBasicInfoPullScrollFragment extends BaseV4Fragment<CommonJson, StockBasicInfoPullScrollFragment> implements OnClickListener {
	private LinearLayout layout_all, layout_event,layout_concept,layout_income;
	private TextView txt_profit, txt_income, txt_pershare;
	private int type;
	private StockBasicInfoBean mStockBasicInfoBean;

	public static StockBasicInfoPullScrollFragment newInstance(String url, boolean isVisibleToUser) {
		StockBasicInfoPullScrollFragment fragment = new StockBasicInfoPullScrollFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_basic_info_scrollable_layout, container, false);
		layout_all = (LinearLayout) view.findViewById(R.id.layout_all);
		layout_event = (LinearLayout) view.findViewById(R.id.layout_event);
		layout_concept = (LinearLayout) view.findViewById(R.id.layout_concept);
		layout_income = (LinearLayout) view.findViewById(R.id.layout_income);

		txt_profit = (TextView) view.findViewById(R.id.txt_profit);
		txt_income = (TextView) view.findViewById(R.id.txt_income);
		txt_pershare = (TextView) view.findViewById(R.id.txt_pershare);
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
		txt_profit.setOnClickListener(this);
		txt_income.setOnClickListener(this);
		txt_pershare.setOnClickListener(this);
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
			companyprofiles(UrlUtils.COMPANYPROFILES + url);
			stockbasicinfo(UrlUtils.STOCKBASICINFO + url);
			break;
		}
	}

	private void stockbasicinfo(final String href) {
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
					StockBasicInfoBean result = gson.fromJson(response.toString(), StockBasicInfoBean.class);
					setStockBasicInfo(result);

					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(response.toString());
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
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				System.out.println(error);
				List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(), url, pageNo + "");
				Gson gson = new Gson();
				StockBasicInfoBean result = gson.fromJson(dblist.get(0).getTitle(), StockBasicInfoBean.class);
				setStockBasicInfo(result);
			}
		});
		requestQueue.add(jsonObjectRequest);
	}

	private void setStockBasicInfo(StockBasicInfoBean result) {
		mStockBasicInfoBean = result;
		if(mStockBasicInfoBean!=null){
			setChart(mStockBasicInfoBean.getStockBasicInfoExt30().getNetProfit(),"净利润");
			setConcept(mStockBasicInfoBean.getRelatedConcept());
		}
		if (result != null && result.getStockBasicInfoExt30() != null && result.getStockBasicInfoExt30().getEvents() != null) {
			for (int i = 0; i < result.getStockBasicInfoExt30().getEvents().size(); i++) {
				Events event = result.getStockBasicInfoExt30().getEvents().get(i);
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_stock_basic_info_item, null);
				TextView txt_datetime = (TextView) view.findViewById(R.id.txt_datetime);
				TextView txt_eventType = (TextView) view.findViewById(R.id.txt_eventType);
				TextView txt_message = (TextView) view.findViewById(R.id.txt_message);
				txt_datetime.setText(event.getDatetime());
				txt_eventType.setText(event.getEventType());
				txt_message.setText(event.getMessage());
				layout_event.addView(view);
			}
		}
	}
	
	private void setConcept(final List<RelatedConcept> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_stock_concept_item, null);
				TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
				TextView txt_ratio = (TextView) view.findViewById(R.id.txt_ratio);
				final TextView txt_stockname = (TextView) view.findViewById(R.id.txt_stockname);
				TextView txt_price = (TextView) view.findViewById(R.id.txt_price);
				TextView txt_netchange = (TextView) view.findViewById(R.id.txt_netchange);
				
				txt_name.setText(list.get(i).getConceptName());
				txt_ratio.setText(String.format("%.2f", list.get(i).getChangeRatio()));
				txt_stockname.setText(list.get(i).getRiseMaxStock().getStockName());
				txt_price.setText(String.format("%.2f", list.get(i).getPrice()));
				txt_netchange.setText(String.format("%.2f", list.get(i).getNetChange()));
				if(list.get(i).getChangeRatio()>0){
					txt_ratio.setTextColor(getResources().getColor(R.color.red_color));
				}else if(list.get(i).getChangeRatio()<0){
					txt_ratio.setTextColor(getResources().getColor(R.color.green_color));
				}else{
					txt_ratio.setTextColor(getResources().getColor(R.color.gray_53_color));
				}
				final String stockcode = list.get(i).getRiseMaxStock().getExchange()+list.get(i).getRiseMaxStock().getStockCode();
				txt_stockname.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						StockScrollMarketFragmentActivity.startStockScrollMarketFragmentActivity(getActivity(),stockcode , txt_stockname.getText().toString(), "");
					}
				});
				layout_concept.addView(view);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	public void companyprofiles(final String href) {
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
					CompanyProfilesBean result = gson.fromJson(response.toString(), CompanyProfilesBean.class);
					setCompany(result);
					setIncome(result);
					 
					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(response.toString());
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
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				System.out.println(error);
				List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(), url, pageNo + "");
				Gson gson = new Gson();
				CompanyProfilesBean result = gson.fromJson(dblist.get(0).getTitle(), CompanyProfilesBean.class);
				setCompany(result);
				setIncome(result);
			}
		});
		requestQueue.add(jsonObjectRequest);
	}

	private void setCompany(CompanyProfilesBean result) {
		if (result != null && result.getDescription() != null) {
			for (int i = 0; i < result.getDescription().size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_stock_company_info_item, null);
				TextView txt_itemName = (TextView) view.findViewById(R.id.txt_itemName);
				TextView txt_itemValue = (TextView) view.findViewById(R.id.txt_itemValue);
				txt_itemName.setText(result.getDescription().get(i).getItemName());
				txt_itemValue.setText(result.getDescription().get(i).getItemValue());
				if (i % 2 == 0) {
					view.setBackgroundColor(getResources().getColor(R.color.round_solid_color));
				}
				layout_all.addView(view);
			}
		}
	}
	
	
	private void setIncome(CompanyProfilesBean result) {
		if (result != null && result.getDescription() != null && result.getStockIncome().getIncome()!=null) {
			for (int i = 0; i < result.getStockIncome().getIncome().size(); i++) {
				View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_stock_income_item, null);
				TextView txt_name = (TextView) view.findViewById(R.id.txt_name);
				TextView txt_money = (TextView) view.findViewById(R.id.txt_money);
				TextView txt_changeRatio = (TextView) view.findViewById(R.id.txt_changeRatio);
				
				txt_name.setText(result.getStockIncome().getIncome().get(i).getName());
				txt_money.setText(String.format("%.2f",  result.getStockIncome().getIncome().get(i).getMoney()));
				txt_changeRatio.setText(String.format("%.2f",  result.getStockIncome().getIncome().get(i).getChangeRatio()));
				if (i % 2 == 0) {
					view.setBackgroundColor(getResources().getColor(R.color.round_solid_color));
				}
				layout_income.addView(view);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_profit:
			//0
			if(type!=0){
				type=0;
				txt_profit.setBackgroundResource(R.drawable.blue_bounds_shape);
				txt_income.setBackgroundResource(R.drawable.round_bounds_shape);
				txt_pershare.setBackgroundResource(R.drawable.round_bounds_shape);
				if(mStockBasicInfoBean!=null){
					setChart(mStockBasicInfoBean.getStockBasicInfoExt30().getNetProfit(),"净利润");
				}
			}
			break;
		case R.id.txt_income:
			//1
			if(type!=1){
				type=1;
				txt_profit.setBackgroundResource(R.drawable.round_bounds_shape);
				txt_income.setBackgroundResource(R.drawable.blue_bounds_shape);
				txt_pershare.setBackgroundResource(R.drawable.round_bounds_shape);
				if(mStockBasicInfoBean!=null){
					setChart(mStockBasicInfoBean.getStockBasicInfoExt30().getBusinessIncome(),"营业收入");
				}
			}
			break;
		case R.id.txt_pershare:
			//2
			if(type!=2){
				type=2;
				txt_profit.setBackgroundResource(R.drawable.round_bounds_shape);
				txt_income.setBackgroundResource(R.drawable.round_bounds_shape);
				txt_pershare.setBackgroundResource(R.drawable.blue_bounds_shape);
				if(mStockBasicInfoBean!=null){
					setChart(mStockBasicInfoBean.getStockBasicInfoExt30().getEarningsPerShare(),"每股收益");
				}
			}
			break;
		default:
			break;
		}
	}
	
	private void setChart(NetProfit mNetProfit,String chartName){
		StockBasicInfoChartFragment fragment = StockBasicInfoChartFragment.newInstance(url,mNetProfit,type,chartName, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_combined_chart, fragment).commit();
	}
}
