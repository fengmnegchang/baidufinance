/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午10:29:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.hot;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshPinnedSectionListView;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.hot.HotConceptPinnedSectionListFragmentActivity;
import com.open.baidu.finance.activity.hot.SentimentIndicatorFragmentActivity;
import com.open.baidu.finance.activity.hot.SurveyPinnedSectionListFragmentActivity;
import com.open.baidu.finance.adapter.hot.MsgStockPinnedSectionListAdapter;
import com.open.baidu.finance.bean.hot.HotConceptBean;
import com.open.baidu.finance.bean.hot.MsgStockBean;
import com.open.baidu.finance.bean.hot.OrganizationSurveyBean;
import com.open.baidu.finance.bean.hot.SentimentBean;
import com.open.baidu.finance.json.hot.HotConceptJson;
import com.open.baidu.finance.json.hot.MsgStockJson;
import com.open.baidu.finance.json.hot.OrganizationSurveyDateJson;
import com.open.baidu.finance.json.hot.OrganizationSurveyJson;
import com.open.baidu.finance.json.hot.SentimentDateJson;
import com.open.baidu.finance.json.hot.SentimentJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-27上午10:29:34
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class MsgStockPinnedSectionListFragment extends BaseV4Fragment<MsgStockJson, MsgStockPinnedSectionListFragment> implements OnRefreshListener<ListView>,OnClickListener {
	public PullToRefreshPinnedSectionListView mPullToRefreshPinnedSectionListView;
	private MsgStockPinnedSectionListAdapter mMsgStockPinnedSectionListAdapter;
	private List<MsgStockBean> list = new ArrayList<MsgStockBean>();
	private View headview;
	private LinearLayout layout_hot,layout_notice,layout_survey,layout_sentiment;

	public static MsgStockPinnedSectionListFragment newInstance(String url, boolean isVisibleToUser) {
		MsgStockPinnedSectionListFragment fragment = new MsgStockPinnedSectionListFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mystock_pulltorefresh_pinnedsection_listview, container, false);
		mPullToRefreshPinnedSectionListView = (PullToRefreshPinnedSectionListView) view.findViewById(R.id.pull_refresh_list);
		headview = LayoutInflater.from(getActivity()).inflate(R.layout.layout_head_msg_stock, null);
		layout_hot = (LinearLayout) headview.findViewById(R.id.layout_hot);
		layout_notice = (LinearLayout) headview.findViewById(R.id.layout_notice);
		layout_survey = (LinearLayout) headview.findViewById(R.id.layout_survey);
		layout_sentiment = (LinearLayout) headview.findViewById(R.id.layout_sentiment);
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.android.fragment.BaseV4MVPPFragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mPullToRefreshPinnedSectionListView.getRefreshableView().addHeaderView(headview);
		
		mMsgStockPinnedSectionListAdapter = new MsgStockPinnedSectionListAdapter(getActivity(), list);
		mPullToRefreshPinnedSectionListView.setAdapter(mMsgStockPinnedSectionListAdapter);
		mPullToRefreshPinnedSectionListView.setMode(Mode.PULL_FROM_START);
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
		mPullToRefreshPinnedSectionListView.setOnRefreshListener(this);
		layout_hot.setOnClickListener(this);
		layout_notice.setOnClickListener(this);
		layout_survey.setOnClickListener(this);
		layout_sentiment.setOnClickListener(this);
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
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#call()
	 */
	@Override
	public MsgStockJson call() throws Exception {
		// TODO Auto-generated method stub
		MsgStockJson mMsgStockJson = new MsgStockJson();
		List<MsgStockBean> clist = new ArrayList<MsgStockBean>();
		MsgStockBean mMsgStockBean;
		//最新热点
		try {
			InputStream is = getActivity().getResources().getAssets().open("hot.json");
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			String data = new String(imgdata);
			Gson gson = new Gson();

			HotConceptJson mHotConceptJson = gson.fromJson(data, HotConceptJson.class);
			
			//最新热点
			mMsgStockBean = new MsgStockBean();
			String dateTime = mHotConceptJson.getList().get(0).getTime();
			mMsgStockBean.setDateTime(dateTime);
			mMsgStockBean.setType(0);
			mMsgStockBean.setTypeName("最新热点");
			mMsgStockBean.setViewType(1);
			clist.add(mMsgStockBean);
			for(HotConceptBean subBean:mHotConceptJson.getList()){
				mMsgStockBean = new MsgStockBean();
				mMsgStockBean.setDateTime(dateTime);
				mMsgStockBean.setType(0);
				mMsgStockBean.setTypeName("最新热点");
				mMsgStockBean.setViewType(0);
				mMsgStockBean.setSurveyCount(subBean.getSearchCount()+"");
				mMsgStockBean.setName(subBean.getName());
				mMsgStockBean.setSubject(subBean.getSubject());
				mMsgStockBean.setStockName(subBean.getStocklist().get(0).getStockName());
				mMsgStockBean.setStockCode(subBean.getStocklist().get(0).getStockCode());
				mMsgStockBean.setRate(subBean.getStocklist().get(0).getRate());
				clist.add(mMsgStockBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//机构调研
		try {
			InputStream is = getActivity().getResources().getAssets().open("survey.json");
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			String data = new String(imgdata);
			Gson gson = new Gson();

			OrganizationSurveyDateJson mOrganizationSurveyDateJson = gson.fromJson(data, OrganizationSurveyDateJson.class);
			OrganizationSurveyJson sbean = mOrganizationSurveyDateJson.getList().get(0);
			
			mMsgStockBean = new MsgStockBean();
			mMsgStockBean.setDateTime(sbean.getDateTime());
			mMsgStockBean.setType(2);
			mMsgStockBean.setTypeName("机构调研");
			mMsgStockBean.setViewType(1);
			clist.add(mMsgStockBean);
			for(OrganizationSurveyBean subBean:sbean.getList()){
				mMsgStockBean = new MsgStockBean();
				mMsgStockBean.setDateTime(sbean.getDateTime());
				mMsgStockBean.setType(2);
				mMsgStockBean.setTypeName("机构调研");
				mMsgStockBean.setViewType(0);
				mMsgStockBean.setSurveyCount(subBean.getSurveyCount()+"");
				mMsgStockBean.setStockName(subBean.getStockName());
				mMsgStockBean.setStockCode(subBean.getStockCode());
				mMsgStockBean.setSurveyNames(subBean.getSurveyNames());
				clist.add(mMsgStockBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//舆情利空
		try {
			InputStream is = getActivity().getResources().getAssets().open("sentiment-good.json");
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			String data = new String(imgdata);
			Gson gson = new Gson();

			SentimentDateJson mSentimentDateJson = gson.fromJson(data, SentimentDateJson.class);
			SentimentJson sbean = mSentimentDateJson.getList().get(0);
			
			mMsgStockBean = new MsgStockBean();
			mMsgStockBean.setDateTime(sbean.getDateTime());
			mMsgStockBean.setType(3);
			mMsgStockBean.setTypeName("舆情利好");
			mMsgStockBean.setViewType(1);
			clist.add(mMsgStockBean);
			for(SentimentBean subBean:sbean.getList()){
				mMsgStockBean = new MsgStockBean();
				mMsgStockBean.setDateTime(sbean.getDateTime());
				mMsgStockBean.setType(3);
				mMsgStockBean.setTypeName("舆情利好");
				mMsgStockBean.setViewType(0);
				mMsgStockBean.setSurveyCount(subBean.getSurveyCount()+"");
				mMsgStockBean.setStockName(subBean.getStockName());
				mMsgStockBean.setStockCode(subBean.getStockCode());
				mMsgStockBean.setSurveyNames(subBean.getSurveyNames());
				mMsgStockBean.setClose(subBean.getClose());
				mMsgStockBean.setMsg(subBean.getMsg());
				mMsgStockBean.setRate(subBean.getRate());
				clist.add(mMsgStockBean);
			}
			
			//舆情利空
			mMsgStockBean = new MsgStockBean();
			mMsgStockBean.setDateTime(sbean.getDateTime());
			mMsgStockBean.setType(4);
			mMsgStockBean.setTypeName("舆情利空");
			mMsgStockBean.setViewType(1);
			clist.add(mMsgStockBean);
			for(SentimentBean subBean:sbean.getList()){
				mMsgStockBean = new MsgStockBean();
				mMsgStockBean.setDateTime(sbean.getDateTime());
				mMsgStockBean.setType(4);
				mMsgStockBean.setTypeName("舆情利空");
				mMsgStockBean.setViewType(0);
				mMsgStockBean.setSurveyCount(subBean.getSurveyCount()+"");
				mMsgStockBean.setStockName(subBean.getStockName());
				mMsgStockBean.setStockCode(subBean.getStockCode());
				mMsgStockBean.setSurveyNames(subBean.getSurveyNames());
				mMsgStockBean.setClose(subBean.getClose());
				mMsgStockBean.setMsg(subBean.getMsg());
				mMsgStockBean.setRate(subBean.getRate());
				clist.add(mMsgStockBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mMsgStockJson.setList(clist);
		return mMsgStockJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.android.fragment.common.CommonPullToRefreshListFragment#onCallback
	 * (com.open.android.json.CommonJson)
	 */
	@Override
	public void onCallback(MsgStockJson result) {
		// TODO Auto-generated method stub
		super.onCallback(result);
		if (result != null) {
			Log.i(TAG, "getMode ===" + mPullToRefreshPinnedSectionListView.getCurrentMode());
			if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_START) {
				list.clear();
				list.addAll(result.getList());
				pageNo = 1;
			} else {
				if (result.getList() != null && result.getList().size() > 0) {
					list.addAll(result.getList());
				}
			}
			mMsgStockPinnedSectionListAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshPinnedSectionListView.onRefreshComplete();
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
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
		// Update the LastUpdatedLabel
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
		// Do work to refresh the list here.
		if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_START) {
			pageNo = 1;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		} else if (mPullToRefreshPinnedSectionListView.getCurrentMode() == Mode.PULL_FROM_END) {
			pageNo++;
			weakReferenceHandler.sendEmptyMessage(MESSAGE_HANDLER);
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.layout_hot:
			HotConceptPinnedSectionListFragmentActivity.startHotConceptPinnedSectionListFragmentActivity(getActivity(), null);
			break;
		case R.id.layout_notice:
			break;
		case R.id.layout_survey:
			SurveyPinnedSectionListFragmentActivity.startSurveyPinnedSectionListFragmentActivity(getActivity(), null);
			break;
		case R.id.layout_sentiment:
			SentimentIndicatorFragmentActivity.startSentimentIndicatorFragmentActivity(getActivity(), null);
			break;
		default:
			break;
		}
	}

}
