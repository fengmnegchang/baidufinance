/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25上午11:35:03
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.article;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.open.android.adapter.CommonAdapter;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.article.EmojiBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25上午11:35:03
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class EmojiAdapter extends CommonAdapter<EmojiBean> {

	public EmojiAdapter(Context mContext, List<EmojiBean> list) {
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
			convertView = mInflater.inflate(R.layout.adapter_add_emoji, parent, false);
			viewHolder.img_user = (ImageView) convertView.findViewById(R.id.img_user);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		EmojiBean bean = (EmojiBean) getItem(position);
		if (bean != null) {
			viewHolder.img_user.setImageResource(mContext.getResources().getIdentifier(bean.getFaceName().replace(".png", ""), "drawable",mContext.getPackageName()));
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img_user;
	}

}
