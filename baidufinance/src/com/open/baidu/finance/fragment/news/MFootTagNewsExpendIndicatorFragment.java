/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20上午11:15:57
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.news;

import java.util.ArrayList;

import android.support.v4.app.Fragment;

import com.open.baidu.finance.bean.MainTabBean;
import com.open.baidu.finance.json.MainTabJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20上午11:15:57
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MFootTagNewsExpendIndicatorFragment extends TagNewsIndicatorFragment {
	public static MFootTagNewsExpendIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		MFootTagNewsExpendIndicatorFragment fragment = new MFootTagNewsExpendIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
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
		bean.setHref(UrlUtils.M_GETTODAYNEWS);
		bean.setTitle("要闻");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.M_GETTAGNEWS_ECONOMY);
		bean.setTitle("宏观");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.M_GETTAGNEWS);
		bean.setTitle("行业");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.M_ORGANIZATION);
		bean.setTitle("机构");
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
				fragment = MFootTodayNewsExpendListFragment.newInstance(bean.getHref(),true);
			}else{
				fragment = MFootTagNewsExpendListFragment.newInstance(bean.getHref(),false);
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
}
