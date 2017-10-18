/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13上午10:33:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.android.utils.ScreenUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.adapter.mystock.IndexGridPopupAdapter;
import com.open.baidu.finance.bean.mystock.StockBean;

/**
 ***************************************************************************************************************************************************************************** 
 * 指数
 * @author :fengguangjing
 * @createTime:2017-10-13上午10:33:52
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class IndexMarkHeadFragment extends BaseV4Fragment<CommonJson, IndexMarkHeadFragment> implements OnClickListener{
	public PopupWindow popupWindow;
	public List<StockBean> indexlist = new ArrayList<StockBean>();
	public TextView txt_index_name, txt_index_value, txt_index_rate;
	public static IndexMarkHeadFragment newInstance(String url, boolean isVisibleToUser) {
		IndexMarkHeadFragment fragment = new IndexMarkHeadFragment();
		fragment.setFragment(fragment);
		fragment.isVisibleToUser = isVisibleToUser;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_index_market_head, container, false);
		txt_index_name  = (TextView) view.findViewById(R.id.txt_index_name);
		txt_index_value  = (TextView) view.findViewById(R.id.txt_index_value);
		txt_index_rate  = (TextView) view.findViewById(R.id.txt_index_rate);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		txt_index_name.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_index_name:
			//popup
			showPopupWindow(txt_index_name);
			break;
		default:
			break;
		}
	}
	
	public void showPopupWindow(View parent) {
		//加载布局
		View view =  LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_index_popup_window, null);
		GridView mGridView = (GridView) view.findViewById(R.id.gridview);
		WindowManager manager=(WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		
		IndexGridPopupAdapter mIndexGridPopupAdapter = new IndexGridPopupAdapter(getActivity(), this.indexlist);
		mGridView.setAdapter(mIndexGridPopupAdapter);
		//找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(),(int)ScreenUtils.getIntToDip(getActivity(), (this.indexlist.size()/4f*60+30)));
		//控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		//设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable(null,""));
		
		popupWindow.showAsDropDown(parent,0,-94);
		//监听
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//关闭popupWindow
				popupWindow.dismiss();
				popupWindow = null;
			}
		});
	}

	/**
	 * 设置指数信息
	 */
	public void setIndexlist(List<StockBean> indexlist) {
		this.indexlist.clear();
		for (StockBean bean:indexlist) {
			if(bean.getStockCode().equalsIgnoreCase(".IXIC")||bean.getStockCode().equalsIgnoreCase(".DJI")
					||bean.getStockCode().equalsIgnoreCase("399005")||bean.getStockCode().equalsIgnoreCase("000300")
					||bean.getStockCode().equalsIgnoreCase("399006")||bean.getStockCode().equalsIgnoreCase("399001")
					||bean.getStockCode().equalsIgnoreCase("000001")||bean.getStockCode().equalsIgnoreCase("HSI")
					){
				this.indexlist.add(bean);
			}
			
			if(bean.getStockCode().equalsIgnoreCase("000001")){
				//设置上证指数
				txt_index_name.setText(bean.getStockName());
				txt_index_value.setText(String.format("%.2f", bean.getClose()));
				txt_index_rate.setText(String.format("%.2f", bean.getNetChange())+" "+String.format("%.2f", bean.getNetChangeRatio()) +"%");
				if(bean.getNetChangeRatio()>0){
					txt_index_rate.setTextColor(getActivity().getResources().getColor(R.color.red_color));
					txt_index_value.setTextColor(getActivity().getResources().getColor(R.color.red_color));
				}else if(bean.getNetChangeRatio()<0){
					txt_index_rate.setTextColor(getActivity().getResources().getColor(R.color.green_color));
					txt_index_value.setTextColor(getActivity().getResources().getColor(R.color.green_color));
				}else{
					txt_index_rate.setTextColor(getActivity().getResources().getColor(R.color.gray_color));
					txt_index_value.setTextColor(getActivity().getResources().getColor(R.color.gray_color));
				}
			}
			
		}
		
		
	}
	
	

}
