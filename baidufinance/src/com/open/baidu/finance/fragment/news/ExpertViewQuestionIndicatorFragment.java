/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午5:31:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.MainTabBean;
import com.open.baidu.finance.bean.news.AdviserPersonBean;
import com.open.baidu.finance.json.MainTabJson;
import com.open.indicator.TabPageIndicator;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午5:31:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ExpertViewQuestionIndicatorFragment extends BaseV4Fragment<MainTabJson, ExpertViewQuestionIndicatorFragment>{
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public CommonFragmentPagerAdapter mRankPagerAdapter;
	public ArrayList<MainTabBean> list = new ArrayList<MainTabBean>();
	
	
	public TextView txt_name, txt_level, txt_info;
	public ImageView img_logo;
	public AdviserPersonBean mAdviserPersonBean;
	
	public static ExpertViewQuestionIndicatorFragment newInstance(String url,AdviserPersonBean bean, boolean isVisibleToUser) {
		ExpertViewQuestionIndicatorFragment fragment = new ExpertViewQuestionIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.mAdviserPersonBean = bean;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_expert_question_indicator_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		txt_name = (TextView) view.findViewById(R.id.txt_name);
		txt_level = (TextView) view.findViewById(R.id.txt_level);
		txt_info = (TextView) view.findViewById(R.id.txt_info);
		img_logo= (ImageView) view.findViewById(R.id.img_logo);
		return view;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
		
		if(mAdviserPersonBean!=null){
			if (mAdviserPersonBean.getSrc() != null && mAdviserPersonBean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(mAdviserPersonBean.getSrc(), img_logo, options, getImageLoadingListener());
			}
			txt_name.setText(mAdviserPersonBean.getExpertName());
			txt_level.setText(mAdviserPersonBean.getLevel());
			txt_info.setText(mAdviserPersonBean.getInfo());
		}
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
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public MainTabJson call() throws Exception {
		// TODO Auto-generated method stub
		MainTabJson mMainTabJson = new MainTabJson();
		ArrayList<MainTabBean> clist = new ArrayList<MainTabBean>();
		MainTabBean bean = new MainTabBean();
		bean.setHref(url);
		bean.setTitle("观点");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(url+"?tab=1");
		bean.setTitle("问答");
		clist.add(bean);
		
		mMainTabJson.setList(clist);
		return mMainTabJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MainTabJson result) {
		// TODO Auto-generated method stub
		if(result==null){
			return;
		}
		list.clear();
		list.addAll(result.getList());
		titleList.clear();

		Fragment fragment;
		for (int i=0;i< result.getList().size();i++) {
			MainTabBean bean = result.getList().get(i);
			titleList.add(bean.getTitle());
			if(i==0){
				fragment = ExpertViewPullListFragment.newInstance(bean.getHref(),true);
			}else{
				fragment = QuestionPullListFragment.newInstance(bean.getHref(),false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
	
}
