/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午4:42:03
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.widget;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午4:42:03
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ScanImageView extends ImageView {
	private OnMeasureListener onMeasureListener;
	
	public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
		this.onMeasureListener = onMeasureListener;
	}

	public ScanImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScanImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		//将图片测量的大小回调到onMeasureSize()方法中
		if(onMeasureListener != null){
			onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
		}
	}

	public interface OnMeasureListener{
		public void onMeasureSize(int width, int height);
	}
	
}
