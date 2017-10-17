/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午5:15:28
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.NewGroupNameFragmentActivity;
import com.open.baidu.finance.adapter.ChildAdapter;
import com.open.baidu.finance.adapter.mystock.GroupPopupAdapter;
import com.open.baidu.finance.adapter.mystock.ScanStockImagePopupAdapter;
import com.open.baidu.finance.bean.ImageBean;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午5:15:28
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class ImageScanStockGridFragment extends BaseV4Fragment<CommonJson, ImageScanStockGridFragment> implements OnClickListener{
	private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
	private GridView mGridView;
	private ChildAdapter mChildAdapter;
	private List<String> list = new ArrayList<String>();
	private List<String> alllist = new ArrayList<String>();
	private List<ImageBean> groupList = new ArrayList<ImageBean>();
	private TextView txt_group ;
	public PopupWindow popupWindow;
	
	public static ImageScanStockGridFragment newInstance(String url, boolean isVisibleToUser) {
		ImageScanStockGridFragment fragment = new ImageScanStockGridFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_image_scan_stock, container, false);
		mGridView = (GridView) view.findViewById(R.id.main_grid);
		txt_group = (TextView) view.findViewById(R.id.txt_group);
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
		txt_group.setOnClickListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public CommonJson call() throws Exception {
		// TODO Auto-generated method stub
		Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		ContentResolver mContentResolver = getActivity().getContentResolver();
		//只查询jpeg和png的图片
		Cursor mCursor = mContentResolver.query(mImageUri, null,
				MediaStore.Images.Media.MIME_TYPE + "=? or "
						+ MediaStore.Images.Media.MIME_TYPE + "=?",
				new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);
		
		if(mCursor == null){
			return null;
		}
		
		while (mCursor.moveToNext()) {
			//获取图片的路径
			String path = mCursor.getString(mCursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			
			//获取该图片的父路径名
			String parentName = new File(path).getParentFile().getName();

			
			//根据父路径名将图片放入到mGruopMap中
			if (!mGruopMap.containsKey(parentName)) {
				List<String> chileList = new ArrayList<String>();
				chileList.add(path);
				mGruopMap.put(parentName, chileList);
			} else {
				mGruopMap.get(parentName).add(path);
			}
		}
		//通知Handler扫描图片完成
		mCursor.close();
		return super.call();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(CommonJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		allGroupImage();
		list.addAll(alllist);
		mChildAdapter = new ChildAdapter(getActivity(), list, mGridView);
		mGridView.setAdapter(mChildAdapter);
	}
	
	
	/**
	 * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
	 * 所以需要遍历HashMap将数据组装成List
	 * 
	 * @param mGruopMap
	 * @return
	 */
	private void allGroupImage(){
		Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<String>> entry = it.next();
			ImageBean mImageBean = new ImageBean();
			String key = entry.getKey();
			List<String> value = entry.getValue();
			
			mImageBean.setFolderName(key);
			mImageBean.setImageCounts(value.size());
			mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片
			groupList.add(mImageBean);
			
			alllist.addAll(value);
		}
		ImageBean mImageBean = new ImageBean();
		mImageBean.setFolderName("所有图片");
		mImageBean.setImageCounts(alllist.size());
		mImageBean.setTopImagePath(alllist.get(0));//获取该组的第一张图片
		groupList.add(0,mImageBean);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.txt_group){
			showPopupWindow(txt_group);
		}
	}
	
	public void showPopupWindow(View parent) {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_scan_stock_image_popup_window, null);
		WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		ListView listview = (ListView) view.findViewById(R.id.listview);
		ScanStockImagePopupAdapter mScanStockImagePopupAdapter = new ScanStockImagePopupAdapter(getActivity(), groupList, listview);
		listview.setAdapter(mScanStockImagePopupAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				setBackgroundAlpha(1.0f);
				if(popupWindow!=null){
					popupWindow.dismiss();
				}
				list.clear();
				if((int)id==0){
					list = alllist;
				}else{
					list = mGruopMap.get(groupList.get(position).getFolderName());
				}
				mChildAdapter = new ChildAdapter(getActivity(), list, mGridView);
				mGridView.setAdapter(mChildAdapter);
				mChildAdapter.notifyDataSetChanged();
			}
		});
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), groupList.size()*200+250);
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);//设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));

		popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0,
	            0);
		 
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
}
