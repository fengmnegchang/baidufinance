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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.news.HotTiebaTopicBean;

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
public class HotTiebaTopicAdapter extends CommonAdapter<HotTiebaTopicBean> {

	public HotTiebaTopicAdapter(Context mContext, List<HotTiebaTopicBean> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_hot_tieba_topic, parent, false);
			viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
			viewHolder.txt_newstime = (TextView) convertView.findViewById(R.id.txt_newstime);
			viewHolder.txt_replycount = (TextView) convertView.findViewById(R.id.txt_replycount);
			viewHolder.txt_readcount= (TextView) convertView.findViewById(R.id.txt_readcount);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		HotTiebaTopicBean bean = (HotTiebaTopicBean) getItem(position);
		if (bean != null) {
			viewHolder.txt_title.setText(bean.getTitle());
			viewHolder.txt_newstime.setText(bean.getTime());
			viewHolder.txt_replycount.setText(bean.getReplycount()+"条回复");
			viewHolder.txt_readcount.setText(bean.getReadcount()+"条阅读");
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_title, txt_newstime, txt_replycount,txt_readcount;
	}

}
