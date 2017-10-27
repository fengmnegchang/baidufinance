/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午3:48:03
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午3:48:03
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StartFragment1 extends BaseV4Fragment<CommonJson, StartFragment1> {
	public int layoutId;
	public static StartFragment1 newInstance(String url, boolean isVisibleToUser,int layoutId) {
		StartFragment1 fragment = new StartFragment1();
		fragment.setFragment(fragment);
		fragment.isVisibleToUser = isVisibleToUser;
		fragment.layoutId = layoutId;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(layoutId, container, false);
		return view;
	}

}
