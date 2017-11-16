/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7下午3:51:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.kline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.kline.TimeLineBean;
import com.open.baidu.finance.json.kline.TimeLineJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7下午3:51:54
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockBarChartFragment extends BaseV4Fragment<TimeLineJson, StockBarChartFragment> implements OnChartValueSelectedListener {
	private BarChart mChart;
	private List<TimeLineBean> list = new ArrayList<TimeLineBean>();

	public static StockBarChartFragment newInstance(String url, boolean isVisibleToUser) {
		StockBarChartFragment fragment = new StockBarChartFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_bar_chart, container, false);
		mChart = (BarChart) view.findViewById(R.id.chart1);
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
		mChart.setOnChartValueSelectedListener(this);

		mChart.setDrawBarShadow(false);
		mChart.setDrawValueAboveBar(true);

//		mChart.getDescription().setEnabled(false);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		// mChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		mChart.setDrawGridBackground(false);
		mChart.setDragEnabled(false);
		mChart.setScaleEnabled(false);
		mChart.setDrawGridBackground(false);
		mChart.setHighlightPerDragEnabled(false);
		mChart.setBackgroundColor(Color.WHITE);
		mChart.setNoDataText("");
		// mChart.setDrawYLabels(false);

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
			volleyJson(url);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
		List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(), url, pageNo + "");
		Gson gson = new Gson();
		TimeLineJson mTimeLineJson = gson.fromJson(dblist.get(0).getTitle(), TimeLineJson.class);
		onCallback(mTimeLineJson);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	@Override
	public void volleyJson(final String href) {
		// TODO Auto-generated method stub
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String, String> params = new HashMap<String, String>();
		params.put("User-Agent", UrlUtils.userAgent);
		// params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href, params, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					TimeLineJson result = gson.fromJson(response.toString(), TimeLineJson.class);
					onCallback(result);

					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(response.toString());
					openbean.setDownloadurl("");
					openbean.setImgsrc("");
					openbean.setType(pageNo);
					openbean.setTypename(pageNo + "");
					openbean.setUrl(url);
					OpenDBService.insert(getActivity(), openbean);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, StockBarChartFragment.this);
		requestQueue.add(jsonObjectRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(TimeLineJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		list.addAll(result.getTimeLine());
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		for (int i = 0; i < list.size(); i++) {
			yVals1.add(new BarEntry(list.get(i).getVolume() / 100f / 10000f,i));
		}
		BarDataSet set1;
		// if (mChart.getData() != null && mChart.getData().getDataSetCount() >
		// 0) {
		// set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
		// set1.setValues(yVals1);
		// mChart.getData().notifyDataChanged();
		// mChart.notifyDataSetChanged();
		// } else {
		set1 = new BarDataSet(yVals1, "交易量");
//		set1.setDrawIcons(false);
		set1.setDrawValues(false);
		set1.setColor(getActivity().getResources().getColor(R.color.blue_dot_color));
		// set1.setColors(ColorTemplate.MATERIAL_COLORS);
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set1);

		BarData data = new BarData(new String[list.size()],dataSets);
		data.setValueTextSize(10f);
		// data.setValueTypeface(mTfLight);
//		data.setBarWidth(0.9f);
		mChart.setData(data);

//		IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart,list);
		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		// xAxis.setTypeface(mTfLight);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
//		xAxis.setGranularity(1f); // only intervals of 1 day
//		xAxis.setLabelCount(4);
//		xAxis.setValueFormatter(xAxisFormatter);

		
		YAxis leftAxis = mChart.getAxisLeft();
		// leftAxis.setTypeface(mTfLight);
//		leftAxis.setLabelCount(3, false);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setDrawGridLines(false);
		 leftAxis.setValueFormatter(new YAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, YAxis axis) {
				// TODO Auto-generated method stub
				if(value==0){
					return "万手";
				}else{
					return value+"万";
				}
			}
		});
		leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
		leftAxis.setSpaceTop(15f);
		leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setDrawGridLines(false);
		rightAxis.setDrawAxisLine(false);
		// rightAxis.setTypeface(mTfLight);
		rightAxis.setLabelCount(8, false);
		// rightAxis.setValueFormatter(custom);
		rightAxis.setSpaceTop(15f);
		rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
		rightAxis.setEnabled(false);

		Legend l = mChart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		l.setDrawInside(false);
		l.setForm(LegendForm.SQUARE);
		l.setFormSize(9f);
		l.setTextSize(11f);
		l.setXEntrySpace(4f);
		l.setEnabled(false);
		// l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });
		// l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });

//		XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
//		mv.setChartView(mChart); // For bounds control
//		mChart.setMarker(mv); // Set the marker to the chart

		mChart.setDescription(null);
		mChart.invalidate();
		// }
	}

	protected RectF mOnValueSelectedRectF = new RectF();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#
	 * onValueSelected(com.github.mikephil.charting.data.Entry,
	 * com.github.mikephil.charting.highlight.Highlight)
	 */
//	@Override
//	public void onValueSelected(Entry e, Highlight h) {
//		// TODO Auto-generated method stub
//		if (e == null)
//			return;
//
//		RectF bounds = mOnValueSelectedRectF;
//		mChart.getBarBounds((BarEntry) e, bounds);
//		MPPointF position = mChart.getPosition(e, AxisDependency.LEFT);
//
//		Log.i("bounds", bounds.toString());
//		Log.i("position", position.toString());
//
//		Log.i("x-index", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
//
//		MPPointF.recycleInstance(position);
//	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#
	 * onNothingSelected()
	 */
	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#onValueSelected(com.github.mikephil.charting.data.Entry, int, com.github.mikephil.charting.highlight.Highlight)
	 */
	@Override
	public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
		// TODO Auto-generated method stub
		
	}
}
