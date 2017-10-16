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

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;

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
public class StockAlarmSettingFragment extends BaseV4Fragment<CommonJson, StockAlarmSettingFragment> {
	public static StockAlarmSettingFragment newInstance(String url, boolean isVisibleToUser) {
		StockAlarmSettingFragment fragment = new StockAlarmSettingFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_alarm_setting, container, false);
		return view;
	}
}
