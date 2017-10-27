/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午2:55:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.open.android.adapter.CommonPagerAdapter;
import com.open.android.bean.CommonBean;
import com.open.baidu.finance.R;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-11下午2:55:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class CommonDotViewPagerAdapter extends CommonPagerAdapter<CommonBean> {

	public CommonDotViewPagerAdapter(Context mContext, List<CommonBean> list) {
		super(mContext, list);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final CommonBean bean = (CommonBean) getItem(position);
		final ViewHolder mViewHolder = new ViewHolder();
		View convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_dot_viewpager, null);
		mViewHolder.draweeview = (ImageView) convertView.findViewById(R.id.draweeview);
		if (bean != null) {
			mViewHolder.draweeview.setImageResource(bean.getResId());
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
		}
		container.addView(convertView);
		return convertView;
	}

	private class ViewHolder {
		ImageView draweeview;
	}
}