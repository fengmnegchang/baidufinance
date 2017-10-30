/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午5:10:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.json.hot.FollowJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27下午5:10:26
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class FollowLineChartFragment extends BaseV4Fragment<FollowJson, FollowLineChartFragment> {
	private LineChart mChart, mChart2;

	public static FollowLineChartFragment newInstance(String url, boolean isVisibleToUser) {
		FollowLineChartFragment fragment = new FollowLineChartFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_follow_line_chart, container, false);
		mChart = (LineChart) view.findViewById(R.id.chart1);
		mChart2 = (LineChart) view.findViewById(R.id.chart2);
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

		// no description text
		mChart.getDescription().setEnabled(false);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		mChart.setDragDecelerationFrictionCoef(0.9f);

		// enable scaling and dragging
		mChart.setDragEnabled(false);
		mChart.setScaleEnabled(false);
		mChart.setDrawGridBackground(false);
		mChart.setHighlightPerDragEnabled(false);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(false);

		// set an alternative background color
		mChart.setBackgroundColor(Color.WHITE);
		mChart.setNoDataText("");

		// no description text
		mChart2.getDescription().setEnabled(false);

		// enable touch gestures
		mChart2.setTouchEnabled(true);

		mChart2.setDragDecelerationFrictionCoef(0.9f);

		// enable scaling and dragging
		mChart2.setDragEnabled(false);
		mChart2.setScaleEnabled(false);
		mChart2.setDrawGridBackground(false);
		mChart2.setHighlightPerDragEnabled(false);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart2.setPinchZoom(false);

		// set an alternative background color
		mChart2.setBackgroundColor(Color.WHITE);
		mChart2.setNoDataText("");

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
	public FollowJson call() throws Exception {
		// TODO Auto-generated method stub
		FollowJson mFollowJson = new FollowJson();
		try {
			InputStream is = getActivity().getResources().getAssets().open("theme.json");
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			String data = new String(imgdata);
			Gson gson = new Gson();

			mFollowJson = gson.fromJson(data, FollowJson.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mFollowJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(FollowJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();

		for (int i = 0; i < result.getList().size(); i++) {
			yVals1.add(new Entry(i, result.getList().get(i).getFollowPrice()));
			yVals2.add(new Entry(i, result.getList().get(i).getFollowIndex()));
		}
		LineDataSet set1;
		// create a dataset and give it a type
		set1 = new LineDataSet(yVals1, "价格趋势");

		set1.setAxisDependency(AxisDependency.LEFT);
		set1.setColor(ColorTemplate.getHoloBlue());
		set1.setCircleColor(Color.WHITE);
		set1.setLineWidth(2f);
		set1.setCircleRadius(3f);
		set1.setFillAlpha(65);
		set1.setFillColor(ColorTemplate.getHoloBlue());
		set1.setHighLightColor(Color.rgb(244, 117, 117));
		set1.setDrawCircleHole(false);
		set1.setDrawValues(false);
		set1.setDrawCircles(false);
		
		// set1.setFillFormatter(new MyFillFormatter(0f));
		// set1.setDrawHorizontalHighlightIndicator(false);
		// set1.setVisible(false);
		// set1.setCircleHoleColor(Color.WHITE);

		// create a dataset and give it a type
		LineDataSet set2 = new LineDataSet(yVals2, "热搜指数");
		set2.setAxisDependency(AxisDependency.RIGHT);
		set2.setColor(Color.RED);
		set2.setCircleColor(Color.WHITE);
		set2.setLineWidth(2f);
		set2.setCircleRadius(3f);
		set2.setFillAlpha(65);
		set2.setFillColor(Color.RED);
		set2.setDrawCircleHole(false);
		set2.setHighLightColor(Color.rgb(244, 117, 117));
		set2.setDrawValues(false);
		set2.setDrawCircles(false);
		set2.setDrawFilled(true);

		// create a data object with the datasets
		LineData data = new LineData(set1);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);
		 
		// set data
		mChart.setData(data);
		// mChart.animateX(2500);

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
		// l.setYOffset(11f);

		XAxis xAxis = mChart.getXAxis();
		// xAxis.setTypeface(mTfLight);
		xAxis.setTextSize(11f);
		// xAxis.setTextColor(Color.WHITE);
		xAxis.setDrawGridLines(false);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawAxisLine(false);
		xAxis.setDrawLabels(true);

//		YAxis leftAxis = mChart.getAxisLeft();
//		// leftAxis.setTypeface(mTfLight);
//		leftAxis.setTextColor(ColorTemplate.getHoloBlue());
//		leftAxis.setDrawGridLines(false);
//		leftAxis.setGranularityEnabled(false);

		 YAxis rightAxis = mChart.getAxisRight();
		 // rightAxis.setTypeface(mTfLight);
//		 rightAxis.setTextColor(Color.RED);
		 rightAxis.setDrawLabels(false);
		 rightAxis.setDrawGridLines(false);
		 rightAxis.setDrawZeroLine(false);
		 rightAxis.setGranularityEnabled(false);
		 mChart.setDescription(null);
		mChart.invalidate();

		// create a data object with the datasets
		LineData data2 = new LineData(set2);
		data2.setValueTextColor(Color.WHITE);
		data2.setValueTextSize(9f);

		// set data
		mChart2.setData(data2);
		// mChart.animateX(2500);

		// get the legend (only possible after setting data)
		Legend l2 = mChart2.getLegend();

		// modify the legend ...
		l2.setForm(LegendForm.LINE);
		// l.setTypeface(mTfLight);
		l2.setTextSize(11f);
		// l.setTextColor(Color.WHITE);
		l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
		l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		l2.setDrawInside(false);
		// l.setYOffset(11f);

		XAxis xAxis2 = mChart2.getXAxis();
		// xAxis2.setTypeface(mTfLight);
		xAxis2.setTextSize(11f);
		// xAxis2.setTextColor(Color.WHITE);
		xAxis2.setDrawGridLines(false);
		xAxis2.setDrawAxisLine(false);
		xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);

//		YAxis leftAxis2 = mChart2.getAxisLeft();
//		// leftAxis2.setTypeface(mTfLight);
//		leftAxis2.setTextColor(ColorTemplate.getHoloBlue());
//		leftAxis2.setDrawGridLines(false);
//		leftAxis2.setGranularityEnabled(false);
//		leftAxis2.setAxisMaximum(1600f);
//		leftAxis2.setAxisMinimum(1100f);
		
		YAxis rightAxis2 = mChart2.getAxisRight();
		 // rightAxis.setTypeface(mTfLight);
//		 rightAxis.setTextColor(Color.RED);
		 rightAxis2.setDrawLabels(false);
		 rightAxis2.setDrawGridLines(false);
		 rightAxis2.setDrawZeroLine(false);
		 rightAxis2.setGranularityEnabled(false);
		 rightAxis2.setAxisMaximum(1600f);
		 rightAxis2.setAxisMinimum(1100f);
		 
		// YAxis rightAxis = mChart.getAxisRight();
		// // rightAxis.setTypeface(mTfLight);
		// rightAxis.setTextColor(Color.RED);
		// rightAxis.setDrawGridLines(false);
		// rightAxis.setDrawZeroLine(false);
		// rightAxis.setGranularityEnabled(false);
		 mChart2.setDescription(null);
		mChart2.invalidate();
	}

}
