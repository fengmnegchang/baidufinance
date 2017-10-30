/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20下午4:17:29
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.open.android.fragment.common.CommonPullToRefreshListFragment;
import com.open.android.utils.ScreenUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.SearchStockFragmentActivity;
import com.open.baidu.finance.adapter.article.EmojiAdapter;
import com.open.baidu.finance.adapter.article.EmojiViewPagerAdapter;
import com.open.baidu.finance.adapter.article.NewsCommentAdapter;
import com.open.baidu.finance.bean.article.CommentBean;
import com.open.baidu.finance.bean.article.EmojiBean;
import com.open.baidu.finance.json.article.EmojiJson;
import com.open.baidu.finance.json.article.GetCommentListJson;
import com.open.baidu.finance.utils.UrlUtils;
/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20下午4:17:29
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class MNewsCommentPullListFragment extends CommonPullToRefreshListFragment<CommentBean, GetCommentListJson> implements OnClickListener {
	public NewsCommentAdapter mNewsCommentAdapter;
	public TextView edit_search;
	public PopupWindow popupWindow;
	public List<EmojiJson> emojilist = new ArrayList<EmojiJson>();
	private List<EmojiAdapter> adapterlist = new ArrayList<EmojiAdapter>();
	private List<View> viewlist = new ArrayList<View>();
	public ImageView img_nocommnets;
	public TextView txt_nocomment;
	
	
	public static MNewsCommentPullListFragment newInstance(String url, boolean isVisibleToUser) {
		MNewsCommentPullListFragment fragment = new MNewsCommentPullListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news_comment_pulllistview, container, false);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
		edit_search = (TextView) view.findViewById(R.id.edit_search);
		txt_nocomment = (TextView) view.findViewById(R.id.txt_nocomment);
		img_nocommnets = (ImageView) view.findViewById(R.id.img_nocommnets);
		return view;
	}
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mNewsCommentAdapter = new NewsCommentAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mNewsCommentAdapter);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#bindEvent()
	 */
	@Override
	public void bindEvent() {
		// TODO Auto-generated method stub
		super.bindEvent();
		edit_search.setOnClickListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			volleyJson(url);
			try {
				String fileNames[] = getActivity().getAssets().list("emoji");
				EmojiBean emojiBean;
				List<EmojiBean> elist = new ArrayList<EmojiBean>();
				for(int i=0;i<fileNames.length;i++){
					emojiBean = new EmojiBean();
					emojiBean.setFaceName(fileNames[i]);
					elist.add(emojiBean);
					
					if(i==17 || i==35 || i==fileNames.length-1){
						EmojiJson mEmojiJson = new EmojiJson();
						mEmojiJson.setList(elist);
						emojilist.add(mEmojiJson);
						
						elist = new ArrayList<EmojiBean>();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public GetCommentListJson call() throws Exception {
		// TODO Auto-generated method stub
		return super.call();
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback(com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(GetCommentListJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if(result!=null){
			Log.i(TAG, "getMode ===" + mPullToRefreshListView.getCurrentMode());
			if (mPullToRefreshListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				if (result.getData().getComments() != null && result.getData().getComments().size() > 0) {
					txt_nocomment.setVisibility(View.GONE);
					img_nocommnets.setVisibility(View.GONE);
					list.addAll(result.getData().getComments());
				}else{
					txt_nocomment.setVisibility(View.VISIBLE);
					img_nocommnets.setVisibility(View.VISIBLE);
				}
				pageNo = 1;
			} else {
				if (result.getData().getComments() != null && result.getData().getComments().size() > 0) {
					list.addAll(result.getData().getComments());
				}
			}
			mNewsCommentAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
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
	
	@Override
	public void volleyJson(final String href) {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		Map<String,String> params = new HashMap<String,String>(); 
		params.put("User-Agent",UrlUtils.userAgent);
//		params.put("Referer","https://gupiao.baidu.com/my/");
		params.put("Cookie", UrlUtils.COOKIE);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, href,params,null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println("href=" + href);
				System.out.println("response=" + response.toString());
				try {
					Gson gson = new Gson();
					GetCommentListJson result = gson.fromJson(response.toString(), GetCommentListJson.class);
					onCallback(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}, MNewsCommentPullListFragment.this);
		requestQueue.add(jsonObjectRequest);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.edit_search){
			showPopupWindow();
		}
	}
	int currentIndex;
	public void showPopupWindow() {
		// 加载布局
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_add_comment_popup_window, null);
		final WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		// 找到布局的控件
		// 实例化popupWindow
		popupWindow = new PopupWindow(view, manager.getDefaultDisplay().getWidth(), (int)ScreenUtils.getIntToDip(getActivity(), 180));
		// 控制键盘是否可以获得焦点
		popupWindow.setFocusable(true);
		setBackgroundAlpha(0.5f);//设置屏幕透明度
		// 设置popupWindow弹出窗体的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_popup_drag_shape));
		
		popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0,
	            0);
		
		ImageView img_search_stock = (ImageView) view.findViewById(R.id.img_search_stock);
		ImageView img_face = (ImageView) view.findViewById(R.id.img_face);
		Button btn_send = (Button) view.findViewById(R.id.btn_send);
		final ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		final LinearLayout layout_dot = (LinearLayout) view.findViewById(R.id.layout_dot);
		final ImageView[] dots = new ImageView[emojilist.size()];
		for (int i = 0; i < emojilist.size(); i++) {
				GridView gridView = new GridView(getActivity());
				EmojiAdapter adapter = new EmojiAdapter(getActivity(), emojilist.get(i).getList());
				gridView.setAdapter(adapter);
				adapterlist.add(adapter);
				
//				gridView.setOnItemClickListener(this);
				gridView.setNumColumns(6);
				gridView.setBackgroundColor(Color.TRANSPARENT);
				gridView.setHorizontalSpacing(1);
				gridView.setVerticalSpacing(1);
				gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
				gridView.setCacheColorHint(0);
				gridView.setPadding(5, 0, 5, 0);

				gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
				gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						(int)ScreenUtils.getIntToDip(getActivity(), 160)));
				gridView.setGravity(Gravity.CENTER);
				this.viewlist.add(gridView);
		}
		
		for (int i = 0; i < emojilist.size(); i++) {
			ImageView img = new ImageView(getActivity());
			img.setImageResource(R.drawable.emoji_dot);
			img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			img.setPadding(15, 15, 15, 15);
			img.setClickable(true);
			dots[i] = img;
			dots[i].setEnabled(true);
			dots[i].setTag(i);
			layout_dot.addView(dots[i]);
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);
		viewpager.setCurrentItem(0);
		
		EmojiViewPagerAdapter mEmojiViewPagerAdapter = new EmojiViewPagerAdapter(getActivity(),viewlist);
		viewpager.setAdapter(mEmojiViewPagerAdapter);
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				viewpager.setCurrentItem(arg0);
				dots[arg0].setEnabled(false);
				dots[currentIndex].setEnabled(true);
				currentIndex = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		img_search_stock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchStockFragmentActivity.startSearchStockFragmentActivity(getActivity(), url);
			}
		});
		img_face.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(layout_dot.getVisibility()==View.VISIBLE){
					viewpager.setVisibility(View.GONE);
					layout_dot.setVisibility(View.GONE);
					popupWindow.update(manager.getDefaultDisplay().getWidth(), (int)ScreenUtils.getIntToDip(getActivity(), 180));
				}else{
					viewpager.setVisibility(View.VISIBLE);
					layout_dot.setVisibility(View.VISIBLE);
					popupWindow.update(manager.getDefaultDisplay().getWidth(), (int)ScreenUtils.getIntToDip(getActivity(), 350));
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
	    WindowManager.LayoutParams lp = getActivity().getWindow()
	            .getAttributes();
	    lp.alpha = bgAlpha;
	    getActivity().getWindow().setAttributes(lp);
	}
	
 
}
