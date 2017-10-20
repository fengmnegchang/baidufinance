/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20下午3:43:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.json.article;

import java.util.List;

import com.open.baidu.finance.bean.article.CommentBean;
import com.open.baidu.finance.json.CommonDataJson;
import com.open.baidu.finance.json.article.GetCommentListJson.GetCommentListModel;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-20下午3:43:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class GetCommentListJson extends CommonDataJson<GetCommentListModel> {

	public class GetCommentListModel{
		private String sourceId;//"TT1049386",
		private String sourceName;//"财经资讯",
		private String sourceType;//"1",
		private String sourceUrl;//null,
		private List<CommentBean> comments;//Array[2],
		private String total;//"1",
		private String timeStamp;//1508484858403,
		private String guessType;//0,
		private String isAdmin;//0
		
		public String getSourceId() {
			return sourceId;
		}
		public void setSourceId(String sourceId) {
			this.sourceId = sourceId;
		}
		public String getSourceName() {
			return sourceName;
		}
		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}
		public String getSourceType() {
			return sourceType;
		}
		public void setSourceType(String sourceType) {
			this.sourceType = sourceType;
		}
		public String getSourceUrl() {
			return sourceUrl;
		}
		public void setSourceUrl(String sourceUrl) {
			this.sourceUrl = sourceUrl;
		}
		public List<CommentBean> getComments() {
			return comments;
		}
		public void setComments(List<CommentBean> comments) {
			this.comments = comments;
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
		public String getGuessType() {
			return guessType;
		}
		public void setGuessType(String guessType) {
			this.guessType = guessType;
		}
		public String getIsAdmin() {
			return isAdmin;
		}
		public void setIsAdmin(String isAdmin) {
			this.isAdmin = isAdmin;
		}
		
		
	}
}

