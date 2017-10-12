/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午2:18:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.mystock;

import java.io.Serializable;
import java.util.List;

import com.open.baidu.finance.bean.mystock.GroupBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午2:18:07
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class GroupListJson implements Serializable{
	private List<GroupBean> groupList;

	public List<GroupBean> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupBean> groupList) {
		this.groupList = groupList;
	}

}
