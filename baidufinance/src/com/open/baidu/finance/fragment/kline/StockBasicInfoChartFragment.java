/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14下午2:40:19
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.kline;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.kline.StockBasicInfoBean.StockBasicInfoExt30.NetProfit;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-14下午2:40:19
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockBasicInfoChartFragment extends BaseV4Fragment<CommonJson, StockBasicInfoChartFragment> {
	private NetProfit mNetProfit;
	private CombinedChart mChart;
	private int type;
	private String chartName;
	
	public static StockBasicInfoChartFragment newInstance(String url, NetProfit mNetProfit,int type,String chartName, boolean isVisibleToUser) {
		StockBasicInfoChartFragment fragment = new StockBasicInfoChartFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.mNetProfit = mNetProfit;
		fragment.type = type;
		fragment.chartName = chartName;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_basic_info_chart, container, false);
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
//		mChart.getDescription().setEnabled(false);
		mChart.setBackgroundColor(Color.WHITE);
		mChart.setDrawGridBackground(false);
		mChart.setDrawBarShadow(false);
		mChart.setHighlightFullBarEnabled(false);
		mChart.setPinchZoom(false);
		mChart.setDrawGridBackground(false);
		mChart.setHighlightPerDragEnabled(false);
		mChart.setNoDataText("");
		mChart.setDragEnabled(false);//启用图表拖拽事件
		mChart.setScaleEnabled(false);
		mChart.setScaleYEnabled(false);//启用Y轴上的缩放

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
		rightAxis.setEnabled(false);

		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setDrawGridLines(false);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
		leftAxis.setValueFormatter(new YAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, YAxis axis) {
				return String.format("%.2f", value);
			}
		});

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM_INSIDE);
//		xAxis.setGranularity(1f);
		xAxis.setDrawGridLines(false);
		xAxis.setValueFormatter(new XAxisValueFormatter() {
			@Override
			public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
				return mNetProfit.getYear().get((int)index);
			}
		});

		CombinedData data = new CombinedData();
		data.setData(generateLineData());
		data.setData(generateBarData());
		mChart.setScaleMinima(0.8f, 1f);
		// data.setValueTypeface(mTfLight);
		mChart.setData(data);
		mChart.invalidate();
	}

	private LineData generateLineData() {
		LineData d = new LineData();
		ArrayList<Entry> entries = new ArrayList<Entry>();
		for (int index = 0; index < mNetProfit.getIndustryAvg().size(); index++) {
			if(type<2){
				entries.add(new Entry( (mNetProfit.getIndustryAvg().get(index)/10000f/10000f),index));
			}else{
				entries.add(new Entry(mNetProfit.getIndustryAvg().get(index),index));
			}
		}
		LineDataSet set = new LineDataSet(entries, "行业平均值");
//		set.setColor(Color.rgb(240, 238, 70));
		set.setColor(getActivity().getResources().getColor(R.color.blue_dot_color));
		set.setLineWidth(2.5f);
		set.setCircleColor(Color.rgb(240, 238, 70));
		set.setCircleRadius(5f);
		set.setFillColor(Color.rgb(240, 238, 70));
		set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
		set.setDrawValues(true);
		set.setValueTextSize(10f);
		set.setValueTextColor(getActivity().getResources().getColor(R.color.blue_dot_color));
		set.setAxisDependency(YAxis.AxisDependency.LEFT);
		d.addDataSet(set);
		return d;
	}

	private BarData generateBarData() {
		ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
		for (int index = 0; index < mNetProfit.getFianceData().size(); index++) {
			if(type<2){
				entries1.add(new BarEntry(mNetProfit.getFianceData().get(index)/10000f/10000f,index));
			}else{
				entries1.add(new BarEntry( mNetProfit.getFianceData().get(index),index));
			}
		}
		BarDataSet set1 = new BarDataSet(entries1, chartName);
//		set1.setColor(Color.rgb(60, 220, 78));
		set1.setValueTextColor(getActivity().getResources().getColor(R.color.red_color));
		set1.setValueTextSize(10f);
		set1.setAxisDependency(YAxis.AxisDependency.LEFT);
		set1.setValueTextSize(10f);
//		set1.setDrawIcons(false);
		set1.setDrawValues(false);
		set1.setColor(getActivity().getResources().getColor(R.color.red_color));
//		set1.setAxisDependency(YAxis.AxisDependency.LEFT);
		set1.setHighlightEnabled(true);
        set1.setHighLightColor(getResources().getColor(R.color.yellow_color));

		float groupSpace = 0.06f;
		float barSpace = 0.02f; // x2 dataset
		float barWidth = 0.45f; // x2 dataset
		// (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
		
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set1);
		BarData d = new BarData(new String[mNetProfit.getFianceData().size()],dataSets);
//		d.setBarWidth(barWidth);
		d.setValueTextSize(10f);
		// data.setValueTypeface(mTfLight);
		// make this BarData object grouped
		// d.groupBars(0, groupSpace, barSpace); // start at x = 0
		return d;
	}
}
