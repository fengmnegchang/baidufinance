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

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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
public class FollowLineChartFragment extends BaseV4Fragment<FollowJson, FollowLineChartFragment> implements OnChartValueSelectedListener {
	private LineChart mChart;
	private int[] mColors = new int[] { ColorTemplate.VORDIPLOM_COLORS[0], ColorTemplate.VORDIPLOM_COLORS[1], ColorTemplate.VORDIPLOM_COLORS[2] };

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

		mChart.setDrawGridBackground(false);
		mChart.getDescription().setEnabled(false);
		mChart.setDrawBorders(false);

		mChart.getAxisLeft().setEnabled(false);
		mChart.getAxisRight().setDrawAxisLine(false);
		mChart.getAxisRight().setDrawGridLines(false);
		mChart.getXAxis().setDrawAxisLine(false);
		mChart.getXAxis().setDrawGridLines(false);

		// enable touch gestures
		mChart.setTouchEnabled(true);

		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(true);

		// if disabled, scaling can be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		Legend l = mChart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
		l.setOrientation(Legend.LegendOrientation.VERTICAL);
		l.setDrawInside(false);
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
		ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
		ArrayList<Entry> values = new ArrayList<Entry>();
		for (int i = 0; i < result.getList().size(); i++) {
			double val = result.getList().get(i).getFollowIndex();
			values.add(new Entry(i, (float) val));
		}
		LineDataSet d = new LineDataSet(values, "DataSet");
		d.setLineWidth(2.5f);
		d.setCircleRadius(4f);

		int color = mColors[0];
		d.setColor(color);
		d.setCircleColor(color);
		dataSets.add(d);

		// make the first DataSet dashed
		((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
		((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
		((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

		LineData data = new LineData(dataSets);
		mChart.setData(data);
		mChart.invalidate();
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
