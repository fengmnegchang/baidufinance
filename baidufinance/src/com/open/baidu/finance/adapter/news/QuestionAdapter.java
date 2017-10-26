/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午11:21:34
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
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.news.AdviserPersonAdapter.ViewHolder;
import com.open.baidu.finance.bean.news.AdviserPersonBean;
import com.open.baidu.finance.bean.news.QuestionBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午11:21:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class QuestionAdapter extends CommonAdapter<QuestionBean>{

	public QuestionAdapter(Context mContext, List<QuestionBean> list) {
		super(mContext, list);
		// TODO Auto-generated constructor stub
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
			convertView = mInflater.inflate(R.layout.adapter_question, parent, false);
			viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			viewHolder.txt_question = (TextView) convertView.findViewById(R.id.txt_question);
			viewHolder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			viewHolder.img_logo= (ImageView) convertView.findViewById(R.id.img_logo);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		QuestionBean bean = (QuestionBean) getItem(position);
		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), viewHolder.img_logo, options, getImageLoadingListener());
			}
			viewHolder.txt_name.setText(bean.getUname());
			viewHolder.txt_time.setText(bean.getTime());
			viewHolder.txt_question.setText(bean.getTitle());
			 
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_name, txt_time,txt_question;
		ImageView img_logo;
	}


}
