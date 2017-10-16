/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-16上午10:59:10
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.mystock.StockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 股价提醒
 * @author :fengguangjing
 * @createTime:2017-10-16上午10:59:10
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockAlarmSettingFragment extends BaseV4Fragment<CommonJson, StockAlarmSettingFragment> {
	private StockBean bean;
	private TextView txt_stock_name,txt_close,txt_rate;
	private RelativeLayout layout_event;
	
	public static StockAlarmSettingFragment newInstance(String url,StockBean bean, boolean isVisibleToUser) {
		StockAlarmSettingFragment fragment = new StockAlarmSettingFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.bean = bean;
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_alarm_setting, container, false);
		txt_stock_name = (TextView) view.findViewById(R.id.txt_stock_name);
		txt_close = (TextView) view.findViewById(R.id.txt_close);
		txt_rate = (TextView) view.findViewById(R.id.txt_rate);
		layout_event = (RelativeLayout) view.findViewById(R.id.layout_event);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		if(bean!=null){
			txt_stock_name.setText(bean.getStockName());
			txt_close.setText(String.format("%.2f", bean.getClose()));
			txt_rate.setText(String.format("%.2f", bean.getNetChangeRatio()) +"%");
			if(bean.getNetChangeRatio()>0){
				txt_close.setTextColor(getActivity().getResources().getColor(R.color.red_color));
				txt_rate.setTextColor(getActivity().getResources().getColor(R.color.red_color));
			}else if(bean.getNetChangeRatio()<0){
				txt_close.setTextColor(getActivity().getResources().getColor(R.color.green_color));
				txt_rate.setTextColor(getActivity().getResources().getColor(R.color.green_color));
			}else{
				txt_close.setTextColor(getActivity().getResources().getColor(R.color.gray_color));
				txt_rate.setTextColor(getActivity().getResources().getColor(R.color.gray_color));
			}
			
			if(bean.getStockCode().equalsIgnoreCase(".IXIC")||bean.getStockCode().equalsIgnoreCase(".DJI")
					||bean.getStockCode().equalsIgnoreCase("399005")||bean.getStockCode().equalsIgnoreCase("000300")
					||bean.getStockCode().equalsIgnoreCase("399006")||bean.getStockCode().equalsIgnoreCase("399001")
					||bean.getStockCode().equalsIgnoreCase("000001")||bean.getStockCode().equalsIgnoreCase("HSI")
					){
				layout_event.setVisibility(View.GONE);
			}else{
				layout_event.setVisibility(View.VISIBLE);
			}
		}
	}
}
