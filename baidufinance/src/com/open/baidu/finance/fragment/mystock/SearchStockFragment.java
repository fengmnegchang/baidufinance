/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午1:50:38
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

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NormalPostRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.open.android.bean.db.OpenDBBean;
import com.open.android.db.service.OpenDBService;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.utils.ScreenUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.ImageScanStockGridFragmentActivity;
import com.open.baidu.finance.activity.mystock.NewGroupNameFragmentActivity;
import com.open.baidu.finance.adapter.mystock.GroupPopupAdapter;
import com.open.baidu.finance.adapter.mystock.SearchStockAdapter;
import com.open.baidu.finance.bean.mystock.GroupBean;
import com.open.baidu.finance.bean.mystock.SearchStockData;
import com.open.baidu.finance.json.mystock.SearchStockJson;
import com.open.baidu.finance.json.mystock.StockJson;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 搜索股票
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午1:50:38
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class SearchStockFragment extends BaseV4Fragment<SearchStockJson, SearchStockFragment> implements OnClickListener{
	public ListView listview;
	public SearchStockAdapter mSearchStockAdapter;
	public List<SearchStockData> list  = new ArrayList<SearchStockData>();
	public List<SearchStockData> temptlist  = new ArrayList<SearchStockData>();
	
	private EditText edit_search;
	private ImageView img_photo ;
	private Button btn_cancel;
	public PopupWindow popupWindow;
	public List<GroupBean> glist = new ArrayList<GroupBean>();
	
	public static SearchStockFragment newInstance(String url, boolean isVisibleToUser) {
		SearchStockFragment fragment = new SearchStockFragment();
		fragment.setFragment(fragment);
		fragment.isVisibleToUser = isVisibleToUser;
		fragment.setUserVisibleHint(isVisibleToUser);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_stock, container, false);
		listview = (ListView) view.findViewById(R.id.listview);
		edit_search = (EditText) view.findViewById(R.id.edit_search);
		img_photo = (ImageView) view.findViewById(R.id.img_photo);
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		return view;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mSearchStockAdapter = new SearchStockAdapter(getActivity(),weakReferenceHandler, list);
		listview.setAdapter(mSearchStockAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		img_photo.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		edit_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				volleyJson(UrlUtils.STOCKQUERY+edit_search.getText().toString());
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cancel:
			getActivity().finish();
			break;
		case R.id.img_photo:
			ImageScanStockGridFragmentActivity.startImageScanStockGridFragmentActivity(getActivity(), url);
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(SearchStockJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null){
			list.clear();
			if(edit_search.getText().toString().length()>0){
				list.addAll(result.getData().getStock_data());
				SearchStockData bean = new SearchStockData();
				bean.setBottom(true);
				list.add(bean);
			}else{
				list.addAll(temptlist);
			}
			mSearchStockAdapter.notifyDataSetChanged();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#volleyJson(java.lang.String)
	 */
	@Override
	public void volleyJson(final String href) {
		// TODO Auto-generated method stub
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent",UrlUtils.userAgent);
		params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					SearchStockJson result = gson.fromJson(response.toString(), SearchStockJson.class);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, SearchStockFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case 10000:
			showPopupWindow((SearchStockData)msg.obj);
			break;
		case MESSAGE_HANDLER:
			gathermystock(UrlUtils.GATHERMYSTOCK_ALL);
			List<OpenDBBean> dblist = OpenDBService.queryListType(getActivity(), 1);
			if(dblist!=null && dblist.size()>0){
				SearchStockData bean = null;
				for(OpenDBBean dbBean:dblist){
					bean = new SearchStockData();
					bean.setF_symbolName(dbBean.getTitle());
					bean.setF_symbol(dbBean.getDownloadurl());
					bean.setGroupId(dbBean.getImgsrc());
					list.add(bean);
				}
				bean = new SearchStockData();
				bean.setBottom(true);
				list.add(bean);
				temptlist.addAll(list);
				mSearchStockAdapter.notifyDataSetChanged();
			}
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
	
	public void gathermystock(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent",UrlUtils.userAgent);
		params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					StockJson result = gson.fromJson(response.toString(), StockJson.class);
					glist.clear();
					glist.addAll(result.getData().get(0).getData().getGroupList());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, SearchStockFragment.this);
		requestQueue.add(jsonObjectRequest);
	}
	
	
	public void showPopupWindow(final SearchStockData bean) {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dragedit_popup_window, null);
		WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		GroupPopupAdapter mGroupPopupAdapter = new GroupPopupAdapter(getActivity(), glist);
		ListView listview = (ListView) view.findViewById(R.id.listview);
		listview.setAdapter(mGroupPopupAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				//添加股票
				addmystock(UrlUtils.ADDMYSTOCK,bean.getF_code(), glist.get((int)id).getGroup_id());
				try {
					//保存记录
					OpenDBBean openbean = new OpenDBBean();
					openbean.setTitle(bean.getF_symbolName());
					openbean.setDownloadurl(bean.getF_symbol());
					openbean.setImgsrc(glist.get((int)id).getGroup_id());
					openbean.setType(1);
					openbean.setTypename("1");
					openbean.setUrl(bean.getF_symbolName());
					OpenDBService.insert(getActivity(), openbean);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
				
			}
		});
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), (int)ScreenUtils.getIntToDip(getActivity(), (glist.size()*40+100)));
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);//设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));

		popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0,
	            0);
		
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
	
	/**
	 * 添加股票
	 */
	public void addmystock(final String href,final String stockCode ,final String groupId) {
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
		params.put("stock_code", stockCode);
		params.put("group_id", groupId);
		params.put("token", UrlUtils.TOKEN);
		params.put("timestamp", System.currentTimeMillis()+"");

		Log.d(TAG, "href=="+href);
		Log.d(TAG, "headers=="+headers);
		Log.d(TAG, "params=="+params);
		
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
	
}
