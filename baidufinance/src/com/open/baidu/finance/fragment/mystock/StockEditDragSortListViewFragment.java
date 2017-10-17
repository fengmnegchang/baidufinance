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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NormalPostRequest;
import com.android.volley.toolbox.Volley;
import com.mobeta.android.dslv.DragSortListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.NewGroupNameFragmentActivity;
import com.open.baidu.finance.adapter.mystock.GroupPopupAdapter;
import com.open.baidu.finance.adapter.mystock.StockEditDragSortAdapter;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.bean.mystock.StockBean;
import com.open.baidu.finance.json.mystock.GroupListJson;
import com.open.baidu.finance.utils.UrlUtils;

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
	public GroupListJson groupListJson;

	public static StockEditDragSortListViewFragment newInstance(String url,GroupListJson groupListJson ,int  position, boolean isVisibleToUser) {
		StockEditDragSortListViewFragment fragment = new StockEditDragSortListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		try {
			fragment.list = groupListJson.getGroupList().get(position).getStock();
			fragment.groupListJson = groupListJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			try {
				StockBean bean = (StockBean) mStockEditDragSortAdapter.getItem(msg.arg1);
				volleyJson(UrlUtils.TOPMYSTOCK,bean.getExchange()+bean.getStockCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
			drop(msg.arg1, 0);
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android.volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}
	
	/**
	 * 删除 stock_code:usMSFT,usFB
	 */
	public void volleyJson(final String href,final String stockCode ) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		final Map<String,String> headers = new HashMap<String,String>(); 
		headers.put("User-Agent",UrlUtils.userAgent);
		headers.put("Referer","https://gupiao.baidu.com/my/");
		headers.put("Cookie", UrlUtils.COOKIE);
		
//		headers.put("X-Requested-With","XMLHttpRequest");
//		headers.put("Host","gupiao.baidu.com");
//		headers.put("Origin","https://gupiao.baidu.com");
//		headers.put("Content-Type","application/x-www-form-urlencoded");
		
		/****
		 * from:pc
			os_ver:1
			cuid:xxx
			vv:100
			format:json
			stock_code:sz399001
			group_id:8a5205fe03d1e3fcd7ec591ff8daea29
			token:9df0129455f37719
			timestamp:1508142738888
		 */
		final Map<String, String> params = new HashMap<String, String>();
		params.put("from", "pc");
		params.put("os_ver", "1");
		params.put("cuid", "xxx");
		params.put("vv", "100");
		params.put("format", "json");
		params.put("stock_code", stockCode);
		params.put("group_id", groupListJson.getGroupList().get(position).getGroup_id()+"");
		params.put("token", UrlUtils.TOKEN);
		params.put("timestamp", System.currentTimeMillis()+"");

		Log.d("StockEditDragSortListViewFragment", "href=="+href);
		Log.d("StockEditDragSortListViewFragment", "headers=="+headers);
		Log.d("StockEditDragSortListViewFragment", "params=="+params);
//		JSONObject paramJsonObject = new JSONObject(headers); 
//		
//		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, href,params,paramJsonObject, new Response.Listener<JSONObject>() {
//			@Override
//			public void onResponse(JSONObject response) {
//				System.out.println("href=" + href);
//				System.out.println("response=" + response.toString());
//			}
//		}, StockEditDragSortListViewFragment.this){
//			/* (non-Javadoc)
//			 * @see com.android.volley.toolbox.JsonObjectRequest#getHeaders()
//			 */
////			@Override
////			public Map<String, String> getHeaders() throws AuthFailureError {
////				// TODO Auto-generated method stub
////				return params;
////			}
//		};
		
		Request<JSONObject> request = new NormalPostRequest(href,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			            Log.d(TAG, "response -> " + response.toString());
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			            Log.e(TAG, error.getMessage(), error);
			        }
			    }, headers,params);

		requestQueue.add(request);
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
		final String groupId = groupListJson.getGroupList().get(position).getGroup_id();
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dragedit_popup_window, null);
		WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		GroupPopupAdapter mGroupPopupAdapter = new GroupPopupAdapter(getActivity(), groupListJson.getGroupList());
		ListView listview = (ListView) view.findViewById(R.id.listview);
		listview.setAdapter(mGroupPopupAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//group_id_from:8a5205fe03d1e3fcd7ec591ff8daea29
				//group_id_to:ae38a876efd3da6c006f7f8d4add415b
				for (int i = list.size() - 1; i >= 0; i--) {
					if (list.get(i).isCheck()) {
						movemystock(UrlUtils.MOVEMYSTOCK, list.get(i).getExchange()+list.get(i).getStockCode(),  
								groupId,
								groupListJson.getGroupList().get((int)id).getGroup_id());
						remove(i);
					}
				}
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
				
			}
		});
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), groupListJson.getGroupList().size()*200+250);
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
				NewGroupNameFragmentActivity.startNewGroupNameFragmentActivity(getActivity(),"", url,null);
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
	
	public void movemystock(final String href,final String stockCode,String group_id_from,String group_id_to) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		final Map<String,String> headers = new HashMap<String,String>(); 
		headers.put("User-Agent",UrlUtils.userAgent);
		headers.put("Referer","https://gupiao.baidu.com/my/");
		headers.put("Cookie", UrlUtils.COOKIE);
		/****
		 *  from:pc
			os_ver:1
			cuid:xxx
			vv:100
			format:json
			stock_code:usBIDU
			group_id_from:8a5205fe03d1e3fcd7ec591ff8daea29
			group_id_to:ae38a876efd3da6c006f7f8d4add415b
			token:65e93709f606922c
			timestamp:1508210848998
		 */
		final Map<String, String> params = new HashMap<String, String>();
		params.put("from", "pc");
		params.put("os_ver", "1");
		params.put("cuid", "xxx");
		params.put("vv", "100");
		params.put("format", "json");
		params.put("stock_code", stockCode);
		params.put("group_id_from", group_id_from);
		params.put("group_id_to", group_id_to);
		params.put("token", UrlUtils.TOKEN);
		params.put("timestamp", System.currentTimeMillis()+"");

		Log.d("StockEditDragSortListViewFragment", "href=="+href);
		Log.d("StockEditDragSortListViewFragment", "headers=="+headers);
		Log.d("StockEditDragSortListViewFragment", "params=="+params);
		Request<JSONObject> request = new NormalPostRequest(href,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			            Log.d(TAG, "response -> " + response.toString());
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			            Log.e(TAG, error.getMessage(), error);
			        }
			    }, headers,params);

		requestQueue.add(request);
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
						volleyJson(UrlUtils.BATCHDELMYSTOCK, list.get(i).getExchange()+list.get(i).getStockCode());
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
