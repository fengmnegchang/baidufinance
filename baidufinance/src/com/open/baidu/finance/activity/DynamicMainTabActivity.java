/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12上午9:59:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.open.android.activity.CommonTabActivity;
import com.open.android.utils.ScreenUtils;
import com.open.baidu.finance.R;
import com.open.baidu.finance.activity.mystock.MyStockPullToRefreshPinnedSectionListViewActivity;
import com.open.baidu.finance.bean.MainTabBean;
import com.open.baidu.finance.json.MainTabJson;
/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12上午9:59:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class DynamicMainTabActivity extends CommonTabActivity<MainTabJson>{
    private TabHost mTabHost;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_tab_main_dynamic);
        init();
    }

    @Override
    protected void findView() {
        // TODO Auto-generated method stub
        super.findView();
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mTabHost = getTabHost();
        doAsync(this, this, this);
    }


    @Override
    public MainTabJson call() throws Exception {
    	MainTabJson  mNavMJson = new MainTabJson();
    	List<MainTabBean> list = new ArrayList<MainTabBean>();
    	MainTabBean mbean = new MainTabBean();
    	mbean.setTitle("自选股");
    	mbean.setResId(R.drawable.selector_tab_mystock);
    	list.add(mbean);
    	
    	mbean = new MainTabBean();
    	mbean.setTitle("资讯");
    	mbean.setResId(R.drawable.selector_tab_news);
    	list.add(mbean);
    	
    	mbean = new MainTabBean();
    	mbean.setTitle("智能选股");
    	mbean.setResId(R.drawable.selector_tab_hot);
    	list.add(mbean);
    	
    	mbean = new MainTabBean();
    	mbean.setTitle("行情");
    	mbean.setResId(R.drawable.selector_tab_market);
    	list.add(mbean);
    	
    	mbean = new MainTabBean();
    	mbean.setTitle("我");
    	mbean.setResId(R.drawable.selector_tab_me);
    	list.add(mbean);
    	
    	mNavMJson.setList(list);
        return mNavMJson;
    }

    @Override
    public void onCallback(MainTabJson result) {
        // TODO Auto-generated method stub
        super.onCallback(result);
        if(result==null){
            return;
        }
        for(int i=0;i<result.getList().size();i++){
        	MainTabBean mbean = result.getList().get(i);
            TabHost.TabSpec tab_main = mTabHost.newTabSpec(mbean.getTitle());
            Intent intent = null;
            if(mbean.getTitle().equals("自选股")){
                  intent = new Intent(this, MyStockPullToRefreshPinnedSectionListViewActivity.class);
            }else if(mbean.getTitle().equals("资讯")){
                  intent = new Intent(this, CommonDotPagerFragmentFragmentActivity.class);
            }else if(mbean.getTitle().equals("智能选股")){
                  intent = new Intent(this, CommonDotPagerFragmentFragmentActivity.class);
            }else if(mbean.getTitle().equals("行情")){
                  intent = new Intent(this, CommonDotPagerFragmentFragmentActivity.class);
            }else if(mbean.getTitle().equals("我")){
                intent = new Intent(this, CommonDotPagerFragmentFragmentActivity.class);
            }
//            intent.putExtra("URL",mbean.getHref());
            tab_main.setContent(intent).setIndicator(mbean.getTitle());
            mTabHost.addTab(tab_main);

            View viewRadio = LayoutInflater.from(this).inflate(R.layout.layout_tab_main_dynamic_radio, null);
            RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
            radio.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mbean.getResId()), null, null);
            radio.setText(mbean.getTitle());
            if(i==0){
                radio.setChecked(true);
            }else {
                radio.setChecked(false);
            }
            final int position = i;
            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurrentTab(position);
                }
            });
            
            LayoutParams params = new LayoutParams((int) ScreenUtils.getIntToDip(this, 72), LayoutParams.WRAP_CONTENT);
            mRadioGroup.addView(viewRadio,params);
        }
    }

    protected void setCurrentTab(int position){
    	 mTabHost.setCurrentTab(position);
        for(int i=0;i<mRadioGroup.getChildCount();i++){
            View viewRadio = mRadioGroup.getChildAt(i);
            RadioButton radio = (RadioButton) viewRadio.findViewById(R.id.radio_item);
            if(i==position){
                radio.setChecked(true);
            } else{
            	radio.setChecked(false);
            }
            mRadioGroup.invalidate();
        }
    }
 
}
