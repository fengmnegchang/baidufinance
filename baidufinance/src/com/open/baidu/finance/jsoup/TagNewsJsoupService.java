/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午1:58:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.jsoup;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.open.android.jsoup.CommonService;
import com.open.baidu.finance.bean.article.NewsContainerBean;
import com.open.baidu.finance.bean.news.HotTiebaTopicBean;
import com.open.baidu.finance.bean.news.LiveShowBean;
import com.open.baidu.finance.bean.news.TagNewsBean;
import com.open.baidu.finance.utils.UrlUtils;

/**
 ***************************************************************************************************************************************************************************** 
 * 
 * @author :fengguangjing
 * @createTime:2017-10-12下午1:58:56
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 ***************************************************************************************************************************************************************************** 
 */
public class TagNewsJsoupService extends CommonService {

	/***
	 * 今日要闻
	 */
	public static List<TagNewsBean> parseTodayNews(String href, int pageNo) {
		List<TagNewsBean> list = new ArrayList<TagNewsBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("div.news-section").first();
				Elements moduleElements = globalnavElement.select("a");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						TagNewsBean sbean = new TagNewsBean();
						try {
							try {
								Element aElement = moduleElements.get(i).select("a").first();
								if (aElement != null) {
									String hrefa = UrlUtils.GUPIAO_BAIDU+aElement.attr("href");
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
									sbean.setUrl(hrefa);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("a").first();
								if (imgElement != null) {
									String title = imgElement.text();
									Log.i(TAG, "i==" + i + ";title==" + title);
									sbean.setTitle(title);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						list.add(sbean);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/***
	 * 直播
	 */
	public static List<LiveShowBean> parseLiveNews(String href, int pageNo) {
		List<LiveShowBean> list = new ArrayList<LiveShowBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("div.live-show").first();
				Elements moduleElements = globalnavElement.select("div._li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						LiveShowBean sbean = new LiveShowBean();
						try {
							try {
								Element aElement = moduleElements.get(i).select("a").first();
								if (aElement != null) {
									String hrefa = aElement.attr("href");
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
									sbean.setHref(hrefa);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("img").first();
								if (imgElement != null) {
									String src = imgElement.attr("src");
									Log.i(TAG, "i==" + i + ";src==" + src);
									sbean.setSrc(src);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Element imgElement = moduleElements.get(i).select("div.desc").first();
								if (imgElement != null) {
									String desc = imgElement.text();
									Log.i(TAG, "i==" + i + ";desc==" + desc);
									sbean.setDesc(desc);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						list.add(sbean);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/***
	 * 精华帖子
	 */
	public static List<HotTiebaTopicBean> parseHotTiebaTopic(String href, int pageNo) {
		List<HotTiebaTopicBean> list = new ArrayList<HotTiebaTopicBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("div.hot-tieba-topic").first();
				Elements moduleElements = globalnavElement.select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						HotTiebaTopicBean sbean = new HotTiebaTopicBean();
						try {
							try {
								Element aElement = moduleElements.get(i).select("a").first();
								if (aElement != null) {
									String hrefa = aElement.attr("href");
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
									sbean.setHref(hrefa);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Element imgElement = moduleElements.get(i).select("a").first();
								if (imgElement != null) {
									String title = imgElement.text();
									Log.i(TAG, "i==" + i + ";title==" + title);
									sbean.setTitle(title);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Element imgElement = moduleElements.get(i).select("span").first();
								if (imgElement != null) {
									String time = imgElement.text();
									Log.i(TAG, "i==" + i + ";time==" + time);
									sbean.setTime(time);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Element imgElement = moduleElements.get(i).select("p").first();
								if (imgElement != null) {
									String desc = imgElement.text();
									Log.i(TAG, "i==" + i + ";desc==" + desc);
									sbean.setDesc(desc);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							
							try {
								Element imgElement = moduleElements.get(i).select("div.statistic").first();
								if (imgElement != null) {
									String readcount = imgElement.select("span").get(1).text();
									Log.i(TAG, "i==" + i + ";readcount==" + readcount);
									sbean.setReadcount(readcount);
									
									
									String replycount = imgElement.select("span").get(3).text();
									Log.i(TAG, "i==" + i + ";replycount==" + replycount);
									sbean.setReplycount(replycount);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						list.add(sbean);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/***
	 *  
	 */
	public static List<NewsContainerBean> parseArticle(String href, int pageNo) {
		List<NewsContainerBean> list = new ArrayList<NewsContainerBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("div.news-container").first();
				Elements moduleElements = globalnavElement.select("div.news-container");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						NewsContainerBean sbean = new NewsContainerBean();
						try {
							try {
								Element aElement = moduleElements.get(i).select("h1").first();
								if (aElement != null) {
									String title = aElement.text();
									Log.i(TAG, "i==" + i + ";title==" + title);
									sbean.setTitle(title);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Element imgElement = moduleElements.get(i).select("div.news-subtitle").first();
								if (imgElement != null) {
									String time = imgElement.text();
									Log.i(TAG, "i==" + i + ";time==" + time);
									sbean.setTime(time);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Element imgElement = moduleElements.get(i).select("div.news-box").first();
								if (imgElement != null) {
									String newsbox = imgElement.text();
									Log.i(TAG, "i==" + i + ";newsbox==" + newsbox);
									sbean.setNewsbox(newsbox);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						list.add(sbean);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<TagNewsBean> parseNewsContainerFoot(String href, int pageNo) {
		List<TagNewsBean> list = new ArrayList<TagNewsBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("ul.more-news-list").first();
				Elements moduleElements = globalnavElement.select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						TagNewsBean sbean = new TagNewsBean();
						try {
							try {
								Element aElement = moduleElements.get(i).select("a").first();
								if (aElement != null) {
									String url = UrlUtils.GUPIAO_BAIDU+aElement.attr("href");
									Log.i(TAG, "i==" + i + ";url==" + url);
									sbean.setUrl(url);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							try {
								Element imgElement = moduleElements.get(i).select("a").first();
								if (imgElement != null) {
									String title = imgElement.text();
									Log.i(TAG, "i==" + i + ";title==" + title);
									sbean.setTitle(title);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

						list.add(sbean);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
