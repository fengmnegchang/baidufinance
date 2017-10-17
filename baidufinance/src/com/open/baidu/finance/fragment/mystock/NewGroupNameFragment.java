/**
 *****************************************************************************************************************************************************************************
 * 新建分组
 * @createTime:2017-10-16下午2:55:06
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NormalPostRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.json.mystock.AddGroupJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 新建分组
 * @author :fengguangjing
 * @createTime:2017-10-16下午2:55:06
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class NewGroupNameFragment extends BaseV4Fragment<CommonJson, NewGroupNameFragment>implements OnClickListener {
	public String groupName;
	public EditText edit_name;
	public Button btn_add;
	public String groupId;
	
	public static NewGroupNameFragment newInstance(String url, String groupName,String groupId,boolean isVisibleToUser) {
		NewGroupNameFragment fragment = new NewGroupNameFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.groupName = groupName;
		fragment.groupId = groupId;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_group_name, container, false);
		edit_name = (EditText) view.findViewById(R.id.edit_name);
		btn_add = (Button) view.findViewById(R.id.btn_add);
		return view;
	}
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		if(groupName!=null){
			edit_name.setText(groupName);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		btn_add.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_add){
			if(edit_name.getText().toString().length()==0){
				Toast.makeText(getActivity(), "输入分组名称", Toast.LENGTH_SHORT).show();
				return;
			}
			if(groupId==null){
				volleyJson(UrlUtils.ADDGROUP,edit_name.getText().toString(),groupId);
			}else{
				volleyJson(UrlUtils.MODIFYGROUP,edit_name.getText().toString(),groupId);
			}
			
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
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	public void volleyJson(final String href,String groupName,String groupid) {
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
		//group_id:086eb128ec6cb70a132dd90a59531a70
		//group_name:持股2
		params.put("group_name", groupName);
		if(groupid!=null){
			params.put("group_id", groupid);
		}
		params.put("token", UrlUtils.TOKEN);
		params.put("timestamp", System.currentTimeMillis()+"");

		Log.d("NewGroupNameFragment", "href=="+href);
		Log.d("NewGroupNameFragment", "headers=="+headers);
		Log.d("NewGroupNameFragment", "params=="+params);
		Request<JSONObject> request = new NormalPostRequest(href,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			            Log.d(TAG, "response -> " + response.toString());
			            //{"errorNo":0,"errorMsg":"success","data":{"group_id":"e21afe5f5de982fe1560dd3120d33214"}}
			            Gson gson = new Gson();
			            AddGroupJson addjson = gson.fromJson(response.toString(), AddGroupJson.class);
			            if(addjson!=null){
			            	if(addjson.getErrorNo()==0){
			            		Toast.makeText(getActivity(), addjson.getErrorMsg(), Toast.LENGTH_LONG).show();
			            		edit_name.setText("");
			            	}
			            }
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			            Log.e(TAG, error.getMessage(), error);
			        }
			    }, headers,params);

		requestQueue.add(request);
	}
	
	
 
}
