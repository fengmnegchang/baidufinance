/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:30:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.android.utils.ScreenUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.article.MNewsCommentPullListFragmentActivity;
import com.open.baidu.finance.adapter.article.ShareGridAdapter;
import com.open.baidu.finance.application.CommonApplication;
import com.open.baidu.finance.bean.article.ShareBean;
import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.article.NewsContainerJson;
import com.open.baidu.finance.jsoup.TagNewsJsoupService;
import com.open.baidu.finance.utils.UrlUtils;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXVideoObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.tencent.mm.sdk.platformtools.Util;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-19下午4:30:58
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class NewsContainerPullScrollFragment extends BaseV4Fragment<NewsContainerJson, NewsContainerPullScrollFragment> implements OnRefreshListener<ScrollView>, OnClickListener, IUiListener {
	private TextView txt_title, txt_time, txt_news;
	public PullToRefreshScrollView mPullToRefreshScrollView;
	private FrameLayout layout_more;
	// 底部
	private ImageView img_msg, img_share, img_love, img_top;
	private TextView txt_msg;
	public PopupWindow popupWindow;
	private List<ShareBean> shareList = new ArrayList<ShareBean>();

	private Tencent mTencent;// 新建Tencent实例用于调用分享方法

	public static NewsContainerPullScrollFragment newInstance(String url, boolean isVisibleToUser) {
		NewsContainerPullScrollFragment fragment = new NewsContainerPullScrollFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_container_pullscroll, container, false);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		txt_time = (TextView) view.findViewById(R.id.txt_time);
		txt_news = (TextView) view.findViewById(R.id.txt_news);
		mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pulltorefreshscrollview);
		layout_more = (FrameLayout) view.findViewById(R.id.layout_more);
		img_msg = (ImageView) view.findViewById(R.id.img_msg);
		img_share = (ImageView) view.findViewById(R.id.img_share);
		img_love = (ImageView) view.findViewById(R.id.img_love);
		img_top = (ImageView) view.findViewById(R.id.img_top);
		txt_msg = (TextView) view.findViewById(R.id.txt_msg);

		mTencent = Tencent.createInstance(UrlUtils.APP_ID, getActivity());
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
		mPullToRefreshScrollView.setMode(Mode.PULL_FROM_START);

		NewsContainerFootExpendListFragment fragment = NewsContainerFootExpendListFragment.newInstance(url, true);
		getChildFragmentManager().beginTransaction().replace(R.id.layout_more, fragment).commit();
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
		mPullToRefreshScrollView.setOnRefreshListener(this);
		img_msg.setOnClickListener(this);
		txt_msg.setOnClickListener(this);
		img_share.setOnClickListener(this);
		img_top.setOnClickListener(this);
		img_love.setOnClickListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public NewsContainerJson call() throws Exception {
		// TODO Auto-generated method stub
		ShareBean bean = new ShareBean();
		bean.setChannelName("微信好友");
		bean.setResId(R.drawable.share_weixin_friend);
		shareList.add(bean);

		bean = new ShareBean();
		bean.setChannelName("朋友圈");
		bean.setResId(R.drawable.share_weixin_timeline);
		shareList.add(bean);

		bean = new ShareBean();
		bean.setChannelName("新浪微博");
		bean.setResId(R.drawable.share_weibo);
		shareList.add(bean);

		bean = new ShareBean();
		bean.setChannelName("QQ好友");
		bean.setResId(R.drawable.share_qq);
		shareList.add(bean);

		bean = new ShareBean();
		bean.setChannelName("QQ空间");
		bean.setResId(R.drawable.share_qzone);
		shareList.add(bean);

		NewsContainerJson mNewsContainerJson = new NewsContainerJson();
		mNewsContainerJson.setList(TagNewsJsoupService.parseArticle(url, pageNo));
		return mNewsContainerJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(NewsContainerJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null && result.getList() != null) {
			txt_title.setText(result.getList().get(0).getTitle());
			txt_time.setText(result.getList().get(0).getTime());
			txt_news.setText(result.getList().get(0).getNewsbox());
		}
		mPullToRefreshScrollView.onRefreshComplete();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener
	 * #onRefresh(com.handmark.pulltorefresh.library.PullToRefreshBase)
	 */
	@Override
	public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshScrollView.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
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
		case R.id.img_msg:
		case R.id.txt_msg:
			// 留言
			MNewsCommentPullListFragmentActivity.startMNewsCommentPullListFragmentActivity(getActivity(), url.replace(UrlUtils.ARTICLE_URL, ""));
			break;
		case R.id.img_share:
			// 分享
			showPopupWindow();
			break;
		case R.id.img_love:
			// 收藏
			usercollect(UrlUtils.USERCOLLECT, url.replace(UrlUtils.ARTICLE_URL, ""));
			break;
		case R.id.img_top:
			// 置顶
			mPullToRefreshScrollView.getRefreshableView().scrollTo(0, 0);
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.qianbailu.fragment.BaseV4Fragment#onErrorResponse(com.android
	 * .volley.VolleyError)
	 */
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		super.onErrorResponse(error);
		System.out.println(error);
	}

	public void usercollect(final String href, final String uid) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String, String> params = new HashMap<String, String>();
		params.put("User-Agent", UrlUtils.userAgent);
		params.put("Referer", UrlUtils.ARTICLE_URL + uid);
		params.put("Cookie", UrlUtils.COOKIE);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href + uid, params, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href + uid);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					CommonDataJson result = gson.fromJson(response.toString(), CommonDataJson.class);
					Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, NewsContainerPullScrollFragment.this);
		requestQueue.add(jsonObjectRequest);
	}

	/**
	 * 设置字体大小
	 */
	public void setLargeSize(boolean largeSize) {
		if (largeSize) {
			txt_news.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		} else {
			txt_news.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		}
	}

	public void showPopupWindow() {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_share_gridview_popup_window, null);
		final WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), (int) ScreenUtils.getIntToDip(getActivity(), 220));
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);// 设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));

		popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
		GridView mGridView = (GridView) view.findViewById(R.id.gridview);
		ShareGridAdapter mShareGridAdapter = new ShareGridAdapter(getActivity(), shareList);
		mGridView.setAdapter(mShareGridAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String channelName = shareList.get((int) id).getChannelName();
				if (channelName.equals("QQ好友")) {
					shareToQQ();
				} else if (channelName.equals("QQ空间")) {
					shareToQZone();
				}else if (channelName.equals("微信好友")) {
					shareText(txt_title.getText().toString(),SendMessageToWX.Req.WXSceneSession);
				}else if (channelName.equals("朋友圈")) {
					shareText(txt_title.getText().toString(),SendMessageToWX.Req.WXSceneTimeline);
				}
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
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = bgAlpha;
		getActivity().getWindow().setAttributes(lp);
	}
    private abstract class ShareContent {  
        protected abstract int getShareWay();  
        protected abstract String getContent();  
        protected abstract String getTitle();  
        protected abstract String getURL();  
        protected abstract int getPictureResource();  
    }  
	 /* 
     * 分享文字 
     */  
    private void shareText(String shareContent, int shareType) {  
        String text = shareContent;  
        //初始化一个WXTextObject对象  
        WXTextObject textObj = new WXTextObject();  
        textObj.text = text;  
        //用WXTextObject对象初始化一个WXMediaMessage对象  
        WXMediaMessage msg = new WXMediaMessage();  
        msg.mediaObject = textObj;  
        msg.description = text;  
        //构造一个Req  
        SendMessageToWX.Req req = new SendMessageToWX.Req();  
        //transaction字段用于唯一标识一个请求  
        req.transaction = buildTransaction("textshare");  
        req.message = msg;  
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。  
        req.scene = shareType;  
        CommonApplication.api.sendReq(req);  
    } 
    private static final int THUMB_SIZE = 150;  
    /* 
     * 分享图片 
     */  
    private void sharePicture(ShareContent shareContent, int shareType) {  
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), shareContent.getPictureResource());  
        WXImageObject imgObj = new WXImageObject(bitmap);  
           
        WXMediaMessage msg = new WXMediaMessage();  
        msg.mediaObject = imgObj;  
           
        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);  
        bitmap.recycle();  
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);  //设置缩略图  
           
        SendMessageToWX.Req req = new SendMessageToWX.Req();  
        req.transaction = buildTransaction("imgshareappdata");  
        req.message = msg;  
        req.scene = shareType;  
        CommonApplication.api.sendReq(req);  
    }  
   
    /* 
     * 分享链接 
     */  
    private void shareWebPage(String title,String content,String url,int resId, int shareType) {  
        WXWebpageObject webpage = new WXWebpageObject();  
        webpage.webpageUrl = url;  
        WXMediaMessage msg = new WXMediaMessage(webpage);  
        msg.title = title;  
        msg.description = content;  
           
        Bitmap thumb = BitmapFactory.decodeResource(getActivity().getResources(),resId);  
        if(thumb == null) {  
            Toast.makeText(getActivity(), "图片不能为空", Toast.LENGTH_SHORT).show();  
        } else {  
            msg.thumbData = Util.bmpToByteArray(thumb, true);  
        }  
           
        SendMessageToWX.Req req = new SendMessageToWX.Req();  
        req.transaction = buildTransaction("webpage");  
        req.message = msg;  
        req.scene = shareType;  
        CommonApplication.api.sendReq(req);  
    }  
      
    /* 
     * 分享视频 
     */  
    private void shareVideo(ShareContent shareContent, int shareType) {  
        WXVideoObject video = new WXVideoObject();  
        video.videoUrl = shareContent.getURL();  
  
        WXMediaMessage msg = new WXMediaMessage(video);  
        msg.title = shareContent.getTitle();  
        msg.description = shareContent.getContent();  
        Bitmap thumb = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_launcher);  
//      BitmapFactory.decodeStream(new URL(video.videoUrl).openStream());  
        /** 
         * 测试过程中会出现这种情况，会有个别手机会出现调不起微信客户端的情况。造成这种情况的原因是微信对缩略图的大小、title、description等参数的大小做了限制，所以有可能是大小超过了默认的范围。 
         * 一般情况下缩略图超出比较常见。Title、description都是文本，一般不会超过。 
         */  
        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(thumb, THUMB_SIZE, THUMB_SIZE, true);  
        thumb.recycle();  
        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);  
          
        SendMessageToWX.Req req = new SendMessageToWX.Req();  
        req.transaction = buildTransaction("video");  
        req.message = msg;  
        req.scene =  shareType;  
        CommonApplication.api.sendReq(req);  
    }  
    
    private String buildTransaction(final String type) {  
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();  
    }  
	private Bundle params;

	private void shareToQZone() {
		params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, getActivity().getResources().getString(R.string.app_name));// 标题
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, txt_title.getText().toString());// 摘要
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);// 内容地址
		ArrayList<String> imgUrlList = new ArrayList<String>();
		// imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
		// 分享操作要在主线程中完成
		ThreadManager.getMainHandler().post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mTencent.shareToQzone(getActivity(), params, NewsContainerPullScrollFragment.this);
			}
		});
	}

	private void shareToQQ() {
		params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, getActivity().getResources().getString(R.string.app_name));// 标题
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, txt_title.getText().toString());// 摘要
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);// 内容地址
		// params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imageUrl.getText().toString());
		// params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
		// "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");//
		// 网络图片地址　　
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getActivity().getResources().getString(R.string.app_name));
		params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "");
		// 分享操作要在主线程中完成
		ThreadManager.getMainHandler().post(new Runnable() {
			@Override
			public void run() {
				mTencent.shareToQQ(getActivity(), params, NewsContainerPullScrollFragment.this);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tencent.tauth.IUiListener#onCancel()
	 */
	@Override
	public void onCancel() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tencent.tauth.IUiListener#onComplete(java.lang.Object)
	 */
	@Override
	public void onComplete(Object arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tencent.tauth.IUiListener#onError(com.tencent.tauth.UiError)
	 */
	@Override
	public void onError(UiError arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Tencent.onActivityResultData(requestCode, resultCode, data, this);
		if (requestCode == Constants.REQUEST_API) {
			if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
				Tencent.handleResultData(data, this);
			}
		}
	}

}
