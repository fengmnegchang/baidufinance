/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:30:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.article;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.baidu.finance.R;
import com.open.baidu.finance.fragment.news.MFootTagNewsExpendIndicatorFragment;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:30:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MNewsContainerPullScrollFragment extends NewsContainerPullScrollFragment{
	
	public static MNewsContainerPullScrollFragment newInstance(String url, boolean isVisibleToUser) {
		MNewsContainerPullScrollFragment fragment = new MNewsContainerPullScrollFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
 
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		mPullToRefreshScrollView.setMode(Mode.PULL_FROM_START);
		
		MFootTagNewsExpendIndicatorFragment fragment = MFootTagNewsExpendIndicatorFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_more, fragment).commit();
	}
	
	 
}
