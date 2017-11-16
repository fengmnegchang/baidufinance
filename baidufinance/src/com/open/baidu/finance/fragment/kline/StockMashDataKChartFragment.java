///**
// *****************************************************************************************************************************************************************************
// * 
// * @author :fengguangjing
// * @createTime:2017-11-9下午3:23:52
// * @version:4.2.4
// * @modifyTime:
// * @modifyAuthor:
// * @description:
// *****************************************************************************************************************************************************************************
// */
//package com.open.baidu.finance.fragment.kline;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONObject;
//
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.github.mikephil.charting.charts.CandleStickChart;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.components.YAxis.AxisDependency;
//import com.github.mikephil.charting.data.CandleData;
//import com.github.mikephil.charting.data.CandleDataSet;
//import com.github.mikephil.charting.data.CandleEntry;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.highlight.Highlight;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
//import com.google.gson.Gson;
//import com.open.android.bean.db.OpenDBBean;
//import com.open.android.db.service.OpenDBService;
//import com.open.android.fragment.BaseV4Fragment;
//import com.open.baidu.finance.R;
//import com.open.baidu.finance.bean.kline.MashDataBean;
//import com.open.baidu.finance.json.kline.MashDataJson;
//import com.open.baidu.finance.utils.UrlUtils;
//
///**
// ***************************************************************************************************************************************************************************** 
// * 
// * @author :fengguangjing
// * @createTime:2017-11-9下午3:23:52
// * @version:4.2.4
// * @modifyTime:
// * @modifyAuthor:
// * @description:
// ***************************************************************************************************************************************************************************** 
// */
//public class StockMashDataKChartFragment extends BaseV4Fragment<MashDataJson, StockMashDataKChartFragment> implements OnChartValueSelectedListener {
//	private CandleStickChart candlestickchart;
//	private List<MashDataBean> list = new ArrayList<MashDataBean>();
//
//	public static StockMashDataKChartFragment newInstance(String url, boolean isVisibleToUser) {
//		StockMashDataKChartFragment fragment = new StockMashDataKChartFragment();
//		fragment.setFragment(fragment);
//		fragment.setUserVisibleHint(isVisibleToUser);
//		fragment.url = url;
//		return fragment;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_stock_mashdata_k_chart, container, false);
//		candlestickchart = (CandleStickChart) view.findViewById(R.id.candlestickchart);
//		return view;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
//	 */
//	@Override
//	public void initValues() {
//		// TODO Auto-generated method stub
//		super.initValues();
//		candlestickchart.setBackgroundColor(Color.WHITE);
//		candlestickchart.getDescription().setEnabled(false);
//
//		// scaling can now only be done on x- and y-axis separately
//		candlestickchart.setPinchZoom(false);
//		candlestickchart.setDrawGridBackground(false);
//		candlestickchart.setTouchEnabled(true); // enable touch gestures 
//		// setting data
//		candlestickchart.getLegend().setEnabled(false);
//		candlestickchart.setHighlightPerDragEnabled(true);
//		candlestickchart.setNoDataText("");
//		candlestickchart.setOnChartValueSelectedListener(this);
//
//		candlestickchart.setDrawBorders(true);// 是否绘制边线
//		candlestickchart.setBorderWidth(1);// 边线宽度，单位dp
//		candlestickchart.setBorderColor(Color.GRAY);
//		candlestickchart.setDragEnabled(true);// 启用图表拖拽事件
//		candlestickchart.setScaleEnabled(true);
//		candlestickchart.setScaleYEnabled(false);// 启用Y轴上的缩放
//		candlestickchart.setAutoScaleMinMaxEnabled(true);
//		
//		candlestickchart.setMinOffset(0f);
//		candlestickchart.setExtraOffsets(0f, 0f, 0f, 3f);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message
//	 * )
//	 */
//	@Override
//	public void handlerMessage(Message msg) {
//		// TODO Auto-generated method stub
//		super.handlerMessage(msg);
//		switch (msg.what) {
//		case MESSAGE_HANDLER:
//			volleyJson(url);
//			break;
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android
//	 * .volley.VolleyError)
//	 */
//	@Override
//	public void onErrorResponse(VolleyError error) {
//		// TODO Auto-generated method stub
//		super.onErrorResponse(error);
//		System.out.println(error);
//		List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(), url, pageNo + "");
//		Gson gson = new Gson();
//		MashDataJson mMashDataJson = gson.fromJson(dblist.get(0).getTitle(), MashDataJson.class);
//		onCallback(mMashDataJson);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
//	 */
//	@Override
//	public void volleyJson(final String href) {
//		// TODO Auto-generated method stub
//		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("User-Agent", UrlUtils.userAgent);
//		// params.put("Referer","https://gupiao.baidu.com/my/");
//		params.put("Cookie", UrlUtils.COOKIE);
//		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href, params, null, new Response.Listener<JSONObject>() {
//			@Override
//			public void onResponse(JSONObject response) {
//				System.out.println("href=" + href);
//				System.out.println("response=" + response.toString());
//				try {
//					Gson gson = new Gson();
//					MashDataJson result = gson.fromJson(response.toString(), MashDataJson.class);
//					onCallback(result);
//
//					OpenDBBean openbean = new OpenDBBean();
//					openbean.setTitle(response.toString());
//					openbean.setDownloadurl("");
//					openbean.setImgsrc("");
//					openbean.setType(pageNo);
//					openbean.setTypename(pageNo + "");
//					openbean.setUrl(url);
//					OpenDBService.insert(getActivity(), openbean);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		}, StockMashDataKChartFragment.this);
//		requestQueue.add(jsonObjectRequest);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
//	 */
//	@Override
//	public void onCallback(MashDataJson result) {
//		// TODO Auto-generated method stub
//		super.onCallback(result);
//		list.clear();
//		for (int i = result.getMashData().size() - 1; i >= 0; i--) {
//			list.add(result.getMashData().get(i));
//		}
//		ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();
//		for (int i = 0; i < list.size(); i++) {
//			float high = list.get(i).getKline().getHigh();
//			float low = list.get(i).getKline().getLow();
//
//			float open = list.get(i).getKline().getOpen();
//			float close = list.get(i).getKline().getClose();
//
//			yVals1.add(new CandleEntry(i, high, low, open, close, null));
//		}
//
//		CandleDataSet set1 = new CandleDataSet(yVals1, "日K");
//		set1.setDrawIcons(false);
//		set1.setDrawHorizontalHighlightIndicator(false);
//		set1.setHighlightEnabled(true);
//		set1.setAxisDependency(AxisDependency.LEFT);
//		// set1.setColor(Color.rgb(80, 80, 80));
//		set1.setShadowColor(Color.DKGRAY);
//		set1.setShadowColorSameAsCandle(true);//影线颜色与实体一致
//		set1.setShadowWidth(0.7f);//影线
////		set1.setBarSpace(1f);
//		
//		set1.setValueTextSize(10f);
//		set1.setDecreasingColor(getResources().getColor(R.color.green_color));// 设置开盘价高于收盘价的颜色
//		set1.setDecreasingPaintStyle(Paint.Style.FILL);
//		set1.setIncreasingColor(getResources().getColor(R.color.red_color));// 设置开盘价地狱收盘价的颜色
//		set1.setIncreasingPaintStyle(Paint.Style.FILL);
//		set1.setNeutralColor(getResources().getColor(R.color.black_color));// 设置开盘价等于收盘价的颜色
//		set1.setShadowColorSameAsCandle(true);
//		set1.setHighlightLineWidth(1f);
//		set1.setHighLightColor(getResources().getColor(R.color.yellow_color));
//		set1.setDrawValues(false);
//		set1.setValueTextColor(getResources().getColor(R.color.gray_53_color));
//
//		XAxis xAxis = candlestickchart.getXAxis();
//		xAxis.setLabelCount(4, true);
//		xAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				// TODO Auto-generated method stub
//				return (list.get((int) value).getDate()) + "";
//			}
//		});
//		xAxis.setDrawLabels(true); // 是否显示X坐标轴上的刻度，默认是true
//		// xAxis.setDrawGridLines(false);//是否显示X坐标轴上的刻度竖线，默认是true
//		xAxis.setDrawAxisLine(false); // 是否绘制坐标轴的线，即含有坐标的那条线，默认是true
//		xAxis.enableGridDashedLine(10f, 10f, 0f);// 虚线表示X轴上的刻度竖线(float
//													// lineLength, float
//													// spaceLength, float
//													// phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
//		xAxis.setTextColor(getResources().getColor(R.color.gray_53_color));// 设置字的颜色
//		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置值显示在什么位置
//		xAxis.setAvoidFirstLastClipping(true);// 设置首尾的值是否自动调整，避免被遮挡
//
//		YAxis leftAxis = candlestickchart.getAxisLeft();
//		// leftAxis.setEnabled(false);
//		// leftAxis.setDrawGridLines(false);
//		leftAxis.setDrawAxisLine(false);
//		leftAxis.setDrawZeroLine(false);
//		leftAxis.setDrawLabels(true);
//		leftAxis.enableGridDashedLine(10f, 10f, 0f);
//		leftAxis.setTextColor(getResources().getColor(R.color.gray_53_color));
//		// leftAxis.setGridColor(getResources().getColor(R.color.minute_grayLine));
//		leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//		leftAxis.setLabelCount(3, true); // 第一个参数是Y轴坐标的个数，第二个参数是
//											// 是否不均匀分布，true是不均匀分布
//		leftAxis.setSpaceTop(10);// 距离顶部留白
//
//		YAxis rightAxis = candlestickchart.getAxisRight();
//		rightAxis.setEnabled(false);
//		// rightAxis.setStartAtZero(false);
//
//		CandleData data = new CandleData(set1);
//
//		candlestickchart.setDragDecelerationEnabled(true);
//		candlestickchart.setDragDecelerationFrictionCoef(0.2f);
//
//		// if more than 40 entries are displayed in the chart, no values will be
//		// drawn
//		
//		candlestickchart.setData(data);
//		candlestickchart.moveViewToX(list.size() - 1);
//		//设置最小的缩放
//		candlestickchart.setScaleMinima(2.5f, 1f);
//		candlestickchart.setMaxVisibleValueCount(40);
//		candlestickchart.invalidate();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#
//	 * onValueSelected(com.github.mikephil.charting.data.Entry,
//	 * com.github.mikephil.charting.highlight.Highlight)
//	 */
//	@Override
//	public void onValueSelected(Entry e, Highlight h) {
//		// TODO Auto-generated method stub
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#
//	 * onNothingSelected()
//	 */
//	@Override
//	public void onNothingSelected() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
