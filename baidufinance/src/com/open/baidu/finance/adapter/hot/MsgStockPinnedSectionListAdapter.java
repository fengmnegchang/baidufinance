/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午4:32:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.hot;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PinnedSectionListView.PinnedSectionListAdapter;
import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.hot.HotConceptPinnedSectionListFragmentActivity;
import com.open.baidu.finance.activity.hot.SentimentIndicatorFragmentActivity;
import com.open.baidu.finance.activity.hot.SurveyPinnedSectionListFragmentActivity;
import com.open.baidu.finance.bean.hot.MsgStockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26下午4:32:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MsgStockPinnedSectionListAdapter extends CommonAdapter<MsgStockBean> implements PinnedSectionListAdapter {
	public static final int ITEM_VIEW_TYPE_HEADER = 1;

	public MsgStockPinnedSectionListAdapter(Context mContext, List<MsgStockBean> list) {
		super(mContext, list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final MsgStockBean bean = (MsgStockBean) getItem(position);
		if (getItemViewType(position) == ITEM_VIEW_TYPE_HEADER) {
			convertView = mInflater.inflate(R.layout.adapter_hot_concept_itemtype, parent, false);
			TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			TextView txt_all = (TextView) convertView.findViewById(R.id.txt_all);
			txt_time.setText(bean.getTypeName());
			txt_all.setText(bean.getDateTime()+" 查看全部>");
			txt_all.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (bean.getType()) {
					case 0:
						HotConceptPinnedSectionListFragmentActivity.startHotConceptPinnedSectionListFragmentActivity(mContext, null);
						break;
					case R.id.layout_notice:
						break;
					case 2:
						SurveyPinnedSectionListFragmentActivity.startSurveyPinnedSectionListFragmentActivity(mContext, null);
						break;
					case 3:
					case 4:
						SentimentIndicatorFragmentActivity.startSentimentIndicatorFragmentActivity(mContext, null);
						break;
					default:
						break;
					}
				}
			});
		} else {
			if(bean.getType() == 2){
				convertView = mInflater.inflate(R.layout.adapter_survey_report, parent, false);
				TextView txt_count = (TextView) convertView.findViewById(R.id.txt_count);
				TextView txt_detail = (TextView) convertView.findViewById(R.id.txt_detail);
				TextView txt_stockname = (TextView) convertView.findViewById(R.id.txt_stockname);
				TextView txt_stockcode = (TextView) convertView.findViewById(R.id.txt_stockcode);
				TextView txt_survey_all = (TextView) convertView.findViewById(R.id.txt_survey_all);
				if (bean != null) {
					txt_count.setText(bean.getSurveyCount()+"");
					txt_stockname.setText(bean.getStockName());
					txt_stockcode.setText(bean.getStockCode());
					StringBuffer buffer = new StringBuffer("调研机构：");
					for(String name:bean.getSurveyNames()){
						buffer.append(name+" ");
					}
					txt_survey_all.setText(buffer.toString());
				}
			}else if(bean.getType() == 0){
				convertView = mInflater.inflate(R.layout.adapter_hot_concept, parent, false);
				TextView txt_count = (TextView) convertView.findViewById(R.id.txt_count);
				TextView txt_rate = (TextView) convertView.findViewById(R.id.txt_rate);
				TextView txt_follow = (TextView) convertView.findViewById(R.id.txt_follow);
				TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
				TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
				TextView txt_subject = (TextView) convertView.findViewById(R.id.txt_subject);
				TextView txt_stock = (TextView) convertView.findViewById(R.id.txt_stock);
				if (bean != null) {
					txt_count.setText(bean.getSurveyCount().replace("热搜指数: ", ""));
					txt_rate.setText("");
					txt_name.setText(bean.getName());
					txt_time.setText(bean.getTime());
					txt_subject.setText(bean.getSubject());
					txt_stock.setText(Html.fromHtml("相关股：<font color='#1a83ff'>"+bean.getStockName()+"</font> <font color='#f24957'>"+bean.getRate()+"</font>"));
				}
			}else{
				convertView = mInflater.inflate(R.layout.adapter_sentiment_good_bad, parent, false);
				TextView txt_count = (TextView) convertView.findViewById(R.id.txt_count);
				TextView txt_detail = (TextView) convertView.findViewById(R.id.txt_detail);
				TextView txt_stockname = (TextView) convertView.findViewById(R.id.txt_stockname);
				TextView txt_stockcode = (TextView) convertView.findViewById(R.id.txt_stockcode);
				TextView txt_survey_all = (TextView) convertView.findViewById(R.id.txt_survey_all);
				TextView txt_close = (TextView) convertView.findViewById(R.id.txt_close);
				TextView txt_rate = (TextView) convertView.findViewById(R.id.txt_close);
				if (bean != null) {
					txt_count.setText(bean.getSurveyCount()+"");
					txt_stockname.setText(bean.getStockName());
					txt_stockcode.setText(bean.getStockCode());
					txt_close.setText(bean.getClose());
					txt_rate.setText(bean.getRate());
					txt_detail.setText(bean.getMsg()+"条评论");
					
					StringBuffer buffer = new StringBuffer("舆情关键词：");
					for(String name:bean.getSurveyNames()){
						buffer.append(name+" ");
					}
					txt_survey_all.setText(buffer.toString());
				}
			}
			
		}
		return convertView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.handmark.pulltorefresh.library.PinnedSectionListView.
	 * PinnedSectionListAdapter#isItemViewTypePinned(int)
	 */
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return viewType == ITEM_VIEW_TYPE_HEADER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return ((MsgStockBean) getItem(position)).getViewType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
