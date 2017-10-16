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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.open.android.fragment.BaseV4Fragment;
import com.open.android.json.CommonJson;
import com.open.baidu.finance.R;

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
public class NewGroupNameFragment extends BaseV4Fragment<CommonJson, NewGroupNameFragment> {
	String groupName;
	public EditText edit_name;
	
	public static NewGroupNameFragment newInstance(String url, String groupName,boolean isVisibleToUser) {
		NewGroupNameFragment fragment = new NewGroupNameFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		fragment.groupName = groupName;
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_group_name, container, false);
		edit_name = (EditText) view.findViewById(R.id.edit_name);
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
 
}
