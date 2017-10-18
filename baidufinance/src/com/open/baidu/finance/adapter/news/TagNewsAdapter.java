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

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.news.TagNewsBean;

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
public class TagNewsAdapter extends CommonAdapter<TagNewsBean> {

	public TagNewsAdapter(Context mContext, List<TagNewsBean> list) {
		super(mContext, list);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.adapter.CommonAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_news_tagnews, parent, false);
			viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_title);
			viewHolder.txt_newstime = (TextView) convertView.findViewById(R.id.txt_newstime);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TagNewsBean bean = (TagNewsBean) getItem(position);
		if(bean!=null){
			viewHolder.txt_title.setText(bean.getTitle());
			viewHolder.txt_newstime.setText(getStringTimeFromLongCustomFormat(bean.getNewstime(),"yy-MM dd:mm"));
		}
		return convertView;
	}
	class ViewHolder {
		TextView txt_title,txt_newstime;
	}
	
	public static String getStringTimeFromLongCustomFormat(long time, String timeFormat) {
		String strTime = "- -";
		try {
			if (time > 0) {
				SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
				strTime = formatter.format(Long.valueOf(time));
			}
		} catch (Exception e) {

		}
		return strTime;
	}
}
