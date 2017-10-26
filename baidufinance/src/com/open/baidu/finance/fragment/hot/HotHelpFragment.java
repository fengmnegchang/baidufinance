/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午5:26:13
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午5:26:13
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class HotHelpFragment extends BaseV4Fragment<CommonJson, HotHelpFragment>{
	private TextView txt_help;
	public static HotHelpFragment newInstance(String url, boolean isVisibleToUser) {
		HotHelpFragment fragment = new HotHelpFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_hot_help, container, false);
		txt_help = (TextView) view.findViewById(R.id.txt_help);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		txt_help.setText(Html.fromHtml(getResources().getString(R.string.hot_help)));
	}

}
