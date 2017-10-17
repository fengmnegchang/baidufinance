/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午3:48:01
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.MyStockViewPagerFragmentActivity;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.json.mystock.StockJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午3:48:01
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MyStockViewPagerFragment extends BaseV4Fragment<StockJson, MyStockViewPagerFragment> implements OnPageChangeListener {
	public ViewPager viewpager;
	public CommonFragmentPagerAdapter mCommonFragmentPagerAdapter;
	public List<Fragment> listFragment = new ArrayList<Fragment>();
	public List<String> titleList = new ArrayList<String>();
	public List<GroupBean> list = new ArrayList<GroupBean>();

	public static MyStockViewPagerFragment newInstance(String url, boolean isVisibleToUser) {
		MyStockViewPagerFragment fragment = new MyStockViewPagerFragment();
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
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mCommonFragmentPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listFragment, titleList);
		viewpager.setAdapter(mCommonFragmentPagerAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		viewpager.setOnPageChangeListener(this);
	}
 
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android.volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}
	
	@Override
	public void volleyJson(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", "BAIDUID=CF6C53AD63064C72CDA6A1CF7788EC1C:FG=1; BIDUPSID=CF6C53AD63064C72CDA6A1CF7788EC1C; PSTM=1504837961; MCITY=-289%3A; BDUSS=0dkR0YyclhzQTdsdTBnV09mRkFtYnRRYWdUM3BXV2E5d204RUVLeklHdnFxZ0phTVFBQUFBJCQAAAAAAAAAAAEAAAD5apgiZmVuZ3hpYW4wMzgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOod21nqHdtZS; stoken=4f7caf550b1c8aa74fb4b459d6d670d428c747b61bcda1f865b03434c562a47e; locale=zh; PSINO=5; H_PS_PSSID=1434_21118_17001_20929; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; Hm_lvt_35d1e71f4c913c126b8703586f1d2307=1507532257,1507532966,1507533021,1507692673; Hm_lpvt_35d1e71f4c913c126b8703586f1d2307=1507778418");
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					StockJson result = gson.fromJson(response.toString(), StockJson.class);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, MyStockViewPagerFragment.this);
		requestQueue.add(jsonObjectRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.enrz.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(StockJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getData().get(0).getData().getGroupList());
		for (int i = 0; i < list.size(); i++) {
			titleList.add(list.get(i).getGroup_name());
			if(i==0){
				// 8a5205fe03d1e3fcd7ec591ff8daea29 %22%7D%5D
				listFragment.add(MyStockPullToRefreshPinnedSectionListViewFragment.newInstance(UrlUtils.GATHERMYSTOCK+list.get(i).getGroup_id()+"%22%7D%5D", true));
			}else{
				listFragment.add(MyStockPullToRefreshPinnedSectionListViewFragment.newInstance(UrlUtils.GATHERMYSTOCK+list.get(i).getGroup_id()+"%22%7D%5D", false));
			}
		}
		mCommonFragmentPagerAdapter.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		((MyStockViewPagerFragmentActivity) getActivity()).setCenterTitle(titleList.get(arg0));
		position = arg0;
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
			volleyJson(url);
			break;
		default:
			break;
		}
	}

	public List<GroupBean> getList() {
		return list;
	}

}
