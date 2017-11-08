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
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
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
import com.open.baidu.finance.widget.kline.DayAxisValueFormatter;
import com.open.baidu.finance.widget.kline.XYMarkerView;

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
public class StockCombinedChartFragment extends BaseV4Fragment<TimeLineJson, StockCombinedChartFragment> implements OnChartValueSelectedListener {
	private CombinedChart mChart;
	private List<TimeLineBean> list = new ArrayList<TimeLineBean>();
	private float maxLeftY = -10000;
	private float minLeftY = 10000;
	private float maxVolume = -10000;
	private float preclose = 0;
	public static StockCombinedChartFragment newInstance(String url, boolean isVisibleToUser) {
		StockCombinedChartFragment fragment = new StockCombinedChartFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_combined_chart, container, false);
		mChart = (CombinedChart) view.findViewById(R.id.chart1);
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
		mChart.getDescription().setEnabled(false);
		mChart.setBackgroundColor(Color.WHITE);
		mChart.setDrawGridBackground(false);
		mChart.setDrawBarShadow(false);
		mChart.setHighlightFullBarEnabled(false);
		// draw bars behind lines
		mChart.setDrawOrder(new DrawOrder[] { DrawOrder.LINE, DrawOrder.BAR });
		mChart.setOnChartValueSelectedListener(this);
		mChart.setDrawValueAboveBar(true);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		// mChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		mChart.setDragEnabled(false);
		mChart.setScaleEnabled(false);
		mChart.setDrawGridBackground(false);
		mChart.setHighlightPerDragEnabled(false);
		mChart.setNoDataText("");
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
		}, StockCombinedChartFragment.this);
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

		CombinedData data = new CombinedData();
		data.setData(generateLineData());
		data.setData(generateBarData());
		// data.setValueTypeface(mTfLight);
		mChart.setData(data);
		mChart.invalidate();
	}

	private LineData generateLineData() {
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		
		for (int i = 0; i < list.size(); i++) {
			yVals1.add(new Entry(i, list.get(i).getAvgPrice()));
			yVals2.add(new Entry(i, list.get(i).getPrice()));
			preclose = list.get(i).getPreClose();
			// 最大、小值
			if (maxLeftY < list.get(i).getPrice()) {
				maxLeftY = list.get(i).getPrice();
			}
			if (minLeftY > list.get(i).getPrice()) {
				minLeftY = list.get(i).getPrice();
			}
			
			float volume = list.get(i).getVolume() / 100f / 10000f;
			if (maxVolume < volume) {
				maxVolume = volume;
			}
		}

		if (Math.abs(maxLeftY - preclose) <= Math.abs(minLeftY - preclose)) {
			maxLeftY = maxLeftY + Math.abs(minLeftY - preclose);
		} else {
			minLeftY = preclose - Math.abs(maxLeftY - preclose);
		}

		LineDataSet set1;
		// create a dataset and give it a type
		set1 = new LineDataSet(yVals1, "平均价");
		set1.setAxisDependency(AxisDependency.LEFT);
		set1.setColor(getActivity().getResources().getColor(R.color.yellow_color));
		set1.setCircleColor(Color.WHITE);
		set1.setLineWidth(2f);
		set1.setCircleRadius(3f);
		set1.setFillAlpha(65);
		set1.setFillColor(getActivity().getResources().getColor(R.color.yellow_color));
		set1.setHighLightColor(Color.rgb(244, 117, 117));
		set1.setDrawCircleHole(false);
		set1.setDrawValues(false);
		set1.setDrawCircles(false);

		// create a dataset and give it a type
		LineDataSet set2 = new LineDataSet(yVals2, "分时");
		set2.setAxisDependency(AxisDependency.RIGHT);
		set2.setColor(getActivity().getResources().getColor(R.color.blue_dot_color));
		set2.setCircleColor(Color.WHITE);
		set2.setLineWidth(2f);
		set2.setCircleRadius(3f);
		set2.setFillAlpha(65);
		set2.setFillColor(getActivity().getResources().getColor(R.color.blue_dot_color));
		set2.setDrawCircleHole(false);
		set2.setHighLightColor(Color.rgb(244, 117, 117));
		set2.setDrawValues(false);
		set2.setDrawCircles(false);
		set2.setDrawFilled(true);

		// create a data object with the datasets
		LineData data = new LineData(set1, set2);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);

		// get the legend (only possible after setting data)
		Legend l = mChart.getLegend();
		// modify the legend ...
		l.setForm(LegendForm.LINE);
		// l.setTypeface(mTfLight);
		l.setTextSize(11f);
		// l.setTextColor(Color.WHITE);
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		l.setDrawInside(false);
		l.setEnabled(false);

//		XAxis xAxis = mChart.getXAxis();
//		// xAxis.setTypeface(mTfLight);
//		xAxis.setTextSize(11f);
//		// xAxis.setTextColor(Color.WHITE);
//		xAxis.setDrawGridLines(false);
//		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//		xAxis.setDrawAxisLine(false);
//		xAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				// TODO Auto-generated method stub
//				return (list.get((int) value).getTime() / 100000) + "";
//			}
//		});

		YAxis leftAxis = mChart.getAxisLeft();
		// leftAxis.setDrawLabels(false);
		leftAxis.setDrawGridLines(false);
		leftAxis.setGranularityEnabled(false);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setDrawTopYLabelEntry(true);
		leftAxis.setDrawZeroLine(true);
		leftAxis.setAxisMaximum(maxLeftY);
		leftAxis.setAxisMinimum(minLeftY);
		leftAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				return String.format("%.2f", value) + "";
			}
		});

		YAxis rightAxis = mChart.getAxisRight();
		// rightAxis.setEnabled(false);
		// rightAxis.setDrawLabels(false);
		rightAxis.setDrawGridLines(false);
		rightAxis.setGranularityEnabled(false);
		rightAxis.setDrawAxisLine(false);
		rightAxis.setDrawTopYLabelEntry(true);
		rightAxis.setDrawZeroLine(true);
		rightAxis.setAxisMaximum(maxLeftY);
		rightAxis.setAxisMinimum(minLeftY);
		rightAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				float rate = (value - list.get((int) value).getPreClose()) / list.get((int) value).getPreClose() * 1f;
				Log.d(TAG, value + "====" + ";" + list.get((int) value).getPreClose() + ";rate=" + rate);
				return String.format("%.2f", rate * 100f) + "%";
			}
		});

		mChart.setDescription(null);
		mChart.invalidate();

		return data;
	}

	private BarData generateBarData() {
		ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
		for (int i = 0; i < list.size(); i++) {
			float volume = Math.abs(preclose-minLeftY)*list.get(i).getVolume() /maxVolume/ 100f / 10000f+minLeftY;
//			float volume =  list.get(i).getVolume() / 100f / 10000f;
			entries1.add(new BarEntry(i, volume));
		}
		 
		BarDataSet set1 = new BarDataSet(entries1, "交易量");
		set1.setValueTextSize(10f);
		set1.setDrawIcons(false);
		set1.setDrawValues(false);
		set1.setColor(getActivity().getResources().getColor(R.color.blue_dot_color));
//		set1.setAxisDependency(YAxis.AxisDependency.LEFT);

		float barWidth = 0.9f;
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set1);
		BarData data = new BarData(dataSets);
		data.setValueTextSize(10f);
		// data.setValueTypeface(mTfLight);
		data.setBarWidth(barWidth);
		// make this BarData object grouped
		// d.groupBars(0, groupSpace, barSpace); // start at x = 0
		
		IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart, list);
		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		// xAxis.setTypeface(mTfLight);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setGranularity(1f); // only intervals of 1 day
		// xAxis.setLabelCount(4);
		xAxis.setValueFormatter(xAxisFormatter);

//		YAxis leftAxis = mChart.getAxisLeft();
//		// leftAxis.setTypeface(mTfLight);
//		// leftAxis.setLabelCount(3, false);
//		leftAxis.setDrawAxisLine(false);
//		leftAxis.setDrawGridLines(false);
//		leftAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				// TODO Auto-generated method stub
//				if (value == 0) {
//					return "万手";
//				} else {
//					return value + "万";
//				}
//			}
//		});
//		leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
//		leftAxis.setSpaceTop(15f);
//		leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

		
//		YAxis rightAxis = mChart.getAxisRight();
////		rightAxis.setEnabled(false);
////		rightAxis.setDrawLabels(false);
//		rightAxis.setDrawGridLines(false);
//		rightAxis.setGranularityEnabled(false);
//		rightAxis.setDrawAxisLine(false);
//		rightAxis.setDrawTopYLabelEntry(true);
//		rightAxis.setDrawZeroLine(true);
//		rightAxis.setAxisMaximum(maxLeftY);
//		rightAxis.setAxisMinimum(minLeftY);
//
//		Legend l = mChart.getLegend();
//		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//		l.setDrawInside(false);
//		l.setForm(LegendForm.SQUARE);
//		l.setFormSize(9f);
//		l.setTextSize(11f);
//		l.setXEntrySpace(4f);
//		l.setEnabled(false);
		// l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });
		// l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });

//		XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
//		mv.setChartView(mChart); // For bounds control
//		mChart.setMarker(mv); // Set the marker to the chart

		mChart.setDescription(null);
		mChart.invalidate();
		return data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#
	 * onValueSelected(com.github.mikephil.charting.data.Entry,
	 * com.github.mikephil.charting.highlight.Highlight)
	 */
	@Override
	public void onValueSelected(Entry e, Highlight h) {
		// TODO Auto-generated method stub

	}

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
}
