/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20下午4:05:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.article;

import java.text.SimpleDateFormat;
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
import com.open.baidu.finance.bean.article.CommentBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20下午4:05:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class NewsCommentAdapter extends CommonAdapter<CommentBean> {

	public NewsCommentAdapter(Context mContext, List<CommentBean> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_news_comment, parent, false);
			viewHolder.txt_username = (TextView) convertView.findViewById(R.id.txt_username);
			viewHolder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
			viewHolder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
			viewHolder.txt_recentstr = (TextView) convertView.findViewById(R.id.txt_recentstr);
			viewHolder.txt_reply = (TextView) convertView.findViewById(R.id.txt_reply);
			viewHolder.txt_like = (TextView) convertView.findViewById(R.id.txt_like);
			viewHolder.txt_delete = (TextView) convertView.findViewById(R.id.txt_delete);
			viewHolder.img_user = (ImageView) convertView.findViewById(R.id.img_user);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CommentBean bean = (CommentBean) getItem(position);
		if (bean != null) {
			viewHolder.txt_username.setText(bean.getUserName());
			viewHolder.txt_time.setText(getStringTimeFromLongCustomFormat(bean.getTimeStamp(),"yy-MM dd:mm"));
			viewHolder.txt_content.setText(bean.getContent());
			if(bean.getRecentUserId()!=null && bean.getRecentUserId().length()>0){
				viewHolder.txt_recentstr.setVisibility(View.VISIBLE);
				viewHolder.txt_recentstr.setText(bean.getRecentUserName()+":"+bean.getRecentstr());
			}else{
				viewHolder.txt_recentstr.setVisibility(View.GONE);
			}
			if(bean.getUserhead()!=null&&bean.getUserhead().length()>0){
				DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.comment_head_light).showImageForEmptyUri(R.drawable.comment_head_light).showImageOnFail(R.drawable.comment_head_light)
						.cacheInMemory().cacheOnDisc().build();
				ImageLoader.getInstance().displayImage(bean.getUserhead(), viewHolder.img_user, options, getImageLoadingListener());
			}
			viewHolder.txt_reply.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			viewHolder.txt_like.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			viewHolder.txt_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
		}
		return convertView;
	}

	class ViewHolder {
		TextView txt_username,txt_time,txt_content,txt_recentstr,txt_reply,txt_like,txt_delete;
		ImageView img_user;
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
