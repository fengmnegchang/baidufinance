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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.open.android.activity.common.CommonALLActivity;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.DynamicMainTabActivity;

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
public class StartLoginFragment extends BaseV4Fragment<CommonJson, StartLoginFragment> {
	private Button btn_use,btn_login;
	public static StartLoginFragment newInstance(String url, boolean isVisibleToUser) {
		StartLoginFragment fragment = new StartLoginFragment();
		fragment.setFragment(fragment);
		fragment.isVisibleToUser = isVisibleToUser;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_start_login, container, false);
		btn_use = (Button) view.findViewById(R.id.btn_use);
		btn_login = (Button) view.findViewById(R.id.btn_login);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		btn_use.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DynamicMainTabActivity.startDynamicMainTabActivity(getActivity(), null);
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
//				intent.putExtra("URL", url);
				intent.setClass(getActivity(), CommonALLActivity.class);
				getActivity().startActivity(intent);
			}
		});
	}

}
