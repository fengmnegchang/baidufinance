/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午4:33:06
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.bean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-17下午4:33:06
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ImageBean {
	/**
	 * 文件夹的第一张图片路径
	 */
	private String topImagePath;
	/**
	 * 文件夹名
	 */
	private String folderName; 
	/**
	 * 文件夹中的图片数
	 */
	private int imageCounts;
	
	public String getTopImagePath() {
		return topImagePath;
	}
	public void setTopImagePath(String topImagePath) {
		this.topImagePath = topImagePath;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getImageCounts() {
		return imageCounts;
	}
	public void setImageCounts(int imageCounts) {
		this.imageCounts = imageCounts;
	}
}
