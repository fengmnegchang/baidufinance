/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午5:04:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午5:04:55
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private static final int CHECKABLE_CHILD_INDEX = 1;
    private Checkable child;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child = (Checkable) getChildAt(CHECKABLE_CHILD_INDEX);
    }

    @Override
    public boolean isChecked() {
        return child.isChecked(); 
    }

    @Override
    public void setChecked(boolean checked) {
        child.setChecked(checked);
    }

    @Override
    public void toggle() {
        child.toggle();
    }
    
}

