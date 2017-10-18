/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18上午9:47:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.mystock;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.andenginetask.IProgressListener;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.RecognitionStockImageFragmentActivity;
import com.open.baidu.finance.utils.NativeImageLoader;
import com.open.baidu.finance.utils.NativeImageLoader.NativeImageCallBack;
import com.open.baidu.finance.widget.ScanImageView;
import com.open.baidu.finance.widget.ScanImageView.OnMeasureListener;

/**
 ***************************************************************************************************************************************************************************** 
 * 数字识别
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18上午9:47:08
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class RecognitionStockImageFragment extends BaseV4Fragment<CommonJson, RecognitionStockImageFragment> {
	private ScanImageView scan_image;
	private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象
	private View view;
	private ImageView img_refresh;
	private TextView txt_tip;
	private Button btn_refresh;
	
	public static RecognitionStockImageFragment newInstance(String url, boolean isVisibleToUser) {
		RecognitionStockImageFragment fragment = new RecognitionStockImageFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_recognition_stock_image, container, false);
		scan_image = (ScanImageView) view.findViewById(R.id.scan_image);
		img_refresh = (ImageView) view.findViewById(R.id.img_refresh);
		txt_tip = (TextView) view.findViewById(R.id.txt_tip);
		btn_refresh = (Button) view.findViewById(R.id.btn_refresh);
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
		scan_image.setOnMeasureListener(new OnMeasureListener() {

			@Override
			public void onMeasureSize(int width, int height) {
				mPoint.set(width, height);
			}
		});
		scan_image.setTag(url);
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(url, mPoint, new NativeImageCallBack() {

			@Override
			public void onImageLoader(Bitmap bitmap, String path) {
				ImageView mImageView = (ImageView) view.findViewWithTag(path);
				if (bitmap != null && mImageView != null) {
					mImageView.setImageBitmap(bitmap);
				}
			}
		});

		if (bitmap != null) {
			scan_image.setImageBitmap(bitmap);
		} else {
			scan_image.setImageResource(R.drawable.ic_launcher);
		}
	}
	
	/***
	 * 识别图片
	 */
	public void recognitonImage(){
		weakReferenceHandler.sendEmptyMessage(9000);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handlerMessage(msg);
		switch (msg.what) {
		case 9000:
			doProgressAsync(getActivity(), ProgressDialog.STYLE_HORIZONTAL, "","请稍后...",this,this,this);
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call(com.open.andenginetask.IProgressListener)
	 */
	@Override
	public CommonJson call(IProgressListener pProgressListener) throws Exception {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.call(pProgressListener);
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(CommonJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		img_refresh.setVisibility(View.VISIBLE);
		txt_tip.setVisibility(View.VISIBLE);
		btn_refresh.setVisibility(View.VISIBLE);
		scan_image.setVisibility(View.GONE);
		((RecognitionStockImageFragmentActivity)getActivity()).setCenterTextValue("识别结果");
	}
}
