/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13上午10:33:52
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
 * 指数
 * @author :fengguangjing
 * @createTime:2017-10-13上午10:33:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class IndexMarkHeadFragment extends BaseV4Fragment<CommonJson, IndexMarkHeadFragment> {
	
	public static IndexMarkHeadFragment newInstance(String url, boolean isVisibleToUser) {
		IndexMarkHeadFragment fragment = new IndexMarkHeadFragment();
		fragment.setFragment(fragment);
		fragment.isVisibleToUser = isVisibleToUser;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_index_market_head, container, false);
		return view;
	}

}
