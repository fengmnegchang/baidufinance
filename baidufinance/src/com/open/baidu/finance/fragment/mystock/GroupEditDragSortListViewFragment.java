/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-16下午4:02:38
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
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
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
import com.open.baidu.finance.adapter.mystock.GroupEditDragSortAdapter;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.json.mystock.GroupListJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-16下午4:02:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class GroupEditDragSortListViewFragment extends BaseV4Fragment<GroupListJson, GroupEditDragSortListViewFragment> implements DragSortListView.DropListener, DragSortListView.RemoveListener,
OnClickListener, OnCheckedChangeListener {
	public DragSortListView mDragSortListView;
	public List<GroupBean> list = new ArrayList<GroupBean>();
	public GroupEditDragSortAdapter mGroupEditDragSortAdapter;

	// foot
	public CheckBox checkbox_all;
	public TextView  txt_delete;
	public TextView txt_add_group;
	
	
	public static GroupEditDragSortListViewFragment newInstance(String url,GroupListJson  groupJson, boolean isVisibleToUser) {
		GroupEditDragSortListViewFragment fragment = new GroupEditDragSortListViewFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		if(groupJson!=null&& groupJson.getGroupList()!=null){
			fragment.list = groupJson.getGroupList();
		}
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_edit_drag_sort_listview, container, false);
		mDragSortListView = (DragSortListView) view.findViewById(R.id.dragsort_listview);
		checkbox_all = (CheckBox) view.findViewById(R.id.checkbox_all);
		txt_delete = (TextView) view.findViewById(R.id.txt_delete);
		txt_add_group = (TextView) view.findViewById(R.id.txt_add_group);
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
		mGroupEditDragSortAdapter = new GroupEditDragSortAdapter(getActivity(), weakReferenceHandler, list);
		mDragSortListView.setAdapter(mGroupEditDragSortAdapter);
		
		RelativeLayout.LayoutParams lp = (LayoutParams) txt_add_group.getLayoutParams();
		lp.topMargin = list.size()*200;
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
		txt_delete.setOnClickListener(this);
		checkbox_all.setOnCheckedChangeListener(this);
		txt_add_group.setOnClickListener(this);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mobeta.android.dslv.DragSortListView.RemoveListener#remove(int)
	 */
	@Override
	public void remove(int which) {
		// TODO Auto-generated method stub
		mGroupEditDragSortAdapter.removeItem(which);
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
			GroupBean item = (GroupBean) mGroupEditDragSortAdapter.getItem(from);
			mGroupEditDragSortAdapter.removeItem(from);
			mGroupEditDragSortAdapter.insertItem(item, to);
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
		case R.id.txt_delete:
			// 删除
			showNormalDialog();
			break;
		case R.id.txt_add_group:
			NewGroupNameFragmentActivity.startNewGroupNameFragmentActivity(getActivity(), null, "",null);
			break;
		default:
			break;
		}
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
		normalDialog.setMessage("您确定删除分组及分组内的股票吗？");
		normalDialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				for (int i = list.size() - 1; i >= 0; i--) {
					if (list.get(i).isCheck()) {
						volleyJson(UrlUtils.DELGROUP, list.get(i).getGroup_id());
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
	 * 删除 分组
	 */
	public void volleyJson(final String href,final String group_id ) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		final Map<String,String> headers = new HashMap<String,String>(); 
		headers.put("User-Agent",UrlUtils.userAgent);
		headers.put("Referer","https://gupiao.baidu.com/my/");
		headers.put("Cookie", UrlUtils.COOKIE);
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
		params.put("group_id", group_id);
		params.put("token", UrlUtils.TOKEN);
		params.put("timestamp", System.currentTimeMillis()+"");

		Log.d("GroupEditDragSortListViewFragment", "href=="+href);
		Log.d("GroupEditDragSortListViewFragment", "headers=="+headers);
		Log.d("GroupEditDragSortListViewFragment", "params=="+params);
		
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
		for (GroupBean bean : list) {
			bean.setCheck(isChecked);
		}
		mGroupEditDragSortAdapter.notifyDataSetChanged();
	}


	
}
