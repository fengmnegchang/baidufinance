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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PinnedSectionListView.PinnedSectionListAdapter;
import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.hot.OrganizationSurveyBean;

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
public class SurveyPinnedSectionListAdapter extends CommonAdapter<OrganizationSurveyBean> implements PinnedSectionListAdapter {
	public static final int ITEM_VIEW_TYPE_HEADER = 1;

	public SurveyPinnedSectionListAdapter(Context mContext, List<OrganizationSurveyBean> list) {
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
		OrganizationSurveyBean bean = (OrganizationSurveyBean) getItem(position);
		if (getItemViewType(position) == ITEM_VIEW_TYPE_HEADER) {
			convertView = mInflater.inflate(R.layout.adapter_hot_concept_itemtype, parent, false);
			TextView txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			txt_time.setText(bean.getDateTime());
		} else {
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
		return ((OrganizationSurveyBean) getItem(position)).getViewType();
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
