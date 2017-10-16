/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午5:11:48
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.mobeta.android.dslv.DragSortListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.NewGroupNameFragmentActivity;
import com.open.baidu.finance.adapter.mystock.StockEditDragSortAdapter;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.bean.mystock.StockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午5:11:48
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class StockEditDragSortListViewFragment extends BaseV4Fragment<GroupBean, StockEditDragSortListViewFragment> implements DragSortListView.DropListener, DragSortListView.RemoveListener,
		OnClickListener, OnCheckedChangeListener {
	public DragSortListView mDragSortListView;
	public List<StockBean> list = new ArrayList<StockBean>();
	public StockEditDragSortAdapter mStockEditDragSortAdapter;

	// foot
	public CheckBox checkbox_all;
	public TextView txt_move, txt_delete;
	public PopupWindow popupWindow;

	public static StockEditDragSortListViewFragment newInstance(String url, GroupBean groupBean, boolean isVisibleToUser) {
		StockEditDragSortListViewFragment fragment = new StockEditDragSortListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.list = groupBean.getStock();
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_edit_drag_sort_listview, container, false);
		mDragSortListView = (DragSortListView) view.findViewById(R.id.dragsort_listview);
		checkbox_all = (CheckBox) view.findViewById(R.id.checkbox_all);
		txt_move = (TextView) view.findViewById(R.id.txt_move);
		txt_delete = (TextView) view.findViewById(R.id.txt_delete);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mStockEditDragSortAdapter = new StockEditDragSortAdapter(getActivity(), weakReferenceHandler, list);
		mDragSortListView.setAdapter(mStockEditDragSortAdapter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mDragSortListView.setDropListener(this);
		mDragSortListView.setRemoveListener(this);
		txt_move.setOnClickListener(this);
		txt_delete.setOnClickListener(this);
		checkbox_all.setOnCheckedChangeListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobeta.android.dslv.DragSortListView.RemoveListener#remove(int)
	 */
	@Override
	public void remove(int which) {
		// TODO Auto-generated method stub
		mStockEditDragSortAdapter.removeItem(which);
		mDragSortListView.removeCheckState(which);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobeta.android.dslv.DragSortListView.DropListener#drop(int, int)
	 */
	@Override
	public void drop(int from, int to) {
		// TODO Auto-generated method stub
		if (from != to) {
			StockBean item = (StockBean) mStockEditDragSortAdapter.getItem(from);
			mStockEditDragSortAdapter.removeItem(from);
			mStockEditDragSortAdapter.insertItem(item, to);
			mDragSortListView.moveCheckState(from, to);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message
	 * )
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case 1000:
			// 置顶
			drop(msg.arg1, 0);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_move:
			// 移动分组
			showPopupWindow(txt_move);
			break;
		case R.id.txt_delete:
			// 删除
			showNormalDialog();
			break;
		default:
			break;
		}
	}

	public void showPopupWindow(View parent) {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dragedit_popup_window, null);
		WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), 550);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);//设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));

		popupWindow.showAsDropDown(parent, 0, -80);
		
		TextView txt_add_group = (TextView) view.findViewById(R.id.txt_add_group);
		TextView txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
		txt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
			}
		});
		txt_add_group.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//新建分组
				// TODO Auto-generated method stub
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
				NewGroupNameFragmentActivity.startNewGroupNameFragmentActivity(getActivity(), url);
			}
		});
		popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 *            屏幕透明度0.0-1.0 1表示完全不透明
	 */
	public void setBackgroundAlpha(float bgAlpha) {
	    WindowManager.LayoutParams lp = getActivity().getWindow()
	            .getAttributes();
	    lp.alpha = bgAlpha;
	    getActivity().getWindow().setAttributes(lp);
	}

	
	private void showNormalDialog() {
		/*
		 * @setIcon 设置对话框图标
		 * 
		 * @setTitle 设置对话框标题
		 * 
		 * @setMessage 设置对话框消息提示 setXXX方法返回Dialog对象，因此可以链式设置属性
		 */
		final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
		normalDialog.setMessage("您确定删除自选股吗？");
		normalDialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = list.size() - 1; i >= 0; i--) {
					if (list.get(i).isCheck()) {
						remove(i);
					}
				}
				dialog.dismiss();
			}
		});
		normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		// 显示
		normalDialog.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		setCheckedable(isChecked);
	}

	public void setCheckedable(boolean isChecked) {
		for (StockBean bean : list) {
			bean.setCheck(isChecked);
		}
		mStockEditDragSortAdapter.notifyDataSetChanged();
	}
}
