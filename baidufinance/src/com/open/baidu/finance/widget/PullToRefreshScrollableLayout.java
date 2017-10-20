package com.open.baidu.finance.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.open.android.widget.ScrollableLayout;

@SuppressLint({ "NewApi", "Recycle" })
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class PullToRefreshScrollableLayout extends PullToRefreshBase<ScrollableLayout> {

	private int defaultID = 0xffda;
	private boolean isTouchOutSide;

	/**
	 *@atour: Atar
	 *@createTime:2015-11-7下午9:08:29
	 *@modifyTime:
	 *@version: 
	 *@param context:
	 *@description:
	 */
	public PullToRefreshScrollableLayout(Context context) {
		super(context);
	}

	/**
	 *@atour: Atar
	 *@createTime:2015-11-7下午9:08:40
	 *@modifyTime:
	 *@version: 
	 *@param context
	 *@param attrs:
	 *@description:
	 */
	public PullToRefreshScrollableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 *@atour: Atar
	 *@createTime:2015-11-7下午9:07:35
	 *@modifyTime:
	 *@version: 
	 *@param context
	 *@param mode:
	 *@description:
	 */
	public PullToRefreshScrollableLayout(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToRefreshScrollableLayout(Context context, Mode mode, AnimationStyle animStyle) {
		super(context, mode, animStyle);
	}

	@Override
	public Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected ScrollableLayout createRefreshableView(Context context, AttributeSet attrs) {
		ScrollableLayout mScrollableLayout = new ScrollableLayout(context, attrs);
		mScrollableLayout.setId(defaultID);
		return mScrollableLayout;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		// if (getRefreshableView() != null && getRefreshableView().getHelper() != null) {
		// return getRefreshableView().getHelper().isButtom();
		// }
		return false;
	}

	@Override
	protected boolean isReadyForPullStart() {
		if (getRefreshableView() != null) {
			return getRefreshableView().isSmoothTop();
		}
		return false;
	}

	@Override
	protected void onRefreshing(boolean doScroll) {
		super.onRefreshing(doScroll);
		isTouchOutSide = true;
	}

	@Override
	protected void onReleaseToRefresh() {
		super.onReleaseToRefresh();
		isTouchOutSide = true;
	}

	@Override
	protected void onReset() {
		super.onReset();
		isTouchOutSide = false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return !isTouchOutSide && super.onInterceptTouchEvent(event);
	}
}
