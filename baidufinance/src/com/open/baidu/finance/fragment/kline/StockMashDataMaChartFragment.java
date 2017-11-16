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
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.Legend.LegendForm;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.components.YAxis.AxisDependency;
//import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
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
//public class StockMashDataMaChartFragment extends BaseV4Fragment<MashDataJson, StockMashDataMaChartFragment> implements OnChartValueSelectedListener {
//	private LineChart linechart;
//	private List<MashDataBean> list = new ArrayList<MashDataBean>();
//
//	public static StockMashDataMaChartFragment newInstance(String url, boolean isVisibleToUser) {
//		StockMashDataMaChartFragment fragment = new StockMashDataMaChartFragment();
//		fragment.setFragment(fragment);
//		fragment.setUserVisibleHint(isVisibleToUser);
//		fragment.url = url;
//		return fragment;
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_stock_mashdata_ma_chart, container, false);
//		linechart = (LineChart) view.findViewById(R.id.linechart);
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
//		// // no description text
//		linechart.getDescription().setEnabled(false);
//		// enable touch gestures
//		linechart.setTouchEnabled(true);
//		linechart.setDragDecelerationFrictionCoef(0.9f);
//		// enable scaling and dragging
//		linechart.setDragEnabled(false);
//		linechart.setScaleEnabled(false);
//		linechart.setDrawGridBackground(false);
//		linechart.setHighlightPerDragEnabled(false);
//
//		// if disabled, scaling can be done on x- and y-axis separately
//		linechart.setPinchZoom(false);
//
//		// set an alternative background color
//		linechart.setBackgroundColor(Color.WHITE);
//		linechart.setNoDataText("");
//		linechart.setOnChartValueSelectedListener(this);
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
//		}, StockMashDataMaChartFragment.this);
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
//
//		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//		ArrayList<Entry> yVals3 = new ArrayList<Entry>();
//		float maxLeftY = -10000;
//		float minLeftY = 10000;
//		for (int i = 0; i < list.size(); i++) {
//			yVals1.add(new Entry(i, list.get(i).getMa5().getAvgPrice()));
//			yVals2.add(new Entry(i, list.get(i).getMa10().getAvgPrice()));
//			yVals3.add(new Entry(i, list.get(i).getMa20().getAvgPrice()));
//
//			// 最大、小值
//			if (maxLeftY < list.get(i).getMa5().getAvgPrice()) {
//				maxLeftY = list.get(i).getMa5().getAvgPrice();
//			}
//			if (maxLeftY < list.get(i).getMa10().getAvgPrice()) {
//				maxLeftY = list.get(i).getMa10().getAvgPrice();
//			}
//
//			if (maxLeftY < list.get(i).getMa20().getAvgPrice()) {
//				maxLeftY = list.get(i).getMa20().getAvgPrice();
//			}
//
//			if (minLeftY > list.get(i).getMa5().getAvgPrice()) {
//				minLeftY = list.get(i).getMa5().getAvgPrice();
//			}
//
//			if (minLeftY > list.get(i).getMa10().getAvgPrice()) {
//				minLeftY = list.get(i).getMa10().getAvgPrice();
//			}
//
//			if (minLeftY > list.get(i).getMa20().getAvgPrice()) {
//				minLeftY = list.get(i).getMa20().getAvgPrice();
//			}
//
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
//		set2.setAxisDependency(AxisDependency.RIGHT);
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
//		set3.setAxisDependency(AxisDependency.RIGHT);
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
//		LineData data = new LineData(set1, set2,set3);
//		data.setValueTextColor(Color.WHITE);
//		data.setValueTextSize(9f);
//
//		// // set data
//		linechart.setData(data);
//		// linechart.animateX(2500);
//
//		// get the legend (only possible after setting data)
//		Legend l = linechart.getLegend();
//		// modify the legend ...
//		l.setForm(LegendForm.LINE);
//		// l.setTypeface(mTfLight);
//		l.setTextSize(11f);
//		// l.setTextColor(Color.WHITE);
//		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//		l.setDrawInside(false);
//		l.setEnabled(false);
//
//		XAxis xAxis = linechart.getXAxis();
//		// xAxis.setTypeface(mTfLight);
//		xAxis.setTextSize(11f);
//		xAxis.setLabelCount(4, true);
//		// xAxis.setTextColor(Color.WHITE);
//		xAxis.setDrawGridLines(false);
//		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//		xAxis.setDrawAxisLine(false);
//		xAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				// TODO Auto-generated method stub
//				return (list.get((int) value).getDate()) + "";
//			}
//		});
//
//		YAxis leftAxis = linechart.getAxisLeft();
//		// leftAxis.setDrawLabels(false);
//		leftAxis.setDrawGridLines(false);
//		leftAxis.setLabelCount(2, true);
//		leftAxis.setGranularityEnabled(false);
//		leftAxis.setDrawAxisLine(false);
//		leftAxis.setDrawTopYLabelEntry(true);
//		leftAxis.setDrawZeroLine(true);
//		leftAxis.setAxisMaximum(maxLeftY);
//		leftAxis.setAxisMinimum(minLeftY);
//		leftAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
//		leftAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				return String.format("%.2f", value) + "";
//			}
//		});
//
//		YAxis rightAxis = linechart.getAxisRight();
//		rightAxis.setEnabled(false);
//		// rightAxis.setDrawLabels(false);
//		// rightAxis.setDrawGridLines(false);
//		// rightAxis.setGranularityEnabled(false);
//		// rightAxis.setDrawAxisLine(false);
//		// rightAxis.setDrawTopYLabelEntry(true);
//		// rightAxis.setDrawZeroLine(true);
//		// rightAxis.setAxisMaximum(maxLeftY);
//		// rightAxis.setAxisMinimum(minLeftY);
//		// rightAxis.setValueFormatter(new IAxisValueFormatter() {
//		// @Override
//		// public String getFormattedValue(float value, AxisBase axis) {
//		// float rate = (value - list.get((int) value).getPreClose()) /
//		// list.get((int) value).getPreClose() * 1f;
//		// Log.d(TAG, value + "====" + ";" + list.get((int) value).getPreClose()
//		// + ";rate=" + rate);
//		// return String.format("%.2f", rate * 100f) + "%";
//		// }
//		// });
//
//		// LimitLine ll = new LimitLine(preclose, "");
//		// ll.setLineColor(Color.RED);
//		// ll.setLineWidth(1f);
//		// // ll.setTextColor(Color.BLACK);
//		// // ll.setTextSize(12f);
//		// leftAxis.addLimitLine(ll);
//
//		linechart.setDescription(null);
//		linechart.invalidate();
//
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
