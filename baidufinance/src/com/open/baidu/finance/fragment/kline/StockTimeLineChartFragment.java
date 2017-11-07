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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
public class StockTimeLineChartFragment extends BaseV4Fragment<TimeLineJson, StockTimeLineChartFragment> implements OnChartValueSelectedListener {
	private CombinedChart mChart;
	private List<TimeLineBean> list = new ArrayList<TimeLineBean>();

	public static StockTimeLineChartFragment newInstance(String url, boolean isVisibleToUser) {
		StockTimeLineChartFragment fragment = new StockTimeLineChartFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_time_line_chart, container, false);
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
		//
		// // no description text
		// mChart.getDescription().setEnabled(false);
		//
		// // enable touch gestures
		// mChart.setTouchEnabled(true);
		//
		// mChart.setDragDecelerationFrictionCoef(0.9f);
		//
		// // enable scaling and dragging
		// mChart.setDragEnabled(false);
		// mChart.setScaleEnabled(false);
		// mChart.setDrawGridBackground(false);
		// mChart.setHighlightPerDragEnabled(false);
		//
		// // if disabled, scaling can be done on x- and y-axis separately
		// mChart.setPinchZoom(false);
		//
		// // set an alternative background color
		// mChart.setBackgroundColor(Color.WHITE);
		// mChart.setNoDataText("");
		// mChart.setOnChartValueSelectedListener(this);

		mChart.getDescription().setEnabled(false);
		mChart.setBackgroundColor(Color.WHITE);
		mChart.setDrawGridBackground(false);
		mChart.setDrawBarShadow(false);
		mChart.setHighlightFullBarEnabled(false);

		// draw bars behind lines
		mChart.setDrawOrder(new DrawOrder[] { DrawOrder.BAR, DrawOrder.LINE });

		Legend l = mChart.getLegend();
		l.setWordWrapEnabled(true);
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		l.setDrawInside(false);

		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setDrawGridLines(false);
		// rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setDrawGridLines(false);
		// leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTH_SIDED);
		// xAxis.setAxisMinimum(0f);
		xAxis.setGranularity(1f);
		// xAxis.setValueFormatter(new IAxisValueFormatter() {
		// @Override
		// public String getFormattedValue(float value, AxisBase axis) {
		// return mMonths[(int) value % mMonths.length];
		// }
		// });

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
		}, StockTimeLineChartFragment.this);
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

		// xAxis.setAxisMaximum(data.getXMax() + 0.25f);

		mChart.setData(data);
		mChart.invalidate();

	}

	private LineData generateLineData() {
		// LineData d = new LineData();
		// ArrayList<Entry> entries = new ArrayList<Entry>();
		// for (int index = 0; index < 10; index++)
		// entries.add(new Entry(index + 0.5f, getRandom(15, 5)));

		// LineDataSet set = new LineDataSet(entries, "Line DataSet");
		// set.setColor(Color.rgb(240, 238, 70));
		// set.setLineWidth(2.5f);
		// set.setCircleColor(Color.rgb(240, 238, 70));
		// set.setCircleRadius(5f);
		// set.setFillColor(Color.rgb(240, 238, 70));
		// set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
		// set.setDrawValues(true);
		// set.setValueTextSize(10f);
		// set.setValueTextColor(Color.rgb(240, 238, 70));
		//
		// set.setAxisDependency(YAxis.AxisDependency.LEFT);
		// d.addDataSet(set);

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
		// mChart.setData(data);
		// // mChart.animateX(2500);
		//
		// // get the legend (only possible after setting data)
		// Legend l = mChart.getLegend();
		// // modify the legend ...
		// l.setForm(LegendForm.LINE);
		// // l.setTypeface(mTfLight);
		// l.setTextSize(11f);
		// // l.setTextColor(Color.WHITE);
		// l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		// l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
		// l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		// l.setDrawInside(false);
		// l.setEnabled(false);
		//
		// XAxis xAxis = mChart.getXAxis();
		// // xAxis.setTypeface(mTfLight);
		// xAxis.setTextSize(11f);
		// // xAxis.setTextColor(Color.WHITE);
		// xAxis.setDrawGridLines(false);
		// xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		// xAxis.setDrawAxisLine(false);
		// xAxis.setValueFormatter(new IAxisValueFormatter() {
		// @Override
		// public String getFormattedValue(float value, AxisBase axis) {
		// // TODO Auto-generated method stub
		// return (list.get((int) value).getTime() / 10000) + "";
		// }
		// });
		//
		// YAxis leftAxis = mChart.getAxisLeft();
		// // leftAxis.setDrawLabels(false);
		// leftAxis.setDrawGridLines(false);
		// leftAxis.setGranularityEnabled(false);
		// leftAxis.setDrawAxisLine(false);
		// leftAxis.setDrawTopYLabelEntry(true);
		// leftAxis.setDrawZeroLine(true);
		// leftAxis.setAxisMaximum(maxLeftY);
		// leftAxis.setAxisMinimum(minLeftY);
		// leftAxis.setValueFormatter(new IAxisValueFormatter() {
		// @Override
		// public String getFormattedValue(float value, AxisBase axis) {
		// return String.format("%.2f", value) + "";
		// }
		// });
		//
		// YAxis rightAxis = mChart.getAxisRight();
		// // rightAxis.setEnabled(false);
		// // rightAxis.setDrawLabels(false);
		// rightAxis.setDrawGridLines(false);
		// rightAxis.setGranularityEnabled(false);
		// rightAxis.setDrawAxisLine(false);
		// rightAxis.setDrawTopYLabelEntry(true);
		// rightAxis.setDrawZeroLine(true);
		// rightAxis.setAxisMaximum(maxLeftY);
		// rightAxis.setAxisMinimum(minLeftY);
		// rightAxis.setValueFormatter(new IAxisValueFormatter() {
		// @Override
		// public String getFormattedValue(float value, AxisBase axis) {
		// float rate = (value - list.get((int) value).getPreClose()) /
		// list.get((int) value).getPreClose() * 1f;
		// Log.d(TAG, value + "====" + ";" + list.get((int) value).getPreClose()
		// + ";rate=" + rate);
		// return String.format("%.2f", rate * 100f) + "%";
		// }
		// });
		//
		// mChart.setDescription(null);
		// mChart.invalidate();

		return data;
	}


	private BarData generateBarData() {
		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		for (int i = 0; i < list.size(); i++) {
			float val = list.get(i).getVolume()/100f/10000f;
			yVals1.add(new BarEntry(i, val));
		}
		BarDataSet set1;
		set1 = new BarDataSet(yVals1, "成交量");
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set1);
		BarData data = new BarData(dataSets);
		data.setValueTextSize(10f);
		data.setBarWidth(1f);
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
