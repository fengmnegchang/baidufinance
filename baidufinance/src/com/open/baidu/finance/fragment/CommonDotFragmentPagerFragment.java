/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午2:54:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.bean.CommonBean;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午2:54:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class CommonDotFragmentPagerFragment extends BaseV4Fragment<CommonJson, CommonDotFragmentPagerFragment>implements OnPageChangeListener{
	public ViewPager viewpager;
	public ImageView[] dots;
	public int currentIndex;
	public int size;
	public LinearLayout layout_dot;
	public CommonFragmentPagerAdapter mCommonFragmentPagerAdapter;
	public List<Fragment> listFragment = new ArrayList<Fragment>();
	public List<String> titleList = new ArrayList<String>();
	public List<CommonBean> list = new ArrayList<CommonBean>();
	
	public static CommonDotFragmentPagerFragment newInstance(String url, boolean isVisibleToUser) {
		CommonDotFragmentPagerFragment fragment = new CommonDotFragmentPagerFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		layout_dot = (LinearLayout) view.findViewById(R.id.layout_dot);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mCommonFragmentPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(),listFragment,titleList);
		viewpager.setAdapter(mCommonFragmentPagerAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		viewpager.setOnPageChangeListener(this);
	}


	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#call()
	 */
	@Override
	public CommonJson call() throws Exception {
		// TODO Auto-generated method stub
		CommonJson mIndexFocusJson = new CommonJson();
		List<CommonBean> dlist = new ArrayList<CommonBean>();
		CommonBean bean = new CommonBean();
		dlist.add(bean);
		
		bean = new CommonBean();
		dlist.add(bean);
		
		bean = new CommonBean();
		dlist.add(bean);
		
		mIndexFocusJson.setDotlist(dlist);
		return mIndexFocusJson;
	}


	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(CommonJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getDotlist());
		
		for(int i=0;i<list.size();i++){
			titleList.add(""+i);
			switch (i) {
			case 0:
				listFragment.add(StartFragment1.newInstance(null,true,R.layout.fragment_start1));
				break;
			case 1:
				listFragment.add(StartFragment1.newInstance(null,false,R.layout.fragment_start2));
				break;
			case 2:
				listFragment.add(StartLoginFragment.newInstance(null,false));
				break;
			default:
				break;
			}
			
		}
		mCommonFragmentPagerAdapter.notifyDataSetChanged();
		
		 size = result.getDotlist().size();
		 if(size>0){
			 dots = new ImageView[size];
				for (int i = 0; i < size; i++) {
					ImageView img = new ImageView(getActivity());
					img.setImageResource(R.drawable.dot);
					img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					img.setPadding(15, 15, 15, 15);
					img.setClickable(true);
					dots[i] = img;
					dots[i].setEnabled(true);
					dots[i].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							int position = (Integer) v.getTag();
							setCurView(position);
							setCurDot(position);
						}
					});
					dots[i].setTag(i);

					layout_dot.addView(dots[i]);
				}

				currentIndex = 0;
				dots[currentIndex].setEnabled(false);
				viewpager.setCurrentItem(0);
		 }
			
	}
	
	/**
	 * 设置当前的引导页
	 */
	public void setCurView(int position) {
		if (position < 0 || position >= size) {
			return;
		}

		viewpager.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	public void setCurDot(int positon) {
		if (positon < 0 || positon > size - 1
				|| currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurDot(arg0);
	}
	
	/* (non-Javadoc)
	 * @see com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
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
}