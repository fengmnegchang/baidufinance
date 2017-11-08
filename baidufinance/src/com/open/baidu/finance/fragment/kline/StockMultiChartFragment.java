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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
public class StockMultiChartFragment extends BaseV4Fragment<TimeLineJson, StockMultiChartFragment>  {
	private LineChart mLineChart;
	private BarChart mBarChart;
	private List<TimeLineBean> list = new ArrayList<TimeLineBean>();
	private TextView txt_time,txt_price,txt_rate,txt_volume;

	public static StockMultiChartFragment newInstance(String url, boolean isVisibleToUser) {
		StockMultiChartFragment fragment = new StockMultiChartFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_multi_chart, container, false);
		mLineChart = (LineChart) view.findViewById(R.id.linechart);
		mBarChart = (BarChart) view.findViewById(R.id.barchart);
		
		txt_time = (TextView) view.findViewById(R.id.txt_time);
		txt_price = (TextView) view.findViewById(R.id.txt_price);
		txt_rate = (TextView) view.findViewById(R.id.txt_rate);
		txt_volume = (TextView) view.findViewById(R.id.txt_volume);
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
		// // no description text
		mLineChart.getDescription().setEnabled(false);

		// enable touch gestures
//		mLineChart.setTouchEnabled(true);

		mLineChart.setDragDecelerationFrictionCoef(0.9f);

		// enable scaling and dragging
		mLineChart.setScaleEnabled(false);
		mLineChart.setScaleYEnabled(false);//启用Y轴上的缩放
		mLineChart.setDrawGridBackground(false);
		mLineChart.setDragEnabled(true);//启用图表拖拽事件
//		mLineChart.setHighlightPerDragEnabled(false);

		// if disabled, scaling can be done on x- and y-axis separately
		mLineChart.setPinchZoom(false);
		mLineChart.setDrawBorders(true);//是否绘制边线
		mLineChart.setBorderWidth(1);//边线宽度，单位dp
		mLineChart.setBorderColor(Color.GRAY);
		// set an alternative background color
		mLineChart.setBackgroundColor(Color.WHITE);
		mLineChart.setNoDataText("");
		


		mBarChart.setDrawBarShadow(false);
		mBarChart.setDrawValueAboveBar(true);
		mBarChart.setDrawBorders(true);//是否绘制边线
		mBarChart.setBorderWidth(1);//边线宽度，单位dp
		mBarChart.setBorderColor(Color.GRAY);
		mBarChart.getDescription().setEnabled(false);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		// mBarChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		mBarChart.setPinchZoom(false);
		mBarChart.setDrawGridBackground(false);
		mBarChart.setDragEnabled(true);//启用图表拖拽事件
		mBarChart.setScaleEnabled(false);
		mBarChart.setScaleYEnabled(false);//启用Y轴上的缩放
		mBarChart.setDrawGridBackground(false);
//		mBarChart.setHighlightPerDragEnabled(false);
		mBarChart.setBackgroundColor(Color.WHITE);
		mBarChart.setNoDataText("");
		// enable touch gestures
//		mBarChart.setTouchEnabled(true);
		// mChart.setDrawYLabels(false);
		

		mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry e, Highlight h) {
				// TODO Auto-generated method stub
				Highlight highlight = new Highlight(h.getX(), h.getY(), h.getDataSetIndex());
				float touchY = h.getYPx() - mLineChart.getHeight();
                highlight.setDraw(h.getX(), touchY);
                mBarChart.highlightValues(new Highlight[]{highlight});
                
                txt_time.setText(""+list.get((int)e.getX()).getTime()/100000);
                txt_price.setText("价 "+String.format("%.2f", list.get((int)e.getX()).getPrice()));
                txt_rate.setText("幅 "+String.format("%.2f",list.get((int)e.getX()).getNetChangeRatio())+"%");
                txt_volume.setText("量 "+String.format("%.2f",list.get((int)e.getX()).getVolume()/100f/10000f)+"万手");
			}
			
			@Override
			public void onNothingSelected() {
				// TODO Auto-generated method stub
				mBarChart.highlightValue(null);
			}
		});

		mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry e, Highlight h) {
				// TODO Auto-generated method stub
				Highlight highlight = new Highlight(h.getX(), h.getY(), h.getDataSetIndex());
                float touchY = h.getYPx() + mLineChart.getHeight();
                highlight.setDraw(h.getX(), touchY);
                mLineChart.highlightValues(new Highlight[]{highlight});
                txt_time.setText(""+list.get((int)e.getX()).getTime()/100000);
                txt_price.setText("价 "+String.format("%.2f", list.get((int)e.getX()).getPrice()));
                txt_rate.setText("幅 "+String.format("%.2f",list.get((int)e.getX()).getNetChangeRatio())+"%");
                txt_volume.setText("量 "+String.format("%.2f",list.get((int)e.getX()).getVolume()/100f/10000f)+"万手");
			}
			@Override
			public void onNothingSelected() {
				// TODO Auto-generated method stub
				mLineChart.highlightValue(null);
			}
		});
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
		}, StockMultiChartFragment.this);
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

		linechart();
		barchart();
		
		txt_time.setText(""+list.get(0).getTime()/100000);
        txt_price.setText("价 "+String.format("%.2f", list.get(0).getPrice()));
        txt_rate.setText("幅 "+String.format("%.2f",list.get(0).getNetChangeRatio())+"%");
        txt_volume.setText("量 "+String.format("%.2f",list.get(0).getVolume()/100f/10000f)+"万手");
	}

	private void linechart() {
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		float maxLeftY = -10000;
		float minLeftY = 10000;
		float preclose = 0;
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

		// set1.setFillFormatter(new MyFillFormatter(0f));
		// set1.setDrawHorizontalHighlightIndicator(false);
		// set1.setVisible(false);
		// set1.setCircleHoleColor(Color.WHITE);

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

		// // set data
		mLineChart.setData(data);
		// mLineChart.animateX(2500);

		// get the legend (only possible after setting data)
		Legend l = mLineChart.getLegend();
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

		XAxis xAxis = mLineChart.getXAxis();
		// xAxis.setTypeface(mTfLight);
		xAxis.setTextSize(11f);
		xAxis.setLabelCount(3, true);
		// xAxis.setTextColor(Color.WHITE);
		xAxis.setDrawGridLines(false);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawAxisLine(false);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				// TODO Auto-generated method stub
				return (list.get((int) value).getTime() / 100000) + "";
			}
		});

		YAxis leftAxis = mLineChart.getAxisLeft();
		// leftAxis.setDrawLabels(false);
		leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
		leftAxis.setDrawGridLines(false);
		leftAxis.setGranularityEnabled(false);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setLabelCount(3, true);
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

		YAxis rightAxis = mLineChart.getAxisRight();
		rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
		// rightAxis.setEnabled(false);
		// rightAxis.setDrawLabels(false);
		rightAxis.setLabelCount(3, true);
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
		
		LimitLine ll = new LimitLine(preclose, "");
	    ll.setLineColor(Color.DKGRAY);
	    ll.setLineWidth(1f);
//	    ll.setTextColor(Color.BLACK);
//	    ll.setTextSize(12f);
	    ll.enableDashedLine(10f, 10f, 0f);
	    leftAxis.addLimitLine(ll);

		mLineChart.setDescription(null);
		mLineChart.invalidate();
	}

	private void barchart() {
		// TODO Auto-generated method stub
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		for (int i = 0; i < list.size(); i++) {
			yVals1.add(new BarEntry(i, list.get(i).getVolume() / 100f / 10000f));
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
		set1.setDrawIcons(false);
		set1.setDrawValues(false);
		set1.setColor(getActivity().getResources().getColor(R.color.blue_dot_color));
		set1.setHighlightEnabled(true);
        set1.setHighLightColor(getResources().getColor(R.color.yellow_color));
        
		// set1.setColors(ColorTemplate.MATERIAL_COLORS);
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set1);

		BarData data = new BarData(dataSets);
		data.setValueTextSize(10f);
		// data.setValueTypeface(mTfLight);
		data.setBarWidth(0.8f);
		mBarChart.setData(data);
		

		IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mBarChart, list);
		XAxis xAxis = mBarChart.getXAxis();
		xAxis.setEnabled(false);
		xAxis.setPosition(XAxisPosition.BOTTOM);
		// xAxis.setTypeface(mTfLight);
		xAxis.setDrawGridLines(false);
		xAxis.setDrawAxisLine(false);
		xAxis.setGranularity(1f); // only intervals of 1 day
		// xAxis.setLabelCount(4);
		xAxis.setValueFormatter(xAxisFormatter);

		YAxis leftAxis = mBarChart.getAxisLeft();
//		leftAxis.setEnabled(false);
		// leftAxis.setTypeface(mTfLight);
		leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
		leftAxis.setLabelCount(2, true);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setDrawGridLines(false);
		leftAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				// TODO Auto-generated method stub
				if (value <= 0) {
					return "万手";
				} else {
					return String.format("%.2f", value) + "";
				}
			}
		});
//		leftAxis.setSpaceTop(15f);
//		leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

		YAxis rightAxis = mBarChart.getAxisRight();
//		rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
		rightAxis.setEnabled(false);
		rightAxis.setDrawGridLines(false);
		rightAxis.setDrawAxisLine(false);
		rightAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				// TODO Auto-generated method stub
				return value+"万";
			}
		});
		// rightAxis.setTypeface(mTfLight);
//		rightAxis.setLabelCount(8, false);
		// rightAxis.setValueFormatter(custom);
//		rightAxis.setSpaceTop(15f);
//		rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//		rightAxis.setEnabled(false);

		Legend l = mBarChart.getLegend();
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
//		mv.setChartView(mBarChart); // For bounds control
//		mBarChart.setMarker(mv); // Set the marker to the chart

		mBarChart.setDescription(null);
		mBarChart.invalidate();
	}

	 
}
