/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31上午10:32:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.market;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.market.IndexBean;
import com.open.baidu.finance.json.market.IndexJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-31上午10:32:15
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class IndexShSZFragment extends BaseV4Fragment<IndexJson, IndexShSZFragment> {
	private TextView txt_index_value,txt_index_rate;//上证
	private TextView txt_index_value_sz,txt_index_rate_sz;//深证
	private TextView txt_index_value_cy,txt_index_rate_cy;//创业
	private TextView txt_index_value_hs300,txt_index_rate_hs300;//沪深300
	
	public static IndexShSZFragment newInstance(String url, boolean isVisibleToUser) {
		IndexShSZFragment fragment = new IndexShSZFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_index_sh_sz, container, false);
		txt_index_value = (TextView) view.findViewById(R.id.txt_index_value);
		txt_index_rate = (TextView) view.findViewById(R.id.txt_index_rate);
		txt_index_value_sz = (TextView) view.findViewById(R.id.txt_index_value_sz);
		txt_index_rate_sz = (TextView) view.findViewById(R.id.txt_index_rate_sz);
		txt_index_value_cy = (TextView) view.findViewById(R.id.txt_index_value_cy);
		txt_index_rate_cy = (TextView) view.findViewById(R.id.txt_index_rate_cy);
		txt_index_value_hs300 = (TextView) view.findViewById(R.id.txt_index_value_hs300);
		txt_index_rate_hs300 = (TextView) view.findViewById(R.id.txt_index_rate_hs300);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public IndexJson call() throws Exception {
		// TODO Auto-generated method stub
		return super.call();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(IndexJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null && result.getList()!=null){
			txt_index_value.setText(String.format("%.2f", result.getList().get(0).getClose())+"");
			txt_index_rate.setText(String.format("%.2f", result.getList().get(0).getNetChnage())+" "+result.getList().get(0).getNetChnageRate()+"%");

			if(result.getList().get(0).getNetChnage()>0){
				txt_index_value.setTextColor(getActivity().getResources().getColor(R.color.red_color));
//				txt_index_rate.setTextColor(getActivity().getResources().getColor(R.color.red_color));
			}else if(result.getList().get(0).getNetChnage()<0){
				txt_index_value.setTextColor(getActivity().getResources().getColor(R.color.green_color));
//				txt_index_rate.setTextColor(getActivity().getResources().getColor(R.color.green_color));
			}else{
				txt_index_value.setTextColor(getActivity().getResources().getColor(R.color.black_color));
//				txt_index_rate.setTextColor(getActivity().getResources().getColor(R.color.black_color));
			}
		
			txt_index_value_sz.setText(String.format("%.2f", result.getList().get(1).getClose())+"");
			txt_index_rate_sz.setText(String.format("%.2f", result.getList().get(1).getNetChnage())+" "+result.getList().get(1).getNetChnageRate()+"%");
			if(result.getList().get(1).getNetChnage()>0){
				txt_index_value_sz.setTextColor(getActivity().getResources().getColor(R.color.red_color));
//				txt_index_rate_sz.setTextColor(getActivity().getResources().getColor(R.color.red_color));
			}else if(result.getList().get(1).getNetChnage()<0){
				txt_index_value_sz.setTextColor(getActivity().getResources().getColor(R.color.green_color));
//				txt_index_rate_sz.setTextColor(getActivity().getResources().getColor(R.color.green_color));
			}else{
				txt_index_value_sz.setTextColor(getActivity().getResources().getColor(R.color.black_color));
//				txt_index_rate_sz.setTextColor(getActivity().getResources().getColor(R.color.black_color));
			}
			
			txt_index_value_cy.setText(String.format("%.2f", result.getList().get(2).getClose())+"");
			txt_index_rate_cy.setText(String.format("%.2f", result.getList().get(2).getNetChnage())+" "+result.getList().get(2).getNetChnageRate()+"%");
			if(result.getList().get(2).getNetChnage()>0){
				txt_index_value_cy.setTextColor(getActivity().getResources().getColor(R.color.red_color));
//				txt_index_rate_cy.setTextColor(getActivity().getResources().getColor(R.color.red_color));
			}else if(result.getList().get(2).getNetChnage()<0){
				txt_index_value_cy.setTextColor(getActivity().getResources().getColor(R.color.green_color));
//				txt_index_rate_cy.setTextColor(getActivity().getResources().getColor(R.color.green_color));
			}else{
				txt_index_value_cy.setTextColor(getActivity().getResources().getColor(R.color.black_color));
//				txt_index_rate_cy.setTextColor(getActivity().getResources().getColor(R.color.black_color));
			}
			
			txt_index_value_hs300.setText(String.format("%.2f", result.getList().get(3).getClose())+"");
			txt_index_rate_hs300.setText(String.format("%.2f", result.getList().get(3).getNetChnage())+" "+result.getList().get(3).getNetChnageRate()+"%");
			if(result.getList().get(3).getNetChnage()>0){
				txt_index_value_hs300.setTextColor(getActivity().getResources().getColor(R.color.red_color));
//				txt_index_rate_hs300.setTextColor(getActivity().getResources().getColor(R.color.red_color));
			}else if(result.getList().get(3).getNetChnage()<0){
				txt_index_value_hs300.setTextColor(getActivity().getResources().getColor(R.color.green_color));
//				txt_index_rate_hs300.setTextColor(getActivity().getResources().getColor(R.color.green_color));
			}else{
				txt_index_value_hs300.setTextColor(getActivity().getResources().getColor(R.color.black_color));
//				txt_index_rate_hs300.setTextColor(getActivity().getResources().getColor(R.color.black_color));
			}
		
		}
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
//			doAsync(this, this, this);
			volleyJson(url);
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
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//		Map<String,String> params = new HashMap<String,String>(); 
//		params.put("User-Agent",UrlUtils.userAgent);
//		params.put("Referer","https://gupiao.baidu.com/my/");
//		params.put("Cookie", UrlUtils.COOKIE);
		StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, href, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				//var hq_str_s_sh000001="上证指数,3383.1362,-7.2009,-0.21,507639,6764438";
				//var hq_str_s_sz399001="深证成指,11299.34,-13.271,-0.12,54496073,8136313";
				//var hq_str_s_sh000300="沪深300,3991.1227,-18.5991,-0.46,378222,5039534";
				//var hq_str_s_sz399006="创业板指,1864.22,8.237,0.44,3897572,625412";
				try {
					IndexJson result = new IndexJson();
					List<IndexBean> list = new ArrayList<IndexBean>();
					IndexBean bean;
					String[] codes = response.split("var hq_str_s_");
					for(int i=1;i<codes.length;i++){
						//sh000001="上证指数,3383.1362,-7.2009,-0.21,507639,6764438";
						//         指数名称，当前点数，当前价格，涨跌率，成交量（手），成交额（万元）；
						bean = new IndexBean();
						
						try {
							String c = codes[i];
							String stockCode  = c.split("=")[0];
							bean.setStockCode(stockCode);
							
							String other = c.split("=")[1].replace(";", "").replace("\"", "");
							//上证指数,3383.1362,-7.2009,-0.21,507639,6764438
							bean.setStockName(other.split(",")[0]);
							bean.setClose(Double.parseDouble(other.split(",")[1]));
							bean.setNetChnage(Double.parseDouble(other.split(",")[2]));
							bean.setNetChnageRate(Double.parseDouble(other.split(",")[3]));
							bean.setVolume(Long.parseLong(other.split(",")[4]));
							bean.setVolumeMoney(Long.parseLong(other.split(",")[5].replace("\"", "")));
						} catch (Exception e) {
							e.printStackTrace();
						}
						list.add(bean);
					}
					result.setList(list);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, IndexShSZFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
 
	
}
