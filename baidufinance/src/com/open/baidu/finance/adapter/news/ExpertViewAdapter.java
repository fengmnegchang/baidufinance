/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:49:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.news;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.news.ExpertViewBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:49:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ExpertViewAdapter extends CommonAdapter<ExpertViewBean> {

	public ExpertViewAdapter(Context mContext, List<ExpertViewBean> list) {
		super(mContext, list);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.adapter.CommonAdapter#getView(int,
	 * android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_expert_view, parent, false);
			viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
			viewHolder.txt_newstime = (TextView) convertView.findViewById(R.id.txt_newstime);
			viewHolder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ExpertViewBean bean = (ExpertViewBean) getItem(position);
		if (bean != null) {
			viewHolder.txt_title.setText(bean.getTitle());
			viewHolder.txt_newstime.setText(bean.getTime());
			viewHolder.txt_content.setText(bean.getContent());
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_title, txt_newstime, txt_content;
	}

}
