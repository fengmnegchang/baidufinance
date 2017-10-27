/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25上午11:39:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.article;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-25上午11:39:32
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class EmojiViewPagerAdapter extends PagerAdapter{
	public List<View> list;
	public Context mContext;
	
	public EmojiViewPagerAdapter(Context mContext, List<View> list) {
		this.mContext = mContext;
		this.list = list;
	}

	@Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager)arg0).removeView(list.get(arg1));
    }

    /***
     * 获取每一个item�?类于listview中的getview
     */
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager)arg0).addView(list.get(arg1));
        return list.get(arg1);
    }
    

	public View getItem(int position){
		return list.get(position);
	}
 
	@Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}
