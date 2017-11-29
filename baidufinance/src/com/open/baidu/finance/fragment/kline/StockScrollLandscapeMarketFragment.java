/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-10下午2:01:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.kline;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.utils.NetWorkUtils;
import com.open.android.utils.ScreenUtils;
import com.open.android.widget.ScrollableHelper.ScrollableContainer;
import com.open.android.widget.ScrollableLayout;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.article.MNewsCommentPullListFragmentActivity;
import com.open.baidu.finance.activity.kline.StockScrollLandscapeMarketFragmentActivity;
import com.open.baidu.finance.activity.kline.StockScrollMarketFragmentActivity;
import com.open.baidu.finance.bean.MainTabBean;
import com.open.baidu.finance.bean.kline.StockInfoBean;
import com.open.baidu.finance.json.kline.StockInfoJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;
import com.open.baidu.finance.widget.PullToRefreshScrollableLayout;
import com.open.indicator.TabPageIndicator;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-10下午2:01:59
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockScrollLandscapeMarketFragment extends StockScrollMarketFragment{
	 

	public static StockScrollLandscapeMarketFragment newInstance(String url,String stockExCode,String stockCode, boolean isVisibleToUser) {
		StockScrollLandscapeMarketFragment fragment = new StockScrollLandscapeMarketFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.stockExCode = stockExCode;
		fragment.stockExCode = stockExCode;
		return fragment;
	}

 
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_market_land_pullscrollable_layout, container, false);
		img_expand = (ImageView) view.findViewById(R.id.img_expand);
		txt_net_rate = (TextView) view.findViewById(R.id.txt_net_rate);

		txt_close = (TextView) view.findViewById(R.id.txt_close);
		txt_open = (TextView) view.findViewById(R.id.txt_open);
		txt_pre_close = (TextView) view.findViewById(R.id.txt_pre_close);
		txt_volume = (TextView) view.findViewById(R.id.txt_volume);
		txt_ex_rate = (TextView) view.findViewById(R.id.txt_ex_rate);
		layout_top = (RelativeLayout) view.findViewById(R.id.layout_top);

		txt_min_k = (TextView) view.findViewById(R.id.txt_min_k);
		txt_five_k = (TextView) view.findViewById(R.id.txt_five_k);
		txt_day_k = (TextView) view.findViewById(R.id.txt_day_k);
		txt_week_k = (TextView) view.findViewById(R.id.txt_week_k);
		txt_month_k = (TextView) view.findViewById(R.id.txt_month_k);
		txt_minby_k = (TextView) view.findViewById(R.id.txt_minby_k);
		layout_k = (FrameLayout) view.findViewById(R.id.layout_k);

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
		if(stockExCode.startsWith("sh") || stockExCode.startsWith("sz")){
			txt_min_k.setVisibility(View.VISIBLE);
		}else if(stockExCode.startsWith("us")){
			txt_min_k.setVisibility(View.VISIBLE);
		} else{
			txt_min_k.setVisibility(View.GONE);
		}
		txt_min_k.setTextColor(getResources().getColor(R.color.blue_color));
		Fragment fragment = StockCombinedChartFragment.newInstance(UrlUtils.STOCKTIMELINE+stockExCode, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		img_expand.setOnClickListener(this);
		txt_min_k.setOnClickListener(this);
		txt_five_k.setOnClickListener(this);
		txt_day_k.setOnClickListener(this);
		txt_week_k.setOnClickListener(this);
		txt_month_k.setOnClickListener(this);
		txt_minby_k.setOnClickListener(this);
	}

}
