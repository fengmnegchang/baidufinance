/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午4:52:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.news;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open.android.adapter.CommonPagerAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.news.LiveShowBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午4:52:41
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class LiveShowNewsAdapter extends CommonPagerAdapter<LiveShowBean> {

	public LiveShowNewsAdapter(Context mContext, List<LiveShowBean> list) {
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
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_live_show_news,null);
		viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
		viewHolder.img_show = (ImageView) convertView.findViewById(R.id.img_show);
		LiveShowBean bean = (LiveShowBean) getItem(position);
		if (bean != null) {
			viewHolder.txt_title.setText(bean.getDesc());
			if (bean.getSrc() != null && bean.getSrc().length() > 0) {
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_error).showImageForEmptyUri(R.drawable.default_error).showImageOnFail(R.drawable.default_error)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getSrc(), viewHolder.img_show, options, getImageLoadingListener());
			}
		}
		container.addView(convertView);
		return convertView;
	}

	class ViewHolder {
		TextView txt_title;
		ImageView img_show;
	}

}
