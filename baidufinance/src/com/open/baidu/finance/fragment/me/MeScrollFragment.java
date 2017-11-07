/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午9:49:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.me;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.me.MeAdapter;
import com.open.baidu.finance.bean.me.MeBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午9:49:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MeScrollFragment extends BaseV4Fragment<CommonJson, MeScrollFragment> {
	private PullToRefreshListView mPullToRefreshListView;
	private MeAdapter mMeAdapter;
	private List<MeBean> list = new ArrayList<MeBean>();

	public static MeScrollFragment newInstance(String url, boolean isVisibleToUser) {
		MeScrollFragment fragment = new MeScrollFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_me_pullscroll, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
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
		MeBean bean = new MeBean();
		bean.setName("基金交易");
		bean.setResId(R.drawable.personal_howbuy_icon_light);
		list.add(bean);

		bean = new MeBean();
		bean.setName("股市通课堂");
		bean.setResId(R.drawable.personal_investment);
		list.add(bean);

		bean = new MeBean();
		bean.setName("提醒中心");
		bean.setResId(R.drawable.remind_center);
		list.add(bean);
		
		bean = new MeBean();
		bean.setName("股票对比");
		bean.setResId(R.drawable.personal_item_contrast_img_light);
		list.add(bean);

		bean = new MeBean();
		bean.setName("炒股大赛");
		bean.setResId(R.drawable.stock_match);
		list.add(bean);

		bean = new MeBean();
		bean.setName("活动");
		bean.setResId(R.drawable.personal_activity);
		list.add(bean);
		
		bean = new MeBean();
		bean.setName("设置");
		bean.setResId(R.drawable.personal_setting);
		list.add(bean);

		mMeAdapter = new MeAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mMeAdapter);
		mPullToRefreshListView.setMode(Mode.DISABLED);
	}
}
