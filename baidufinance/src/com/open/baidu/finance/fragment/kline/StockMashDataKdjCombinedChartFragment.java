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
//import android.view.DragEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnDragListener;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.github.mikephil.charting.charts.Chart;
//import com.github.mikephil.charting.charts.CombinedChart;
//import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.components.YAxis.AxisDependency;
//import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.data.CandleData;
//import com.github.mikephil.charting.data.CandleDataSet;
//import com.github.mikephil.charting.data.CandleEntry;
//import com.github.mikephil.charting.data.CombinedData;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.highlight.Highlight;
//import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
//import com.google.gson.Gson;
//import com.open.android.bean.db.OpenDBBean;
//import com.open.android.db.service.OpenDBService;
//import com.open.android.fragment.BaseV4Fragment;
//import com.open.baidu.finance.R;
//import com.open.baidu.finance.bean.kline.MashDataBean;
//import com.open.baidu.finance.json.kline.MashDataJson;
//import com.open.baidu.finance.utils.CoupleChartGestureListener;
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
//public class StockMashDataKdjCombinedChartFragment extends BaseV4Fragment<MashDataJson, StockMashDataKdjCombinedChartFragment> {
//	private CombinedChart combinedchart;
//	private CombinedChart barchart;
//	private List<MashDataBean> list = new ArrayList<MashDataBean>();
//	private TextView txt_time, txt_price, txt_rate, txt_volume;
//	private float maxVolume = -10000;
//	private float minVolume = 10000;
//
//	public static StockMashDataKdjCombinedChartFragment newInstance(String url, boolean isVisibleToUser) {
//		StockMashDataKdjCombinedChartFragment fragment = new StockMashDataKdjCombinedChartFragment();
//		fragment.setFragment(fragment);
//		fragment.setUserVisibleHint(isVisibleToUser);
//		fragment.url = url;
//		return fragment;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_stock_mashdata_combined_chart, container, false);
//		combinedchart = (CombinedChart) view.findViewById(R.id.combinedchart);
//		barchart = (CombinedChart) view.findViewById(R.id.barchart);
//
//		txt_time = (TextView) view.findViewById(R.id.txt_time);
//		txt_price = (TextView) view.findViewById(R.id.txt_price);
//		txt_rate = (TextView) view.findViewById(R.id.txt_rate);
//		txt_volume = (TextView) view.findViewById(R.id.txt_volume);
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
//		combinedchart.getDescription().setEnabled(false);
//		combinedchart.setBackgroundColor(Color.WHITE);
//		combinedchart.setDrawGridBackground(false);
//		combinedchart.setDrawBarShadow(false);
//		// linechart.setHighlightFullBarEnabled(false);
//		// draw bars behind lines
//		combinedchart.setDrawOrder(new DrawOrder[] { DrawOrder.LINE, DrawOrder.CANDLE });
//		combinedchart.setDrawValueAboveBar(true);
//
//		// if more than 60 entries are displayed in the chart, no values will be
//		// drawn
//		// linechart.setMaxVisibleValueCount(60);
//
//		// scaling can now only be done on x- and y-axis separately
//		combinedchart.setPinchZoom(false);
//		combinedchart.setDrawGridBackground(false);
//		combinedchart.setNoDataText("");
//		combinedchart.setDrawBorders(true);// 是否绘制边线
//		combinedchart.setBorderWidth(1);// 边线宽度，单位dp
//		combinedchart.setBorderColor(Color.GRAY);
//		combinedchart.setDragEnabled(true);// 启用图表拖拽事件
//		// combinedchart.setScaleYEnabled(false);//启用Y轴上的缩放
//
//		// scaling can now only be done on x- and y-axis separately
//		combinedchart.setPinchZoom(false);
//		combinedchart.setTouchEnabled(true); // enable touch gestures
//		// setting data
//		combinedchart.setHighlightPerDragEnabled(true);
//
//		combinedchart.setScaleEnabled(true);
//		combinedchart.setAutoScaleMinMaxEnabled(true);
//
//		combinedchart.setMinOffset(0f);
//		combinedchart.setExtraOffsets(0f, 0f, 0f, 3f);
//
//		barchart.setDrawBorders(true);// 是否绘制边线
//		barchart.setBorderWidth(1);// 边线宽度，单位dp
//		barchart.setBorderColor(Color.GRAY);
//		barchart.getDescription().setEnabled(false);
//		barchart.setBackgroundColor(Color.WHITE);
//		barchart.setDrawGridBackground(false);
//		barchart.setDrawBarShadow(false);
//		// barchart.setHighlightFullBarEnabled(false);
//		// draw bars behind lines
//		barchart.setDrawOrder(new DrawOrder[] { DrawOrder.BAR, DrawOrder.LINE });
//		barchart.setDrawValueAboveBar(true);
//		barchart.setAutoScaleMinMaxEnabled(true);
//		// if more than 60 entries are displayed in the chart, no values will be
//		// drawn
//		// linechart.setMaxVisibleValueCount(60);
//
//		// scaling can now only be done on x- and y-axis separately
//		barchart.setPinchZoom(false);
//		barchart.setDrawGridBackground(false);
//		barchart.setHighlightPerDragEnabled(false);
//		barchart.setNoDataText("");
//		barchart.setDragEnabled(true);// 启用图表拖拽事件
//		barchart.setScaleEnabled(true);
//		barchart.setScaleYEnabled(false);// 启用Y轴上的缩放
//		barchart.setMinOffset(0f);
//		barchart.setExtraOffsets(0f, 0f, 0f, 3f);
// 
//		combinedchart.setOnChartGestureListener(new CoupleChartGestureListener(combinedchart, new Chart[] { barchart }));
//		// 将交易量控件的滑动事件传递给K线控件
//		barchart.setOnChartGestureListener(new CoupleChartGestureListener(barchart, new Chart[] { combinedchart }));
//
//		combinedchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//			@Override
//			public void onValueSelected(Entry e, Highlight h) {
//				// TODO Auto-generated method stub
//				Highlight highlight = new Highlight(h.getX(), h.getY(), h.getDataSetIndex());
//				float touchY = h.getYPx() - combinedchart.getHeight();
//				highlight.setDraw(h.getX(), touchY);
//				barchart.highlightValues(new Highlight[] { highlight });
//
//				txt_time.setText("" + list.get((int) e.getX()).getDate());
//				txt_price.setText("价 " + String.format("%.2f", list.get((int) e.getX()).getKline().getClose()));
//				txt_rate.setText("幅 " + String.format("%.2f", list.get((int) e.getX()).getKline().getNetChangeRatio()) + "%");
//				txt_volume.setText("量 " + String.format("%.2f", list.get((int) e.getX()).getKline().getVolume() / 100f / 10000f) + "万手");
//			}
//
//			@Override
//			public void onNothingSelected() {
//				// TODO Auto-generated method stub
//				barchart.highlightValue(null);
//			}
//		});
//
//		barchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//			@Override
//			public void onValueSelected(Entry e, Highlight h) {
//				// TODO Auto-generated method stub
//				Highlight highlight = new Highlight(h.getX(), h.getY(), h.getDataSetIndex());
//				float touchY = h.getYPx() + combinedchart.getHeight();
//				highlight.setDraw(h.getX(), touchY);
//				combinedchart.highlightValues(new Highlight[] { highlight });
//
//				txt_time.setText("" + list.get((int) e.getX()).getDate());
//				txt_price.setText("价 " + String.format("%.2f", list.get((int) e.getX()).getKline().getClose()));
//				txt_rate.setText("幅 " + String.format("%.2f", list.get((int) e.getX()).getKline().getNetChangeRatio()) + "%");
//				txt_volume.setText("量 " + String.format("%.2f", list.get((int) e.getX()).getKline().getVolume() / 100f / 10000f) + "万手");
//			}
//
//			@Override
//			public void onNothingSelected() {
//				// TODO Auto-generated method stub
//				combinedchart.highlightValue(null);
//			}
//		});
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
//		}, StockMashDataKdjCombinedChartFragment.this);
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
//		CombinedData data = new CombinedData();
//		data.setData(generateLineData());
//		data.setData(generateCandleData());
//		// data.setValueTypeface(mTfLight);
//		combinedchart.setData(data);
//
//		XAxis xAxis = combinedchart.getXAxis();
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
//		YAxis leftAxis = combinedchart.getAxisLeft();
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
//		YAxis rightAxis = combinedchart.getAxisRight();
//		rightAxis.setEnabled(false);
//		// rightAxis.setStartAtZero(false);
//		combinedchart.setDragDecelerationEnabled(true);
//		combinedchart.setDragDecelerationFrictionCoef(0.2f);
//
//		// if more than 40 entries are displayed in the chart, no values will be
//		// drawn
//
//		// candlestickchart.setData(data);
//		combinedchart.moveViewToX(list.size() - 1);
//		// 设置最小的缩放
//		combinedchart.setScaleMinima(2.5f, 1f);
//		combinedchart.setMaxVisibleValueCount(40);
//		combinedchart.invalidate();
//
//		CombinedData bardata = new CombinedData();
//		bardata.setData(generateNullLineData());
//		bardata.setData(generateBarData());
//		// data.setValueTypeface(mTfLight);
//		barchart.setData(bardata);
//
//		barchart.setDragDecelerationEnabled(true);
//		barchart.setDragDecelerationFrictionCoef(0.2f);
//		// candlestickchart.setData(data);
//		barchart.moveViewToX(list.size() - 1);
//		// 设置最小的缩放
//		barchart.setScaleMinima(2.5f, 1f);
//		barchart.setMaxVisibleValueCount(40);
//		barchart.invalidate();
//
//		txt_time.setText("" + list.get(0).getDate());
//		txt_price.setText("价 " + String.format("%.2f", list.get(0).getKline().getClose()));
//		txt_rate.setText("幅 " + String.format("%.2f", list.get(0).getKline().getNetChangeRatio()) + "%");
//		txt_volume.setText("量 " + String.format("%.2f", list.get(0).getKline().getVolume() / 100f / 10000f) + "万手");
//	}
//
//	private BarData generateBarData() {
//		// 需要添加一个假的bar，才能用使用自定义的高亮
//		ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
//		for (int i = 0; i < list.size(); i++) {
//			entries1.add(new BarEntry(i, 0));
//		}
//		BarDataSet set = new BarDataSet(entries1, "");
//		set.setHighlightEnabled(true);
//		set.setHighLightAlpha(255);
//		set.setHighLightColor(getResources().getColor(R.color.yellow_color));
//		set.setDrawValues(false);
//		set.setColor(getResources().getColor(R.color.transparent_color));
//
//		BarData barData = new BarData(set);
//		barData.setHighlightEnabled(true);
//		
//		
//		XAxis xAxis = barchart.getXAxis();
//		// xAxis.setPosition(XAxisPosition.BOTTOM);
//		xAxis.setEnabled(false);
//		// xAxis.setDrawGridLines(false);
//		// xAxis.setDrawAxisLine(false);
//		// xAxis.setGranularity(1f); // only intervals of 1 day
//		// xAxis.setValueFormatter(xAxisFormatter);
//
//		YAxis leftAxis = barchart.getAxisLeft();
//		// leftAxis.setTypeface(mTfLight);
//		leftAxis.setDrawAxisLine(false);
//		leftAxis.setDrawGridLines(false);
//		leftAxis.setLabelCount(2, true);
//		leftAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				// TODO Auto-generated method stub
//				return String.format("%.2f", value);
//			}
//		});
//		leftAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
//		// leftAxis.setSpaceTop(15f);
//
//		YAxis rightAxis = barchart.getAxisRight();
//		rightAxis.setEnabled(false);
//
//		Legend l = barchart.getLegend();
//		l.setEnabled(false);
//		return barData;
//	}
//
//	private LineData generateNullLineData() {
//		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//		ArrayList<Entry> yVals3 = new ArrayList<Entry>();
//		for (int i = 0; i < list.size(); i++) {
//			yVals1.add(new Entry(i, list.get(i).getKdj().getK()));
//			yVals2.add(new Entry(i, list.get(i).getKdj().getD()));
//			yVals3.add(new Entry(i, list.get(i).getKdj().getJ()));
//		}
//
//		LineDataSet set1;
//		// create a dataset and give it a type
//		set1 = new LineDataSet(yVals1, "k");
//		set1.setAxisDependency(AxisDependency.LEFT);
//		set1.setColor(getActivity().getResources().getColor(R.color.yellow_color));
//		set1.setCircleColor(Color.WHITE);
//		set1.setLineWidth(2f);
//		set1.setCircleRadius(3f);
//		set1.setFillAlpha(65);
//		set1.setFillColor(getActivity().getResources().getColor(R.color.yellow_color));
//		set1.setHighLightColor(Color.rgb(244, 117, 117));
//		set1.setDrawCircleHole(false);
//		set1.setDrawValues(false);
//		set1.setDrawCircles(false);
//
//		// create a dataset and give it a type
//		LineDataSet set2 = new LineDataSet(yVals2, "d");
//		set2.setAxisDependency(AxisDependency.LEFT);
//		set2.setColor(getActivity().getResources().getColor(R.color.blue_color));
//		set2.setCircleColor(Color.WHITE);
//		set2.setLineWidth(2f);
//		set2.setCircleRadius(3f);
//		set2.setFillAlpha(65);
//		set2.setFillColor(getActivity().getResources().getColor(R.color.blue_color));
//		set2.setDrawCircleHole(false);
//		set2.setHighLightColor(Color.rgb(244, 117, 117));
//		set2.setDrawValues(false);
//		set2.setDrawCircles(false);
//		// set2.setDrawFilled(true);
//
//		// create a dataset and give it a type
//		LineDataSet set3 = new LineDataSet(yVals3, "j");
//		set3.setAxisDependency(AxisDependency.LEFT);
//		set3.setColor(getActivity().getResources().getColor(R.color.pink_color));
//		set3.setCircleColor(Color.WHITE);
//		set3.setLineWidth(2f);
//		set3.setCircleRadius(3f);
//		set3.setFillAlpha(65);
//		set3.setFillColor(getActivity().getResources().getColor(R.color.pink_color));
//		set3.setDrawCircleHole(false);
//		set3.setHighLightColor(Color.rgb(244, 117, 117));
//		set3.setDrawValues(false);
//		set3.setDrawCircles(false);
//		// set2.setDrawFilled(true);
//
//		LineData lineData = new LineData(set1, set2, set3);
//		lineData.setHighlightEnabled(true);
//
//		return lineData;
//	}
//
//	private LineData generateLineData() {
//		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//		ArrayList<Entry> yVals3 = new ArrayList<Entry>();
//		for (int i = 0; i < list.size(); i++) {
//			yVals1.add(new Entry(i, list.get(i).getMa5().getAvgPrice()));
//			yVals2.add(new Entry(i, list.get(i).getMa10().getAvgPrice()));
//			yVals3.add(new Entry(i, list.get(i).getMa20().getAvgPrice()));
//		}
//
//		LineDataSet set1;
//		// create a dataset and give it a type
//		set1 = new LineDataSet(yVals1, "ma5");
//		set1.setAxisDependency(AxisDependency.LEFT);
//		set1.setColor(getActivity().getResources().getColor(R.color.yellow_color));
//		set1.setCircleColor(Color.WHITE);
//		set1.setLineWidth(2f);
//		set1.setCircleRadius(3f);
//		set1.setFillAlpha(65);
//		set1.setFillColor(getActivity().getResources().getColor(R.color.yellow_color));
//		set1.setHighLightColor(Color.rgb(244, 117, 117));
//		set1.setDrawCircleHole(false);
//		set1.setDrawValues(false);
//		set1.setDrawCircles(false);
//
//		// set1.setFillFormatter(new MyFillFormatter(0f));
//		// set1.setDrawHorizontalHighlightIndicator(false);
//		// set1.setVisible(false);
//		// set1.setCircleHoleColor(Color.WHITE);
//
//		// create a dataset and give it a type
//		LineDataSet set2 = new LineDataSet(yVals2, "ma10");
//		set2.setAxisDependency(AxisDependency.LEFT);
//		set2.setColor(getActivity().getResources().getColor(R.color.blue_dot_color));
//		set2.setCircleColor(Color.WHITE);
//		set2.setLineWidth(2f);
//		set2.setCircleRadius(3f);
//		set2.setFillAlpha(65);
//		set2.setFillColor(getActivity().getResources().getColor(R.color.blue_dot_color));
//		set2.setDrawCircleHole(false);
//		set2.setHighLightColor(Color.rgb(244, 117, 117));
//		set2.setDrawValues(false);
//		set2.setDrawCircles(false);
//		set2.setDrawFilled(false);
//
//		// create a dataset and give it a type
//		LineDataSet set3 = new LineDataSet(yVals3, "ma20");
//		set3.setAxisDependency(AxisDependency.LEFT);
//		set3.setColor(getActivity().getResources().getColor(R.color.pink_color));
//		set3.setCircleColor(Color.WHITE);
//		set3.setLineWidth(2f);
//		set3.setCircleRadius(3f);
//		set3.setFillAlpha(65);
//		set3.setFillColor(getActivity().getResources().getColor(R.color.pink_color));
//		set3.setDrawCircleHole(false);
//		set3.setHighLightColor(Color.rgb(244, 117, 117));
//		set3.setDrawValues(false);
//		set3.setDrawCircles(false);
//		set3.setDrawFilled(false);
//
//		// create a data object with the datasets
//		LineData data = new LineData(set1, set2, set3);
//		data.setValueTextColor(Color.WHITE);
//		data.setValueTextSize(9f);
//
//		return data;
//	}
//
//	private CandleData generateCandleData() {
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
//		set1.setShadowColorSameAsCandle(true);// 影线颜色与实体一致
//		set1.setShadowWidth(0.7f);// 影线
//		// set1.setBarSpace(1f);
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
//		CandleData data = new CandleData(set1);
//
//		return data;
//	}
//
//}
