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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobeta.android.dslv.DragSortListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
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
public class StockEditDragSortListViewFragment extends BaseV4Fragment<GroupBean, StockEditDragSortListViewFragment>
implements DragSortListView.DropListener,DragSortListView.RemoveListener{
	public DragSortListView mDragSortListView;
	public List<StockBean> list = new ArrayList<StockBean>();
	public StockEditDragSortAdapter mStockEditDragSortAdapter;
	
	public static StockEditDragSortListViewFragment newInstance(String url, boolean isVisibleToUser) {
		StockEditDragSortListViewFragment fragment = new StockEditDragSortListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stock_edit_drag_sort_listview, container, false);
		mDragSortListView = (DragSortListView) view.findViewById(R.id.dragsort_listview);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		list.add(new StockBean());
		list.add(new StockBean());
		list.add(new StockBean());
		list.add(new StockBean());
		mStockEditDragSortAdapter = new StockEditDragSortAdapter(getActivity(), list);
		mDragSortListView.setAdapter(mStockEditDragSortAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		mDragSortListView.setDropListener(this);
		mDragSortListView.setRemoveListener(this);
	}

	/* (non-Javadoc)
	 * @see com.mobeta.android.dslv.DragSortListView.RemoveListener#remove(int)
	 */
	@Override
	public void remove(int which) {
		// TODO Auto-generated method stub
		mStockEditDragSortAdapter.removeItem(which);
        mDragSortListView.removeCheckState(which);
	}

	/* (non-Javadoc)
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
}
