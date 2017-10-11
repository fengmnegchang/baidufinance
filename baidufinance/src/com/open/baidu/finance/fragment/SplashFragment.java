/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午1:57:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.android.activity.common.CommonALLActivity;
import com.open.android.fragment.BaseV4MVPPFragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.presenter.SplashPresenter;
import com.open.baidu.finance.viewmodel.SplashViewModel;
import static com.google.common.base.Preconditions.checkNotNull;
/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午1:57:53
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class SplashFragment extends BaseV4MVPPFragment<CommonJson, SplashFragment,SplashPresenter> implements SplashViewModel<CommonJson, SplashPresenter> {

	public static SplashFragment newInstance() {
		Bundle arguments = new Bundle();
		SplashFragment fragment = new SplashFragment();
		fragment.setArguments(arguments);
		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();
		mPresenter.start();
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_splash, container, false);
		setHasOptionsMenu(true);
		return root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.mvp.view.CommonView#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(CommonJson result) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.mvp.base.BaseView#setPresenter(java.lang.Object)
	 */
	@Override
	public void setPresenter(SplashPresenter presenter) {
		// TODO Auto-generated method stub
		mPresenter = checkNotNull(presenter);
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
			mPresenter.doAsync();
			break;
		default:
			break;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.yaoraotu.viewmodel.SplashViewModel#goToMain()
	 */
	@Override
	public void goToStart() {
		// TODO Auto-generated method stub
		getActivity().startActivity(new Intent(getActivity(), CommonALLActivity.class));
		getActivity().finish();
	}

}

