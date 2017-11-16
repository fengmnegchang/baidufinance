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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
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
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.widget.ScrollableHelper.ScrollableContainer;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.kline.StockSallBuyAddapter;
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
public class StockCombinedChartFragment extends BaseV4Fragment<TimeLineJson, StockCombinedChartFragment> 
implements ScrollableContainer,OnClickListener{
	private CombinedChart linechart,barchart;
	private List<TimeLineBean> list = new ArrayList<TimeLineBean>();
	private float maxLeftY = -10000;
	private float minLeftY = 10000;
	private float maxVolume = -10000;
	private float preclose = 0;
	private TextView txt_time,txt_price,txt_rate,txt_volume;
	private View view;
	//五档
	private TextView txt_five_sallbuy,txt_sallbuy_list;
	private ListView listview;
	private StockSallBuyAddapter mStockSallBuyAddapter;
	private List<TimeLineBean> bsList = new ArrayList<TimeLineBean>();
	private int bstype;
	private TimeLineJson mTimeLineJson;
	
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
		linechart = (CombinedChart) view.findViewById(R.id.linechart);
		barchart = (CombinedChart) view.findViewById(R.id.barchart);
		
		txt_time = (TextView) view.findViewById(R.id.txt_time);
		txt_price = (TextView) view.findViewById(R.id.txt_price);
		txt_rate = (TextView) view.findViewById(R.id.txt_rate);
		txt_volume = (TextView) view.findViewById(R.id.txt_volume);
		
		txt_five_sallbuy = (TextView) view.findViewById(R.id.txt_five_sallbuy);
		txt_sallbuy_list = (TextView) view.findViewById(R.id.txt_sallbuy_list);
		listview = (ListView) view.findViewById(R.id.listview);
		return view;
	}

	/* (non-Javadoc)
	 * @see com.open.android.widget.ScrollableHelper.ScrollableContainer#getScrollableView()
	 */
	@Override
	public View getScrollableView() {
		// TODO Auto-generated method stub
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		txt_five_sallbuy.setOnClickListener(this);
		txt_sallbuy_list.setOnClickListener(this);
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
		mStockSallBuyAddapter = new StockSallBuyAddapter(getActivity(), bsList);
		listview.setAdapter(mStockSallBuyAddapter);
		
//		linechart.getDescription().setEnabled(false);
		linechart.setBackgroundColor(Color.WHITE);
		linechart.setDrawGridBackground(false);
		linechart.setDrawBarShadow(false);
//		linechart.setHighlightFullBarEnabled(false);
		// draw bars behind lines
		linechart.setDrawOrder(new DrawOrder[] { DrawOrder.LINE, DrawOrder.BAR });
		linechart.setDrawValueAboveBar(true);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		// linechart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		linechart.setPinchZoom(false);
		linechart.setDrawGridBackground(false);
		linechart.setNoDataText("");
//		linechart.setDrawBorders(true);//是否绘制边线
//		linechart.setBorderWidth(1);//边线宽度，单位dp
//		linechart.setBorderColor(Color.GRAY);
//		linechart.setDragEnabled(true);//启用图表拖拽事件
		linechart.setScaleEnabled(false);
		linechart.setScaleYEnabled(false);//启用Y轴上的缩放
		
		
//		barchart.setDrawBorders(true);//是否绘制边线
//		barchart.setBorderWidth(1);//边线宽度，单位dp
//		barchart.setBorderColor(Color.GRAY);
//		barchart.getDescription().setEnabled(false);
		barchart.setBackgroundColor(Color.WHITE);
		barchart.setDrawGridBackground(false);
		barchart.setDrawBarShadow(false);
//		barchart.setHighlightFullBarEnabled(false);
		// draw bars behind lines
		barchart.setDrawOrder(new DrawOrder[] { DrawOrder.BAR ,DrawOrder.LINE });
		barchart.setDrawValueAboveBar(true);

		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		// linechart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		barchart.setPinchZoom(false);
		barchart.setDrawGridBackground(false);
		barchart.setHighlightPerDragEnabled(false);
		barchart.setNoDataText("");
//		barchart.setDragEnabled(true);//启用图表拖拽事件
		barchart.setScaleEnabled(false);
		barchart.setScaleYEnabled(false);//启用Y轴上的缩放
		
		
		linechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
				// TODO Auto-generated method stub
//				Highlight highlight = new Highlight(h.getX(), h.getY(), h.getDataSetIndex());
//				float touchY = h.getYPx() - linechart.getHeight();
//                highlight.setDraw(h.getX(), touchY);
//                barchart.highlightValues(new Highlight[]{highlight});
				
				 Highlight highlight = new Highlight(h.getXIndex(), h.getValue(), h.getDataIndex(), h.getDataSetIndex());
				 float touchY = h.getTouchY() - linechart.getHeight();
	                Highlight h1 = barchart.getHighlightByTouchPoint(h.getXIndex(), touchY);
	                highlight.setTouchY(touchY);
	                if (null == h1) {
	                    highlight.setTouchYValue(0);
	                } else {
	                    highlight.setTouchYValue(h1.getTouchYValue());
	                }
	                barchart.highlightValues(new Highlight[]{highlight});
                
                txt_time.setText(""+list.get((int)e.getXIndex()).getTime()/100000);
                txt_price.setText("价 "+String.format("%.2f", list.get((int)e.getXIndex()).getPrice()));
                txt_rate.setText("幅 "+String.format("%.2f",list.get((int)e.getXIndex()).getNetChangeRatio())+"%");
                txt_volume.setText("量 "+String.format("%.2f",list.get((int)e.getXIndex()).getVolume()/100f/10000f)+"万手");
			}
			
			@Override
			public void onNothingSelected() {
				// TODO Auto-generated method stub
				barchart.highlightValue(null);
			}
		});

		barchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
				// TODO Auto-generated method stub
//				Highlight highlight = new Highlight(h.getX(), h.getY(), h.getDataSetIndex());
//                float touchY = h.getYPx() + linechart.getHeight();
//                highlight.setDraw(h.getX(), touchY);
//                linechart.highlightValues(new Highlight[]{highlight});
				 
	                
//                txt_time.setText(""+list.get((int)e.getX()).getTime()/100000);
//                txt_price.setText("价 "+String.format("%.2f", list.get((int)e.getX()).getPrice()));
//                txt_rate.setText("幅 "+String.format("%.2f",list.get((int)e.getX()).getNetChangeRatio())+"%");
//                txt_volume.setText("量 "+String.format("%.2f",list.get((int)e.getX()).getVolume()/100f/10000f)+"万手");
				
				 Highlight highlight = new Highlight(h.getXIndex(), h.getValue(), h.getDataIndex(), h.getDataSetIndex());
				 float touchY = h.getTouchY() - linechart.getHeight();
	                Highlight h1 = linechart.getHighlightByTouchPoint(h.getXIndex(), touchY);
	                highlight.setTouchY(touchY);
	                if (null == h1) {
	                    highlight.setTouchYValue(0);
	                } else {
	                    highlight.setTouchYValue(h1.getTouchYValue());
	                }
	                linechart.highlightValues(new Highlight[]{highlight});
                
                txt_time.setText(""+list.get((int)e.getXIndex()).getTime()/100000);
                txt_price.setText("价 "+String.format("%.2f", list.get((int)e.getXIndex()).getPrice()));
                txt_rate.setText("幅 "+String.format("%.2f",list.get((int)e.getXIndex()).getNetChangeRatio())+"%");
                txt_volume.setText("量 "+String.format("%.2f",list.get((int)e.getXIndex()).getVolume()/100f/10000f)+"万手");
			}
			@Override
			public void onNothingSelected() {
				// TODO Auto-generated method stub
				linechart.highlightValue(null);
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
		mTimeLineJson = result;
		bsList.clear();
		if(bstype==0){
			for(int i=result.getAsk().size()-1;i>=0;i--){
				TimeLineBean bean = result.getAsk().get(i);
				bean.setType(bstype);
				bean.setBsLevel("卖"+(result.getAsk().size()-i));
				bsList.add(bean);
			}
			
			for(int i=0;i<result.getBid().size();i++){
				TimeLineBean bean = result.getBid().get(i);
				bean.setType(bstype);
				bean.setBsLevel("买"+(i+1));
				bsList.add(bean);
			}
		}else{
			int size = result.getTick().size();
			if(result.getTick().size()>10){
				size =10;
			}
			for(int i=0;i<size;i++){
				TimeLineBean bean = result.getTick().get(i);
				bean.setType(bstype);
				bsList.add(bean);
			}
		}
		mStockSallBuyAddapter.notifyDataSetChanged();
		
		list.clear();
		list.addAll(result.getTimeLine());
        
		CombinedData data = new CombinedData();
		data.setData(generateLineData());
		data.setData(generateNullBarData());
		// data.setValueTypeface(mTfLight);
		linechart.setData(data);
		linechart.invalidate();
		
		
		CombinedData bardata = new CombinedData();
		bardata.setData(generateNullLineData());
		bardata.setData(generateBarData());
		// data.setValueTypeface(mTfLight);
		barchart.setData(bardata);
//		barchart.setScaleMinima(2.5f, 1f);
		barchart.invalidate();
		
		txt_time.setText(""+list.get(0).getTime()/100000);
        txt_price.setText("价 "+String.format("%.2f", list.get(0).getPrice()));
        txt_rate.setText("幅 "+String.format("%.2f",list.get(0).getNetChangeRatio())+"%");
        txt_volume.setText("量 "+String.format("%.2f",list.get(0).getVolume()/100f/10000f)+"万手");
	}
	
	private BarData generateNullBarData(){
		 //需要添加一个假的bar，才能用使用自定义的高亮
		ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
		for (int i = 0; i < list.size(); i++) {
			float volume =  list.get(i).getVolume() / 100f / 10000f;
			entries1.add(new BarEntry(volume,i));
		}
        BarDataSet set = new BarDataSet(entries1, "");
        set.setHighlightEnabled(true);
        set.setHighLightAlpha(255);
        set.setHighLightColor(getResources().getColor(R.color.yellow_color));
        set.setDrawValues(false);
        set.setColor(getResources().getColor(R.color.transparent_color));

        BarData barData = new BarData(new String[list.size()],set);
        barData.setHighlightEnabled(true);
        return barData;
	}
	
	private LineData generateNullLineData() {
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		for (int i = 0; i < list.size(); i++) {
//			yVals1.add(new Entry(i, list.get(i).getAvgPrice()));
//			yVals2.add(new Entry(i, list.get(i).getPrice()));
			yVals1.add(new Entry(0,i));
			yVals2.add(new Entry(0,i));
		}
        
        LineDataSet set1;
		// create a dataset and give it a type
		set1 = new LineDataSet(yVals1, "平均价");
		set1.setAxisDependency(AxisDependency.LEFT);
		set1.setColor(getActivity().getResources().getColor(R.color.transparent_color));
		set1.setCircleColor(Color.WHITE);
		set1.setLineWidth(2f);
		set1.setCircleRadius(3f);
		set1.setFillAlpha(65);
		set1.setFillColor(getActivity().getResources().getColor(R.color.transparent_color));
		set1.setHighLightColor(Color.rgb(244, 117, 117));
		set1.setDrawCircleHole(false);
		set1.setDrawValues(false);
		set1.setDrawCircles(false);

		// create a dataset and give it a type
		LineDataSet set2 = new LineDataSet(yVals2, "分时");
		set2.setAxisDependency(AxisDependency.RIGHT);
		set2.setColor(getActivity().getResources().getColor(R.color.transparent_color));
		set2.setCircleColor(Color.WHITE);
		set2.setLineWidth(2f);
		set2.setCircleRadius(3f);
		set2.setFillAlpha(65);
		set2.setFillColor(getActivity().getResources().getColor(R.color.transparent_color));
		set2.setDrawCircleHole(false);
		set2.setHighLightColor(Color.rgb(244, 117, 117));
		set2.setDrawValues(false);
		set2.setDrawCircles(false);
//		set2.setDrawFilled(true);
		
		 ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
		 sets.add(set1);
		 sets.add(set2);
		 
		LineData lineData = new LineData(new String[list.size()],sets);
	    lineData.setHighlightEnabled(true);
	        
        return lineData;
	}

	private LineData generateLineData() {
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		
		for (int i = 0; i < list.size(); i++) {
			yVals1.add(new Entry( list.get(i).getAvgPrice(),i));
			yVals2.add(new Entry( list.get(i).getPrice(),i));
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
		ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
		 sets.add(set1);
		 sets.add(set2);
		LineData data = new LineData(new String[list.size()],sets);
		data.setValueTextColor(Color.WHITE);
		data.setValueTextSize(9f);

		// get the legend (only possible after setting data)
		Legend l = linechart.getLegend();
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

		XAxis xAxis = linechart.getXAxis();
		// xAxis.setTypeface(mTfLight);
		xAxis.setTextSize(11f);
//		xAxis.setLabelCount(3, true);
		// xAxis.setTextColor(Color.WHITE);
		xAxis.setDrawGridLines(false);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawAxisLine(true);
		xAxis.setValueFormatter(new XAxisValueFormatter() {
			@Override
			public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
				// TODO Auto-generated method stub
				return (list.get(index).getTime() / 100000) + "";
			}
		});

		YAxis leftAxis = linechart.getAxisLeft();
		// leftAxis.setDrawLabels(false);
		leftAxis.setDrawGridLines(false);
		leftAxis.setGranularityEnabled(false);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setDrawTopYLabelEntry(true);
		leftAxis.setDrawZeroLine(true);
		leftAxis.setLabelCount(3, true);
		leftAxis.setAxisMaxValue(maxLeftY);
		leftAxis.setAxisMinValue(minLeftY);
		leftAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
		leftAxis.setValueFormatter(new YAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, YAxis axis) {
				return String.format("%.2f", value) + "";
			}
		});
		LimitLine ll = new LimitLine(preclose, "");
	    ll.setLineColor(Color.DKGRAY);
	    ll.setLineWidth(1f);
//	    ll.setTextColor(Color.BLACK);
//	    ll.setTextSize(12f);
	    ll.enableDashedLine(10f, 10f, 0f);
	    leftAxis.addLimitLine(ll);
	    

		YAxis rightAxis = linechart.getAxisRight();
		// rightAxis.setEnabled(false);
		// rightAxis.setDrawLabels(false);
		rightAxis.setDrawGridLines(false);
		rightAxis.setGranularityEnabled(false);
		rightAxis.setLabelCount(3, true);
		rightAxis.setDrawAxisLine(false);
		rightAxis.setDrawTopYLabelEntry(true);
		rightAxis.setDrawZeroLine(true);
		rightAxis.setAxisMaxValue(maxLeftY);
		rightAxis.setAxisMinValue(minLeftY);
		rightAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
		rightAxis.setValueFormatter(new YAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, YAxis axis) {
				float rate = (value - list.get((int) value).getPreClose()) / list.get((int) value).getPreClose() * 1f;
				Log.d(TAG, value + "====" + ";" + list.get((int) value).getPreClose() + ";rate=" + rate);
				return String.format("%.2f", rate * 100f) + "%";
			}
		});

		return data;
	}

	private BarData generateBarData() {
		ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
		for (int i = 0; i < list.size(); i++) {
			float volume =  list.get(i).getVolume() / 100f / 10000f;
			entries1.add(new BarEntry( volume,i));
		}
		 
		BarDataSet set1 = new BarDataSet(entries1, "交易量");
		set1.setValueTextSize(10f);
//		set1.setDrawIcons(false);
		set1.setDrawValues(false);
		set1.setColor(getActivity().getResources().getColor(R.color.blue_dot_color));
//		set1.setAxisDependency(YAxis.AxisDependency.LEFT);
		set1.setHighlightEnabled(true);
        set1.setHighLightColor(getResources().getColor(R.color.yellow_color));
        
		float barWidth = 0.9f;
		ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
		dataSets.add(set1);
		
		BarData data = new BarData(new String[list.size()],dataSets);
		data.setValueTextSize(10f);
		// data.setValueTypeface(mTfLight);
//		data.setBarWidth(barWidth);
		// make this BarData object grouped
		// d.groupBars(0, groupSpace, barSpace); // start at x = 0
		
//		IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart, list);
		XAxis xAxis = barchart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
//		xAxis.setEnabled(false);
		xAxis.setDrawAxisLine(true);
		xAxis.setDrawGridLines(false);
//		xAxis.setDrawGridLines(false);
//		xAxis.setDrawAxisLine(false);
//		xAxis.setGranularity(1f); // only intervals of 1 day
		xAxis.setValueFormatter(new XAxisValueFormatter() {
			@Override
			public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
				// TODO Auto-generated method stub
				return "";
			}
		});

		YAxis leftAxis = barchart.getAxisLeft();
		leftAxis.setEnabled(false);
		// leftAxis.setTypeface(mTfLight);
//		leftAxis.setDrawAxisLine(false);
//		leftAxis.setDrawGridLines(false);
//		leftAxis.setLabelCount(3, true);
//		leftAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				// TODO Auto-generated method stub
//				if (value == 0) {
//					return "万手";
//				} else {
//					return String.format("%.2f", value);
//				}
//			}
//		});
//		leftAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
//		leftAxis.setSpaceTop(15f);
//		leftAxis.setAxisMaximum(maxVolume);
//		leftAxis.setAxisMinimum(0);
		
		YAxis rightAxis = barchart.getAxisRight();
		rightAxis.setEnabled(false);
//		rightAxis.setDrawLabels(false);
//		rightAxis.setDrawGridLines(false);
//		rightAxis.setLabelCount(3, true);
//		rightAxis.setGranularityEnabled(false);
//		rightAxis.setDrawAxisLine(false);
//		rightAxis.setDrawTopYLabelEntry(true);
//		rightAxis.setDrawZeroLine(true);
//		rightAxis.setAxisMaximum(maxVolume);
//		rightAxis.setAxisMinimum(0);
//		rightAxis.setValueFormatter(new IAxisValueFormatter() {
//			@Override
//			public String getFormattedValue(float value, AxisBase axis) {
//				// TODO Auto-generated method stub
//				if (value == 0) {
//					return "万手";
//				} else {
//					return String.format("%.2f", value);
//				}
//			}
//		});
		
		Legend l = barchart.getLegend();
//		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//		l.setDrawInside(false);
//		l.setForm(LegendForm.SQUARE);
//		l.setFormSize(9f);
//		l.setTextSize(11f);
//		l.setXEntrySpace(4f);
		l.setEnabled(false);
		// l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });
		// l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
		// "def", "ghj", "ikl", "mno" });

//		XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
//		mv.setChartView(linechart); // For bounds control
//		linechart.setMarker(mv); // Set the marker to the chart

		return data;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_five_sallbuy:
			if(bstype==1){
				bstype = 0;
				updateBsData();
				txt_five_sallbuy.setBackgroundResource(R.drawable.blue_bounds_shape);
				txt_sallbuy_list.setBackgroundResource(R.drawable.round_bounds_shape);
			}
			break;
		case R.id.txt_sallbuy_list:
			//明细
			if(bstype==0){
				bstype = 1;
				updateBsData();
				txt_sallbuy_list.setBackgroundResource(R.drawable.blue_bounds_shape);
				txt_five_sallbuy.setBackgroundResource(R.drawable.round_bounds_shape);
			}
			break;
		default:
			break;
		}
	}
	
	private void updateBsData(){
		if(mTimeLineJson==null){
			return;
		}
		bsList.clear();
		if(bstype==0){
			for(int i=mTimeLineJson.getAsk().size()-1;i>=0;i--){
				TimeLineBean bean = mTimeLineJson.getAsk().get(i);
				bean.setType(bstype);
				bean.setBsLevel("卖"+(mTimeLineJson.getAsk().size()-i));
				bsList.add(bean);
			}
			
			for(int i=0;i<mTimeLineJson.getBid().size();i++){
				TimeLineBean bean = mTimeLineJson.getBid().get(i);
				bean.setType(bstype);
				bean.setBsLevel("买"+(i+1));
				bsList.add(bean);
			}
		}else{
			int size = mTimeLineJson.getTick().size();
			if(mTimeLineJson.getTick().size()>10){
				size =10;
			}
			for(int i=0;i<size;i++){
				TimeLineBean bean = mTimeLineJson.getTick().get(i);
				bean.setType(bstype);
				bsList.add(bean);
			}
		}
		mStockSallBuyAddapter.notifyDataSetChanged();
	}
}
