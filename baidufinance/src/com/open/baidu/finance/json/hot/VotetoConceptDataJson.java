/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-30下午4:24:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.hot;

import com.open.baidu.finance.json.CommonDataJson;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-30下午4:24:16
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class VotetoConceptDataJson extends CommonDataJson<VotetoConceptDataJson.VotetoConceptDataModel> {

	public class VotetoConceptDataModel {
		private int conceptId;// 1000749,
		private String likeNum;// "16",
		private String dislikeNum;// "8",
		private long updatetime;// :1509351573550

		public int getConceptId() {
			return conceptId;
		}

		public void setConceptId(int conceptId) {
			this.conceptId = conceptId;
		}

		public String getLikeNum() {
			return likeNum;
		}

		public void setLikeNum(String likeNum) {
			this.likeNum = likeNum;
		}

		public String getDislikeNum() {
			return dislikeNum;
		}

		public void setDislikeNum(String dislikeNum) {
			this.dislikeNum = dislikeNum;
		}

		public long getUpdatetime() {
			return updatetime;
		}

		public void setUpdatetime(long updatetime) {
			this.updatetime = updatetime;
		}

	}
}
