/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:09:33
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.news.ExpertQuestionIndicatorFragmentActivity;
import com.open.baidu.finance.bean.news.AdviserPersonBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-26上午10:09:33
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class AdviserPersonAdapter extends CommonAdapter<AdviserPersonBean> {

	public AdviserPersonAdapter(Context mContext, List<AdviserPersonBean> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_adviser_person, parent, false);
			viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			viewHolder.txt_level = (TextView) convertView.findViewById(R.id.txt_level);
			viewHolder.txt_info = (TextView) convertView.findViewById(R.id.txt_info);
			viewHolder.txt_ask= (TextView) convertView.findViewById(R.id.txt_ask);
			viewHolder.img_logo= (ImageView) convertView.findViewById(R.id.img_logo);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final AdviserPersonBean bean = (AdviserPersonBean) getItem(position);
		if (bean != null) {
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.common_v4).showImageForEmptyUri(R.drawable.common_v4).showImageOnFail(R.drawable.common_v4)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), viewHolder.img_logo, options, getImageLoadingListener());
			}
			viewHolder.txt_name.setText(bean.getExpertName());
			viewHolder.txt_level.setText(bean.getLevel());
			viewHolder.txt_info.setText(bean.getInfo());
			viewHolder.txt_ask.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ExpertQuestionIndicatorFragmentActivity.startExpertQuestionIndicatorFragmentActivity(mContext, bean.getHref());
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_name, txt_level, txt_info,txt_ask;
		ImageView img_logo;
	}

}
