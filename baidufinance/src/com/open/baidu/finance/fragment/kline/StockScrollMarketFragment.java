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
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.utils.NetWorkUtils;
import com.open.android.utils.ScreenUtils;
import com.open.android.widget.ScrollableHelper.ScrollableContainer;
import com.open.android.widget.ScrollableLayout;
import com.open.android.widget.ScrollableLayout.ScrollLayoutListener;
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
public class StockScrollMarketFragment extends BaseV4Fragment<StockInfoJson, StockScrollMarketFragment> implements OnClickListener,
OnRefreshListener<ScrollableLayout>,ScrollLayoutListener,OnPageChangeListener{
	public ImageView img_expand;
	public PopupWindow popupWindow;
	public TextView txt_net_rate, txt_close, txt_open, txt_pre_close, txt_volume, txt_ex_rate,txt_msg;
	public ImageView img_msg;
	public StockInfoBean stock;
	public RelativeLayout layout_top,layout_bottom;
	public TextView txt_min_k, txt_five_k, txt_day_k, txt_week_k, txt_month_k, txt_minby_k;
	public FrameLayout layout_k;

	// viewpager
	public PullToRefreshScrollableLayout mPullToRefreshScrollableLayout;
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public CommonFragmentPagerAdapter mRankPagerAdapter;
	public ArrayList<MainTabBean> clist = new ArrayList<MainTabBean>();
	public String stockCode;//000725
	public String stockExCode;//sz000725

	public static StockScrollMarketFragment newInstance(String url,String stockExCode,String stockCode, boolean isVisibleToUser) {
		StockScrollMarketFragment fragment = new StockScrollMarketFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.stockExCode = stockExCode;
		fragment.stockExCode = stockExCode;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_market_pullscrollable_layout, container, false);
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

		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		mPullToRefreshScrollableLayout = (PullToRefreshScrollableLayout) view.findViewById(R.id.pulltorefreshscrollablelayout);
		
		img_msg  = (ImageView) view.findViewById(R.id.img_msg);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);
		layout_bottom = (RelativeLayout) view.findViewById(R.id.layout_bottom);
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
		if(stockExCode.startsWith("sh") || stockExCode.startsWith("sz")){
			txt_min_k.setVisibility(View.VISIBLE);
		}else if(stockExCode.startsWith("us")){
			txt_min_k.setVisibility(View.VISIBLE);
		} else{
			txt_min_k.setVisibility(View.GONE);
		}
		mPullToRefreshScrollableLayout.setMode(Mode.PULL_FROM_START);
		txt_min_k.setTextColor(getResources().getColor(R.color.blue_color));
		Fragment fragment = StockCombinedChartFragment.newInstance(UrlUtils.STOCKTIMELINE+stockExCode, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();

		MainTabBean bean = new MainTabBean();
		bean.setHref(UrlUtils.STOCK_ASYNCNEWSLIST_NEWS+stockExCode);
		bean.setTitle("新闻");
		clist.add(bean);

		bean = new MainTabBean();
		bean.setHref(UrlUtils.STOCK_ASYNCNEWSLIST_NOTICE+stockExCode);
		bean.setTitle("公告");
		clist.add(bean);

		bean = new MainTabBean();
		bean.setHref(UrlUtils.STOCK_ASYNCNEWSLIST_REPORT+stockExCode);
		bean.setTitle("研报");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.STOCKREPORT+stockExCode);
		bean.setTitle("m研报");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(stockExCode);
		bean.setTitle("基本面");
		clist.add(bean);
		
		titleList.clear();
		Fragment fragment2;
		for (int i = 0; i < clist.size(); i++) {
			bean = clist.get(i);
			titleList.add(bean.getTitle());
			if(i==0){
				fragment2 = StockNewsPullListFragment.newInstance(bean.getHref(),true);
			}else if(i==3){
				fragment2 = StockReportDataPullListFragment.newInstance(bean.getHref(), false);
			}else if(i==4){
				fragment2 = StockBasicInfoPullScrollFragment.newInstance(bean.getHref(), false);
			}else{
				fragment2 = StockNewsPullListFragment.newInstance(bean.getHref(),false);
			}
			listRankFragment.add(fragment2);
		}
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
		
		mPullToRefreshScrollableLayout.getRefreshableView().getHelper().setCurrentScrollableContainer((ScrollableContainer) listRankFragment.get(0));
		mPullToRefreshScrollableLayout.getRefreshableView().setScrollLayoutListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mPullToRefreshScrollableLayout.setOnRefreshListener(this);
		img_expand.setOnClickListener(this);
		txt_min_k.setOnClickListener(this);
		txt_five_k.setOnClickListener(this);
		txt_day_k.setOnClickListener(this);
		txt_week_k.setOnClickListener(this);
		txt_month_k.setOnClickListener(this);
		txt_minby_k.setOnClickListener(this);
		txt_msg.setOnClickListener(this);
		img_msg.setOnClickListener(this);
		layout_bottom.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message
	 * )
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public StockInfoJson call() throws Exception {
		// TODO Auto-generated method stub
		StockInfoJson mStockInfoJson = new StockInfoJson();
		if (NetWorkUtils.isNetworkAvailable(getActivity())) {
			mStockInfoJson.setStock(TagNewsJsoupService.parseStockInfo(url, pageNo));

			Gson gson = new Gson();
			OpenDBBean openbean = new OpenDBBean();
			openbean.setTitle(gson.toJson(mStockInfoJson));

			openbean.setDownloadurl("");
			openbean.setImgsrc("");
			openbean.setType(pageNo);
			openbean.setTypename(pageNo + "");
			openbean.setUrl(url + "STOCKINFO");
			OpenDBService.insert(getActivity(), openbean);
		} else {
			List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(), url + "STOCKINFO", pageNo + "");
			Gson gson = new Gson();
			mStockInfoJson = gson.fromJson(dblist.get(0).getTitle(), StockInfoJson.class);
		}
		return mStockInfoJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(StockInfoJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null && result.getStock() != null) {
			stock = result.getStock();
			txt_close.setText(String.format("%.2f", result.getStock().getClose()));
			txt_net_rate.setText(String.format("%.2f", result.getStock().getNetChange()) + " " + result.getStock().getNetChangeRatio());
			txt_open.setText(String.format("%.2f", result.getStock().getOpen()));
			txt_pre_close.setText(String.format("%.2f", result.getStock().getPreClose()));
			txt_volume.setText(result.getStock().getVolume());
			txt_ex_rate.setText(result.getStock().getExchangeRate());

			if(getActivity() instanceof StockScrollMarketFragmentActivity){
				if (result.getStock().getNetChange() > 0) {
					((StockScrollMarketFragmentActivity) getActivity()).setStatusBarColor(getResources().getColor(R.color.red_color));
					layout_top.setBackgroundColor(getResources().getColor(R.color.red_color));
				} else if (result.getStock().getNetChange() < 0) {
					((StockScrollMarketFragmentActivity) getActivity()).setStatusBarColor(getResources().getColor(R.color.green_color));
					layout_top.setBackgroundColor(getResources().getColor(R.color.green_color));
				} else {
					((StockScrollMarketFragmentActivity) getActivity()).setStatusBarColor(getResources().getColor(R.color.gray_53_color));
					layout_top.setBackgroundColor(getResources().getColor(R.color.gray_53_color));
				}
				((StockScrollMarketFragmentActivity) getActivity()).setCenterTitle(result.getStock().getTitle());
				((StockScrollMarketFragmentActivity) getActivity()).setCenterTimeTextValue(result.getStock().getTime());
			}else if(getActivity() instanceof StockScrollLandscapeMarketFragmentActivity){
				if (result.getStock().getNetChange() > 0) {
					((StockScrollLandscapeMarketFragmentActivity) getActivity()).setStatusBarColor(getResources().getColor(R.color.red_color));
					layout_top.setBackgroundColor(getResources().getColor(R.color.red_color));
				} else if (result.getStock().getNetChange() < 0) {
					((StockScrollLandscapeMarketFragmentActivity) getActivity()).setStatusBarColor(getResources().getColor(R.color.green_color));
					layout_top.setBackgroundColor(getResources().getColor(R.color.green_color));
				} else {
					((StockScrollLandscapeMarketFragmentActivity) getActivity()).setStatusBarColor(getResources().getColor(R.color.gray_53_color));
					layout_top.setBackgroundColor(getResources().getColor(R.color.gray_53_color));
				}
				((StockScrollLandscapeMarketFragmentActivity) getActivity()).setCenterTitle(result.getStock().getTitle());
				((StockScrollLandscapeMarketFragmentActivity) getActivity()).setCenterTimeTextValue(result.getStock().getTime());
			}
		}
		if(mPullToRefreshScrollableLayout!=null){
			mPullToRefreshScrollableLayout.onRefreshComplete();
		}
	}

	/* (non-Javadoc)
	 * @see com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener#onRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ScrollableLayout> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshScrollableLayout.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshScrollableLayout.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_expand:
			showPopupWindow();
			break;
		case R.id.txt_min_k:
			if (!"1".equals(txt_min_k.getTag())) {
				clearTag();
				txt_min_k.setTag("1");
				txt_min_k.setTextColor(getResources().getColor(R.color.blue_color));
				Fragment fragment = StockCombinedChartFragment.newInstance(UrlUtils.STOCKTIMELINE+stockExCode, true);
				getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();
			}
			break;
		case R.id.txt_five_k:
			if (!"1".equals(txt_five_k.getTag())) {
				clearTag();
				txt_five_k.setTag("1");
				txt_five_k.setTextColor(getResources().getColor(R.color.blue_color));
				Fragment fragment = StockFiveDayCombinedChartFragment.newInstance(UrlUtils.STOCKTIMELINEFIVE+stockExCode, true);
				getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();
			}
			break;
		case R.id.txt_day_k:
			if (!"1".equals(txt_day_k.getTag())) {
				clearTag();
				txt_day_k.setTag("1");
				txt_day_k.setTextColor(getResources().getColor(R.color.blue_color));
				Fragment fragment = StockMashDataCombinedChartFragment.newInstance(UrlUtils.STOCKDAYBAR+stockExCode, true);
				getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();
			}
			break;
		case R.id.txt_week_k:
			if (!"1".equals(txt_week_k.getTag())) {
				clearTag();
				txt_week_k.setTag("1");
				txt_week_k.setTextColor(getResources().getColor(R.color.blue_color));
				Fragment fragment = StockMashDataCombinedChartFragment.newInstance(UrlUtils.STOCKWEEKBAR+stockExCode, true);
				getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();
			}
			break;
		case R.id.txt_month_k:
			if (!"1".equals(txt_month_k.getTag())) {
				clearTag();
				txt_month_k.setTag("1");
				txt_month_k.setTextColor(getResources().getColor(R.color.blue_color));
				Fragment fragment = StockMashDataCombinedChartFragment.newInstance(UrlUtils.STOCKMONTHBAR+stockExCode, true);
				getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();
			}
			break;
		case R.id.txt_minby_k:
			clearTag();
			showMinPopupWindow();
			txt_minby_k.setTag("1");
			txt_minby_k.setTextColor(getResources().getColor(R.color.blue_color));
			break;
		case R.id.img_msg:
		case R.id.txt_msg:
			MNewsCommentPullListFragmentActivity.startMNewsCommentPullListFragmentActivity(getActivity(), UrlUtils.STOCK_COMMENT+stockExCode);
			break;
		default:
			break;
		}
	}

	private void clearTag() {
		txt_min_k.setTag("0");
		txt_five_k.setTag("0");
		txt_day_k.setTag("0");
		txt_week_k.setTag("0");
		txt_month_k.setTag("0");
		txt_minby_k.setTag("0");

		txt_min_k.setTextColor(getResources().getColor(R.color.black_color));
		txt_five_k.setTextColor(getResources().getColor(R.color.black_color));
		txt_week_k.setTextColor(getResources().getColor(R.color.black_color));
		txt_day_k.setTextColor(getResources().getColor(R.color.black_color));
		txt_month_k.setTextColor(getResources().getColor(R.color.black_color));
		txt_minby_k.setTextColor(getResources().getColor(R.color.black_color));
	}
	
	public void showMinPopupWindow() {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_stock_min_k_window, null);
		WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, (int) ScreenUtils.getIntToDip(getActivity(), 100), (int) ScreenUtils.getIntToDip(getActivity(), 180));
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);// 设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));
		popupWindow.showAsDropDown(txt_minby_k, 0, (int) ScreenUtils.getIntToDip(getActivity(), 5));

		TextView txt_min5 = (TextView) view.findViewById(R.id.txt_min5);
		TextView txt_min15 = (TextView) view.findViewById(R.id.txt_min15);
		TextView txt_min30 = (TextView) view.findViewById(R.id.txt_min30);
		TextView txt_min60 = (TextView) view.findViewById(R.id.txt_min60);
		txt_min5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sinaMinChart("5");
				popupWindow.dismiss();
			}
		});
		txt_min15.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sinaMinChart("15");
				popupWindow.dismiss();
			}
		});
		txt_min30.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sinaMinChart("30");
				popupWindow.dismiss();
			}
		});
		txt_min60.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sinaMinChart("60");
				popupWindow.dismiss();
			}
		});

		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// popupWindow隐藏时恢复屏幕正常透明度
				setBackgroundAlpha(1.0f);
			}
		});
	}
	
	private void sinaMinChart(String type){
		String href = "";
		if(stockExCode.startsWith("sh") || stockExCode.startsWith("sz")){
			href = UrlUtils.GETKLINEDATA_LEFT+stockExCode+"_"+type+UrlUtils.GETKLINEDATA_RIGHT+stockExCode;
		}else if(stockExCode.startsWith("us")){
			href = UrlUtils.US_GETMINK_LEFT+stockExCode.replace("us", "")+"_"+type+UrlUtils.US_GETMINK_RIGHT+stockExCode.replace("us", "")+"&type="+type+"&___qn=3";
		} 
		StockSinaMinCombinedChartFragment fragment = StockSinaMinCombinedChartFragment.newInstance(href, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_k, fragment).commit();
	}

	public void showPopupWindow() {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_stock_market_window, null);
		WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), (int) ScreenUtils.getIntToDip(getActivity(), 250));
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);// 设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));
		popupWindow.showAsDropDown(txt_net_rate, 0, (int) ScreenUtils.getIntToDip(getActivity(), 5));

		TextView txt_high = (TextView) view.findViewById(R.id.txt_high);
		TextView txt_low = (TextView) view.findViewById(R.id.txt_low);
		TextView txt_up = (TextView) view.findViewById(R.id.txt_up);
		TextView txt_down = (TextView) view.findViewById(R.id.txt_down);

		TextView txt_m915 = (TextView) view.findViewById(R.id.txt_m915);
		TextView txt_m930 = (TextView) view.findViewById(R.id.txt_m930);
		TextView txt_amount = (TextView) view.findViewById(R.id.txt_amount);
		TextView txt_rate = (TextView) view.findViewById(R.id.txt_rate);

		TextView txt_weibi = (TextView) view.findViewById(R.id.txt_weibi);
		TextView txt_volumebi = (TextView) view.findViewById(R.id.txt_volumebi);
		TextView txt_total_exmoney = (TextView) view.findViewById(R.id.txt_total_exmoney);
		TextView txt_total_money = (TextView) view.findViewById(R.id.txt_total_money);

		TextView txt_mrq = (TextView) view.findViewById(R.id.txt_mrq);
		TextView txt_jing_rate = (TextView) view.findViewById(R.id.txt_jing_rate);
		TextView txt_profit = (TextView) view.findViewById(R.id.txt_profit);
		TextView txt_jingmoney = (TextView) view.findViewById(R.id.txt_jingmoney);

		TextView txt_total_guben = (TextView) view.findViewById(R.id.txt_total_guben);
		TextView txt_ex_guben = (TextView) view.findViewById(R.id.txt_ex_guben);
		if (stock != null) {
			try {
				txt_high.setText(String.format("%.2f", stock.getHigh()));
				txt_low.setText(String.format("%.2f", stock.getLow()));
				txt_up.setText(String.format("%.2f", stock.getUp()));
				txt_down.setText(String.format("%.2f", stock.getDown()));

				txt_m915.setText(stock.getM915());
				txt_m930.setText(stock.getM930());
				txt_amount.setText(stock.getAmount());
				txt_rate.setText(stock.getRate());

				if (stock.getWeibi().contains("-")) {
					txt_weibi.setTextColor(getResources().getColor(R.color.green_color));
				} else {
					txt_weibi.setTextColor(getResources().getColor(R.color.red_color));
				}
				txt_weibi.setText(stock.getWeibi());
				txt_volumebi.setText(stock.getVolumebi());
				txt_total_exmoney.setText(stock.getTotal_exmoney());
				txt_total_money.setText(stock.getTotal_money());

				txt_mrq.setText(stock.getMrq());
				txt_jing_rate.setText(stock.getJing_rate());
				txt_profit.setText(stock.getProfit());
				txt_jingmoney.setText(stock.getJingmoney());

				txt_total_guben.setText(stock.getTotal_guben());
				txt_ex_guben.setText(stock.getEx_guben());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// popupWindow隐藏时恢复屏幕正常透明度
				setBackgroundAlpha(1.0f);
			}
		});
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 *            屏幕透明度0.0-1.0 1表示完全不透明
	 */
	public void setBackgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha;
		getActivity().getWindow().setAttributes(lp);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.widget.ScrollableLayout.ScrollLayoutListener#onScrollLayout(com.open.android.widget.ScrollableLayout, boolean, int)
	 */
	@Override
	public void onScrollLayout(ScrollableLayout view, boolean isSmoothTop, int scrollDistence) {
		// TODO Auto-generated method stub
		
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
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		try {
			if (mPullToRefreshScrollableLayout.getRefreshableView() != null) {
				mPullToRefreshScrollableLayout.getRefreshableView().getHelper().setCurrentScrollableContainer(((ScrollableContainer) listRankFragment.get(position)));
			}
		} catch (Exception e) {
		}
	}

}
