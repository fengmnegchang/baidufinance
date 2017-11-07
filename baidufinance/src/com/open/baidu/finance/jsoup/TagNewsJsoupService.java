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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.google.gson.Gson;
import com.open.android.jsoup.CommonService;
import com.open.baidu.finance.bean.article.CommentBean;
import com.open.baidu.finance.bean.article.NewsContainerBean;
import com.open.baidu.finance.bean.hot.HotConceptBean;
import com.open.baidu.finance.bean.hot.HotStockBean;
import com.open.baidu.finance.bean.kline.NewsBean;
import com.open.baidu.finance.bean.news.AdviserPersonBean;
import com.open.baidu.finance.bean.news.ExpertViewBean;
import com.open.baidu.finance.bean.news.HotTiebaTopicBean;
import com.open.baidu.finance.bean.news.LiveShowBean;
import com.open.baidu.finance.bean.news.QuestionBean;
import com.open.baidu.finance.bean.news.TagNewsBean;
import com.open.baidu.finance.json.hot.FollowJson;
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
									String hrefa = UrlUtils.GUPIAO_BAIDU + aElement.attr("href");
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
									String url = UrlUtils.GUPIAO_BAIDU + aElement.attr("href");
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

	public static List<AdviserPersonBean> parseAdviserPerson(String href, int pageNo) {
		List<AdviserPersonBean> list = new ArrayList<AdviserPersonBean>();
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
				// Element globalnavElement =
				// doc.select("ul.more-news-list").first();
				Elements moduleElements = doc.select("div.adviser-person");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						AdviserPersonBean sbean = new AdviserPersonBean();
						try {
							try {
								Element aElement = moduleElements.get(i).select("a").first();
								if (aElement != null) {
									String url = UrlUtils.GUPIAO_BAIDU + aElement.attr("href");
									Log.i(TAG, "i==" + i + ";url==" + url);
									sbean.setHref(url);
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
								Element imgElement = moduleElements.get(i).select("div.content").first();
								if (imgElement != null) {
									String title = imgElement.select("a").first().text();
									Log.i(TAG, "i==" + i + ";title==" + title);
									sbean.setExpertName(title);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("div.content").first();
								if (imgElement != null) {
									String level = imgElement.select("h4").first().text();
									Log.i(TAG, "i==" + i + ";level==" + level);
									sbean.setLevel(level);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("div.content").first();
								if (imgElement != null) {
									String info = imgElement.select("h4").first().nextElementSibling().text();
									Log.i(TAG, "i==" + i + ";info==" + info);
									sbean.setInfo(info);
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

	public static List<ExpertViewBean> parseAdviserView(String href, int pageNo) {
		List<ExpertViewBean> list = new ArrayList<ExpertViewBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			if (pageNo > 1) {
				doc = Jsoup.parse(href);
			} else {
				doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			}

			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("ul.view-list").first();
				Elements moduleElements = globalnavElement.select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						ExpertViewBean sbean = new ExpertViewBean();
						try {
							try {
								Element imgElement = moduleElements.get(i).select("div.title").first();
								if (imgElement != null) {
									String title = imgElement.text();
									Log.i(TAG, "i==" + i + ";title==" + title);
									sbean.setTitle(title);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("div.content").first();
								if (imgElement != null) {
									String content = imgElement.text();
									Log.i(TAG, "i==" + i + ";content==" + content);
									sbean.setContent(content);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("div.time").first();
								if (imgElement != null) {
									String time = imgElement.text();
									Log.i(TAG, "i==" + i + ";time==" + time);
									sbean.setTime(time);
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

	public static List<QuestionBean> parseQuestion(String href, int pageNo) {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			if (pageNo > 1) {
				doc = Jsoup.parse(href);
			} else {
				doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			}

			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
				Element globalnavElement = doc.select("ul.qa-list").first();
				Elements moduleElements = globalnavElement.select("li");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						QuestionBean sbean = new QuestionBean();
						try {
							try {
								Element imgElement = moduleElements.get(i).select("span.uname").first();
								if (imgElement != null) {
									String uname = imgElement.text();
									Log.i(TAG, "i==" + i + ";uname==" + uname);
									sbean.setUname(uname);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("span.uname").first();
								if (imgElement != null) {
									String time = imgElement.nextElementSibling().text();
									Log.i(TAG, "i==" + i + ";time==" + time);
									sbean.setTime(time);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("div.title").first();
								if (imgElement != null) {
									String title = imgElement.text();
									Log.i(TAG, "i==" + i + ";title==" + title);
									sbean.setTitle(title);
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

	public static List<HotConceptBean> parseHot(String href, int pageNo) {
		List<HotConceptBean> list = new ArrayList<HotConceptBean>();
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
				// Element globalnavElement =
				// doc.select("div.concept-header").first();
				Elements moduleElements = doc.select("div.hot-concept");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						HotConceptBean sbean = new HotConceptBean();
						try {
							try {
								Element imgElement = moduleElements.get(i).select("a").first();
								if (imgElement != null) {
									String hrefa = UrlUtils.GUPIAO_BAIDU + imgElement.attr("href");
									Log.i(TAG, "i==" + i + ";hrefa==" + hrefa);
									sbean.setHref(hrefa);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("h2").first();
								if (imgElement != null) {
									String uname = imgElement.text();
									Log.i(TAG, "i==" + i + ";uname==" + uname);
									sbean.setName(uname);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("h3").first();
								if (imgElement != null) {
									String searchCount = imgElement.text();
									Log.i(TAG, "i==" + i + ";searchCount==" + searchCount);
									sbean.setSearchCount(searchCount);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("p").first();
								if (imgElement != null) {
									String time = imgElement.text();
									Log.i(TAG, "i==" + i + ";time==" + time);
									sbean.setTime(time);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("p").get(1);
								if (imgElement != null) {
									String subject = imgElement.text();
									Log.i(TAG, "i==" + i + ";subject==" + subject);
									sbean.setSubject(subject);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("div.concept-event").first();
								if (imgElement != null) {
									String event = imgElement.text();
									Log.i(TAG, "i==" + i + ";event==" + event);
									sbean.setEvent(event);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Elements sElements = moduleElements.get(i).select("li.no-click");
								if (sElements == null || sElements.size() == 0) {
									sElements = moduleElements.get(i).select("li.can-click");
								}
								if (sElements != null && sElements.size() > 0) {
									List<HotStockBean> stocklist = new ArrayList<HotStockBean>();
									HotStockBean hbean;
									for (int j = 0; j < sElements.size(); j++) {
										hbean = new HotStockBean();
										try {
											Element imgElement = sElements.get(j).select("a").first();
											if (imgElement != null) {
												String hrefa = UrlUtils.GUPIAO_BAIDU + imgElement.attr("href");
												Log.i(TAG, "j==" + j + ";hrefa==" + hrefa);
												hbean.setHref(hrefa);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}

										try {
											Element imgElement = sElements.get(j).select("li").first();
											if (imgElement != null) {
												String stockName = imgElement.attr("data-name");
												Log.i(TAG, "j==" + j + ";stockName==" + stockName);
												hbean.setStockName(stockName);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}

										try {
											Element imgElement = sElements.get(j).select("li").first();
											if (imgElement != null) {
												String stockCode = imgElement.attr("data-code");
												Log.i(TAG, "j==" + j + ";stockCode==" + stockCode);
												hbean.setStockCode(stockCode);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}

										try {
											Element imgElement = sElements.get(j).select("div.column2").get(1);
											if (imgElement != null) {
												String close = imgElement.text();
												Log.i(TAG, "j==" + j + ";close==" + close);
												hbean.setClose(close);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
										try {
											Element imgElement = sElements.get(j).select("div.column2").get(2);
											if (imgElement != null) {
												String rate = imgElement.text();
												Log.i(TAG, "j==" + j + ";rate==" + rate);
												hbean.setRate(rate);
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
										stocklist.add(hbean);
									}
									sbean.setStocklist(stocklist);
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

	public static List<HotStockBean> parseThmeme(String href, int pageNo) {
		List<HotStockBean> stocklist = new ArrayList<HotStockBean>();
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
				Element globalnavElement = doc.select("div.hot-concept").first();
				Elements sElements = globalnavElement.select("li.can-click");
				if (sElements == null || sElements.size() == 0) {
					sElements = globalnavElement.select("li.no-click");
				}
				if (sElements != null && sElements.size() > 0) {
					HotStockBean hbean;
					for (int j = 0; j < sElements.size(); j++) {
						hbean = new HotStockBean();
						try {
							Element imgElement = sElements.get(j).select("a").first();
							if (imgElement != null) {
								String hrefa = UrlUtils.GUPIAO_BAIDU + imgElement.attr("href");
								Log.i(TAG, "j==" + j + ";hrefa==" + hrefa);
								hbean.setHref(hrefa);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element imgElement = sElements.get(j).select("li").first();
							if (imgElement != null) {
								String stockName = imgElement.attr("data-name");
								Log.i(TAG, "j==" + j + ";stockName==" + stockName);
								hbean.setStockName(stockName);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element imgElement = sElements.get(j).select("li").first();
							if (imgElement != null) {
								String stockCode = imgElement.attr("data-code");
								Log.i(TAG, "j==" + j + ";stockCode==" + stockCode);
								hbean.setStockCode(stockCode);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element imgElement = sElements.get(j).select("div.column2").get(1);
							if (imgElement != null) {
								String close = imgElement.text();
								Log.i(TAG, "j==" + j + ";close==" + close);
								hbean.setClose(close);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Element imgElement = sElements.get(j).select("div.column2").get(2);
							if (imgElement != null) {
								String rate = imgElement.text();
								Log.i(TAG, "j==" + j + ";rate==" + rate);
								hbean.setRate(rate);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						stocklist.add(hbean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stocklist;
	}

	public static List<CommentBean> parseThmemeComment(String href, int pageNo) {
		List<CommentBean> stocklist = new ArrayList<CommentBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
			Elements sElements;
			if (pageNo > 1) {
				doc = Jsoup.parse(href);
				sElements = doc.select("li");
			} else {
				doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
				Element globalnavElement = doc.select("div.comment-list").first();
				sElements = globalnavElement.select("li");
			}
			Log.i(TAG, "url = " + href);
			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				if (sElements != null && sElements.size() > 0) {
					CommentBean hbean;
					for (int j = 0; j < sElements.size(); j++) {
						hbean = new CommentBean();
						try {
							Element imgElement = sElements.get(j).select("img").first();
							if (imgElement != null) {
								String userhead = imgElement.attr("src");
								Log.i(TAG, "j==" + j + ";userhead==" + userhead);
								hbean.setUserhead(userhead);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element imgElement = sElements.get(j).select("img").first();
							if (imgElement != null) {
								String userName = imgElement.attr("alt");
								Log.i(TAG, "j==" + j + ";userName==" + userName);
								hbean.setUserName(userName);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element imgElement = sElements.get(j).select("div.c-content-body").first();
							if (imgElement != null) {
								String content = imgElement.text();
								Log.i(TAG, "j==" + j + ";content==" + content);
								hbean.setContent(content);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element imgElement = sElements.get(j).select("span.c-date").first();
							if (imgElement != null) {
								String dateTimeStr = imgElement.text();
								Log.i(TAG, "j==" + j + ";dateTimeStr==" + dateTimeStr);
								hbean.setDateTimeStr(dateTimeStr);
							}
							Element imgElement2 = sElements.get(j).select("span.c-date").get(1);
							if (imgElement2 != null) {
								String dateTimeStr = imgElement2.text();
								Log.i(TAG, "j==" + j + ";dateTimeStr==" + dateTimeStr);
								hbean.setDateTimeStr(hbean.getDateTimeStr() + " " + dateTimeStr);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						try {
							Element imgElement = sElements.get(j).select("div.comment-refer").first();
							if (imgElement != null) {
								String recentstr = imgElement.text();
								Log.i(TAG, "j==" + j + ";recentstr==" + recentstr);
								hbean.setRecentstr(recentstr);
								hbean.setRecentUserName("");
								hbean.setRecentUserId("1");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (pageNo == 1) {
							/**
							 * <button class="look-good-btn" data-spm="1"> 看好
							 * <span>649</span> </button> <button
							 * class="look-bad-btn" data-spm="2"> 看空
							 * <span>736</span> </button>
							 */
							try {
								Element imgElement = doc.select("button.look-good-btn").first();
								if (imgElement != null) {
									String upNum = imgElement.text().replace("看好 ", "");
									Log.i(TAG, "j==" + j + ";upNum==" + upNum);
									hbean.setUpNum(upNum);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = doc.select("button.look-bad-btn").first();
								if (imgElement != null) {
									String downNum = imgElement.text().replace("看空 ", "");
									Log.i(TAG, "j==" + j + ";downNum==" + downNum);
									hbean.setDownNum(downNum);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						stocklist.add(hbean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stocklist;
	}

	public static FollowJson parseThmemeFollow(String href, int pageNo) {
		FollowJson mFollowJson = new FollowJson();
		try {
		    URL url = new URL(href);
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 设置代理访问
		    connection.setRequestProperty("User-Agent", UrlUtils.userAgent);
		    InputStreamReader read = new InputStreamReader(connection.getInputStream(), "utf-8");// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            StringBuffer buffer = new StringBuffer();
            while ((lineTxt = bufferedReader.readLine()) != null) {//按行读取
                if (!"".equals(lineTxt)) {
                	if(lineTxt.contains("{\\")  && lineTxt.contains("followIndex")){
                		if(lineTxt.contains("__gupiao[\"login\"]")){
                			lineTxt = lineTxt.substring(0,lineTxt.indexOf("__gupiao[\"login\"]"));
                		}else if(lineTxt.contains("[{")){
                			lineTxt = lineTxt.substring(lineTxt.indexOf("[{")+1,lineTxt.length());
                		}
                		buffer.append(lineTxt);
                		System.out.println(lineTxt);
                	}
                }
            }
            read.close();//关闭InputStreamReader
            bufferedReader.close();//关闭BufferedReader
            
            String content = buffer.toString();
            content = content.replace("<script type=\"text/javascript\">__gupiao[\"curve\"] = \"", "")
            		.replace("\";", "");
            content = "{\"list\":"+content.replace("\\\"", "\"").replace("\\&quot;", "\"")+"}";
            Gson gson = new Gson();
            mFollowJson = gson.fromJson(content, FollowJson.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mFollowJson;
	}
	
	
	public static List<NewsBean> parseStockNews(String href, int pageNo) {
		List<NewsBean> list = new ArrayList<NewsBean>();
		try {
			// href = makeURL(href, new HashMap<String, Object>() {
			// {
			// }
			// });
			Document doc;
//			if (pageNo > 1) {
				doc = Jsoup.parse(href);
//			} else {
//				doc = Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
//			}

			Log.i(TAG, "url = " + href);

			// Document doc =
			// Jsoup.connect(href).userAgent(UrlUtils.userAgent).timeout(10000).get();
			// System.out.println(doc.toString());
			try {
				/**
				 */
//				Element globalnavElement = doc.select("ul.qa-list").first();
				Elements moduleElements = doc.select("li.row");
				if (moduleElements != null && moduleElements.size() > 0) {
					for (int i = 0; i < moduleElements.size(); i++) {
						NewsBean sbean = new NewsBean();
						try {
							try {
								Element imgElement = moduleElements.get(i).select("a").first();
								if (imgElement != null) {
									String hrefa = UrlUtils.GUPIAO_BAIDU +imgElement.attr("href");
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
								Element imgElement = moduleElements.get(i).select("p.desc").first();
								if (imgElement != null) {
									String desc = imgElement.text();
									Log.i(TAG, "i==" + i + ";desc==" + desc);
									sbean.setDesc(desc);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							try {
								Element imgElement = moduleElements.get(i).select("ul.left").first();
								if (imgElement != null) {
									String time = imgElement.select("li").first().text();
									Log.i(TAG, "i==" + i + ";time==" + time);
									sbean.setTime(time);
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
