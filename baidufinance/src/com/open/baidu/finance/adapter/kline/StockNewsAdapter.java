/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午11:07:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.kline;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.article.MNewsContainerPullScrollFragmentActivity;
import com.open.baidu.finance.bean.kline.NewsBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-11-7上午11:07:51
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockNewsAdapter extends CommonAdapter<NewsBean> {

	public StockNewsAdapter(Context mContext, List<NewsBean> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_stock_news, parent, false);
			viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
			viewHolder.txt_desc = (TextView) convertView.findViewById(R.id.txt_desc);
			viewHolder.txt_newstime = (TextView) convertView.findViewById(R.id.txt_newstime);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final NewsBean bean = (NewsBean) getItem(position);
		if (bean != null) {
			viewHolder.txt_title.setText(bean.getTitle());
			viewHolder.txt_desc.setText(bean.getDesc());
			viewHolder.txt_newstime.setText(bean.getTime());
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					MNewsContainerPullScrollFragmentActivity.startMNewsContainerPullScrollFragmentActivity(mContext, bean.getHref());
				}
			});
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_title, txt_newstime, txt_desc;
	}

}
