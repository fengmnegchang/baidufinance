/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午5:56:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.adapter.mystock;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.ImageBean;
import com.open.baidu.finance.utils.NativeImageLoader;
import com.open.baidu.finance.utils.NativeImageLoader.NativeImageCallBack;
import com.open.baidu.finance.widget.ScanImageView;
import com.open.baidu.finance.widget.ScanImageView.OnMeasureListener;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午5:56:17
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ScanStockImagePopupAdapter extends BaseAdapter{
	private List<ImageBean> list;
	private Point mPoint = new Point(0, 0);//用来封装ImageView的宽和高的对象
	private ListView mListView;
	protected LayoutInflater mInflater;
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public ScanStockImagePopupAdapter(Context context, List<ImageBean> list, ListView mListView){
		this.list = list;
		this.mListView = mListView;
		mInflater = LayoutInflater.from(context);
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		ImageBean mImageBean = list.get(position);
		String path = mImageBean.getTopImagePath();
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.adapter_scan_stock_image_popup, null);
			viewHolder.mImageView = (ScanImageView) convertView.findViewById(R.id.group_image);
			viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.group_title);
			viewHolder.mTextViewCounts = (TextView) convertView.findViewById(R.id.group_count);
			
			//用来监听ImageView的宽和高
			viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener() {
				
				@Override
				public void onMeasureSize(int width, int height) {
					mPoint.set(width, height);
				}
			});
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mImageView.setImageResource(R.drawable.ic_launcher);
		}
		
		viewHolder.mTextViewTitle.setText(mImageBean.getFolderName());
		viewHolder.mTextViewCounts.setText(Integer.toString(mImageBean.getImageCounts())+"张");
		//给ImageView设置路径Tag,这是异步加载图片的小技巧
		viewHolder.mImageView.setTag(path);
		
		
		//利用NativeImageLoader类加载本地图片
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, mPoint, new NativeImageCallBack() {
			
			@Override
			public void onImageLoader(Bitmap bitmap, String path) {
				ImageView mImageView = (ImageView) mListView.findViewWithTag(path);
				if(bitmap != null && mImageView != null){
					mImageView.setImageBitmap(bitmap);
				}
			}
		});
		
		if(bitmap != null){
			viewHolder.mImageView.setImageBitmap(bitmap);
		}else{
			viewHolder.mImageView.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;
	}
	
	
	
	public static class ViewHolder{
		public ScanImageView mImageView;
		public TextView mTextViewTitle;
		public TextView mTextViewCounts;
	}
}
