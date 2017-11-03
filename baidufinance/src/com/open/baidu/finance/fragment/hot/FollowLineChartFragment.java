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
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.utils.NetWorkUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.hot.FollowBean;
import com.open.baidu.finance.json.article.NewsContainerJson;
import com.open.baidu.finance.json.hot.FollowJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;

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
public class FollowLineChartFragment extends BaseV4Fragment<FollowJson, FollowLineChartFragment> implements OnChartValueSelectedListener{
	private LineChart mChart;
	private TextView txt_time,txt_close,txt_hotcount;
	private List<FollowBean> list =new ArrayList<FollowBean>();
	
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
		txt_time = (TextView) view.findViewById(R.id.txt_time);
		txt_close = (TextView) view.findViewById(R.id.txt_close);
		txt_hotcount = (TextView) view.findViewById(R.id.txt_hotcount);
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
		mChart.setOnChartValueSelectedListener(this);
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
//			InputStream is = getActivity().getResources().getAssets().open("theme.json");
//			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
//			int ch;
//			while ((ch = is.read()) != -1) {
//				bytestream.write(ch);
//			}
//			byte imgdata[] = bytestream.toByteArray();
//			bytestream.close();
//			String data = new String(imgdata);
//			Gson gson = new Gson();
//
//			mFollowJson = gson.fromJson(data, FollowJson.class);
			if(NetWorkUtils.isNetworkAvailable(getActivity())){
				mFollowJson = TagNewsJsoupService.parseThmemeFollow(url, 1);
				Gson gson = new Gson();
				OpenDBBean openbean = new OpenDBBean();
				openbean.setTitle(gson.toJson(mFollowJson));
				
				openbean.setDownloadurl("");
				openbean.setImgsrc("");
				openbean.setType(pageNo);
				openbean.setTypename(pageNo+"");
				openbean.setUrl(url);
				OpenDBService.insert(getActivity(), openbean);
			}else{
				List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(),url, pageNo+"");
				Gson gson = new Gson();
				mFollowJson = gson.fromJson(dblist.get(0).getTitle(), FollowJson.class);
			}
			
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
	public void onCallback(final FollowJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		list.clear();
		for(int i=result.getList().size()-1;i>=0;i--){
			list.add(result.getList().get(i));
		}
		
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		float maxX = 1;
		float minX = 100;
		float maxY = 100;
		float minY = 2500;
		for (int i =0; i <list.size(); i++) {
			yVals1.add(new Entry(i, list.get(i).getFollowPrice()));
			yVals2.add(new Entry(i, list.get(i).getFollowIndex()));
			
			if(maxX<list.get(i).getFollowPrice()){
				maxX = list.get(i).getFollowPrice();
			}
			
			if(minX>list.get(i).getFollowPrice()){
				minX = list.get(i).getFollowPrice();
			}
			
			if(maxY<list.get(i).getFollowIndex()){
				maxY = list.get(i).getFollowIndex();
			}
			
			if(minY>list.get(i).getFollowIndex()){
				minY = list.get(i).getFollowIndex();
			}
			
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
		set2.setColor(getActivity().getResources().getColor(R.color.pink_dot_color));
		set2.setCircleColor(Color.WHITE);
		set2.setLineWidth(2f);
		set2.setCircleRadius(3f);
		set2.setFillAlpha(65);
		set2.setFillColor(getActivity().getResources().getColor(R.color.pink_dot_color));
		set2.setDrawCircleHole(false);
		set2.setHighLightColor(Color.rgb(244, 117, 117));
		set2.setDrawValues(false);
		set2.setDrawCircles(false);
		set2.setDrawFilled(true);

		// create a data object with the datasets
		LineData data = new LineData(set1,set2);
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
		l.setEnabled(false);

		XAxis xAxis = mChart.getXAxis();
		// xAxis.setTypeface(mTfLight);
		xAxis.setTextSize(11f);
		// xAxis.setTextColor(Color.WHITE);
		xAxis.setDrawGridLines(false);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawAxisLine(false);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				// TODO Auto-generated method stub
				if((int)value==0 || (int)value==list.size()-1){
					return list.get((int) value).getFollowTime();
				}else{
					return "";
				}
			}
		});

		YAxis leftAxis = mChart.getAxisLeft();
//		leftAxis.setDrawLabels(false);
		leftAxis.setDrawGridLines(false);
		leftAxis.setGranularityEnabled(false);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setDrawTopYLabelEntry(true);
		leftAxis.setDrawZeroLine(true);
		leftAxis.setAxisMaximum(maxX);
	    leftAxis.setAxisMinimum(minX*2/3);
	    leftAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				if(value==25){
					return "1100";
				}else if(value==27){
					return "1600";
				}else if(value==28){
					return "28";
				}else if(value==32){
					return "32";
				}else{
					return "";
				}
			}
		});
		
		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setEnabled(false);
//		rightAxis.setDrawLabels(false);
		rightAxis.setDrawGridLines(false);
		rightAxis.setGranularityEnabled(false);
		rightAxis.setDrawAxisLine(false);
		rightAxis.setDrawTopYLabelEntry(true);
		rightAxis.setDrawZeroLine(true);
		rightAxis.setAxisMaximum(maxY+1000);
		rightAxis.setAxisMinimum(minY);

		mChart.setDescription(null);
		mChart.invalidate();

	}

	/* (non-Javadoc)
	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#onValueSelected(com.github.mikephil.charting.data.Entry, com.github.mikephil.charting.highlight.Highlight)
	 */
	@Override
	public void onValueSelected(Entry e, Highlight h) {
		// TODO Auto-generated method stub
		try {
			FollowBean bean = list.get((int)e.getX());
			if(bean!=null){
				txt_time.setText("时间 "+bean.getFollowTime());
				txt_close.setText(Html.fromHtml(" 价格趋势 <font color='#17ABEF'>"+String.format("%.2f", bean.getFollowPrice())+"</font>"));
				txt_hotcount.setText(Html.fromHtml(" 热搜指数 <font color='#FC4A87'>"+bean.getFollowIndex()+"</font>"));
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.github.mikephil.charting.listener.OnChartValueSelectedListener#onNothingSelected()
	 */
	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub
		
	}

}
