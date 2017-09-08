package com.watermelon.utils;

import com.watermelon.pojo.BookSeat;
import com.watermelon.pojo.Cancel;
import com.watermelon.pojo.Login;
import com.watermelon.pojo.QueryLogs;
import com.watermelon.pojo.Seat;
import com.watermelon.pojo.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JnueberryUtils {
	private static String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
	private static String AcceptEncoding = "gzip, deflate";
	private static String AcceptLanguage = "zh-CN,zh;q=0.8";
	private static String Connection = "keep-alive";
	private static String Host = "zwfp.jxnu.jadl.net";
	private static String UpgradeInsecureRequests = "1";
	private static String UserAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36";
	private static CookieStore cookieStore = new BasicCookieStore();
	private static CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
	private static HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");
	private static RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

	public static boolean login(User user, Login login) {
		String url = "http://zwfp.jxnu.jadl.net/Login.aspx";
		Map<String, String> params = CommonUtils.beanToMap(login);
		params.put("txt_LoginID", user.getTxt_LoginID());
		params.put("txt_Password", user.getTxt_Password());
		String loginResult = sendPost(params, url);
		String h1;
		try {

			Document document = Jsoup.parse(loginResult);
			Elements elements = document.getElementsByTag("h2");
			h1 = ((Element) elements.get(0)).html();
		} catch (Exception e) {
			System.out.println("-----------------------------------");
			System.out.println(user.getTxt_LoginID() + "已经修改密码");
			System.out.println("登录失败");
			System.out.println(loginResult);
			System.out.println("-----------------------------------");
			return false;
		}
		String main = "Object moved to <a href=\"/MainFunctionPage.aspx\">here</a>.";
		if (!main.equals(h1)) {
			System.out.println("-----------------------------------");
			System.out.println("登录失败");
			System.out.println(loginResult);
			System.out.println("-----------------------------------");
			return false;
		}
		System.out.println("登录成功");
		return true;
	}

	public static String bookSeat(BookSeat bookSeat) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://zwfp.jxnu.jadl.net/BookSeat/BookSeatMessage.aspx?");
		sb.append("seatNo=");
		sb.append(bookSeat.getSeatNo());
		sb.append("&seatShortNo=");
		sb.append(bookSeat.getSeatShortNo());
		sb.append("&roomNo=");
		sb.append(bookSeat.getRoomNo());
		sb.append("&date=");
		sb.append(bookSeat.getDate());
		sb.append("&timeSpan=");

		String URL = sb.toString();

		Map<String, String> params = new HashMap<String, String>();
		params.put("__VIEWSTATE", bookSeat.get__VIEWSTATE());
		params.put("__VIEWSTATEGENERATOR", bookSeat.get__VIEWSTATEGENERATOR());
		params.put("__EVENTVALIDATION", bookSeat.get__EVENTVALIDATION());
		params.put("subCmd", bookSeat.getSubCmd());
		params.put("spanSelect", bookSeat.getSpanSelect());
		String result = sendPost(params, URL);
		Document document = Jsoup.parse(result);
		Element ele = document.getElementById("MessageTip");
		if (ele!=null&&"座位预约成功，请在6:00至9:00到图书馆刷卡确认".equals(ele.text())) {
			System.out.println("-----------------------------------");
			System.out.println("恭喜你，座位预定成功。");
			
			System.out.println("-----------------------------------");
			return "success";
		}
		if(ele!=null&&"对不起，当前日期您已有等待签到的座位。".equals(ele.text()))
		{
			System.out.println("预定失败");
			System.out.println(result);
			return "have";
		}
		Elements eles=document.getElementsByTag("title");
		String title=eles.get(0).text();
		if("Object moved".equals(title))
		{
			System.out.println("-----------------------------------");
			System.out.println("预定失败");
			System.out.println(result);
			System.out.println("-----------------------------------");
			return "noLogin";
		}
		
		return "error";
	}

	public static String[] getSeat(Seat seat) {
		String URL = "http://zwfp.jxnu.jadl.net/BookSeat/BookSeatListForm.aspx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("__VIEWSTATE", seat.get__VIEWSTATE());
		params.put("__VIEWSTATEGENERATOR", seat.get__VIEWSTATEGENERATOR());
		params.put("__EVENTVALIDATION", seat.get__EVENTVALIDATION());
		params.put("hidBookDate", seat.getHidBookDate());
		params.put("hidRrId", seat.getHidRrId());
		params.put("subCmd", seat.getSubCmd());
		params.put("txtBookDate", seat.getTxtBookDate());
		params.put("selReadingRoom", seat.getSelReadingRoom());
		String result = sendPost(params, URL);
		Document document = Jsoup.parse(result);
		Elements elements = document.getElementsByTag("span");
		if (elements.size() >= 1) {
			System.out.println("-----------------------------------");
			System.out.println(seat.getRoomName() + ":无位置");
			System.out.println("-----------------------------------");
			return null;
		}
		System.out.println("有位置");

		String[] seatsArray = CommonUtils.getSeat(result);
		return seatsArray;
	}

	public static String getRandomSeat(Seat seat) {
		String[] seats = getSeat(seat);
		if (seats == null) {
			return null;
		}
		return CommonUtils.getRandomSeat(seats);
	}

	public static boolean cancelBook(Cancel cancel) {
		String URL = "http://zwfp.jxnu.jadl.net/UserInfos/QueryLogs.aspx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("__VIEWSTATE", cancel.get__VIEWSTATE());
		params.put("__VIEWSTATEGENERATOR", cancel.get__VIEWSTATEGENERATOR());
		params.put("__EVENTVALIDATION", cancel.get__EVENTVALIDATION());
		params.put("subCmd", cancel.getSubCmd());
		params.put("subBookNo", cancel.getSubBookNo());
		params.put("chooseDate", cancel.getChooseDate());
		params.put("ddlDate", cancel.getDdlDate());
		params.put("ddlRoom", cancel.getDdlRoom());
		String result = sendPost(params, URL);

		Document document = Jsoup.parse(result);
		Elements elements = document.getElementsByTag("script");
		if (elements == null) {
			System.out.println("-----------------------------------");
			System.out.println("取消失败");
			System.out.println(result);
			System.out.println("-----------------------------------");
			return false;
		}
		try {
			((Element) elements.get(1)).html();
		} catch (Exception e) {
			System.out.println("-----------------------------------");
			System.out.println("取消失败");
			System.out.println(result);
			System.out.println("-----------------------------------");
			return false;
		}
		return true;
	}

	public static String queryLogs(QueryLogs queryLogs) {
		String URL = "http://zwfp.jxnu.jadl.net/UserInfos/QueryLogs.aspx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("__VIEWSTATE", queryLogs.get__VIEWSTATE());
		params.put("__VIEWSTATEGENERATOR", queryLogs.get__VIEWSTATEGENERATOR());
		params.put("__EVENTVALIDATION", queryLogs.get__EVENTVALIDATION());
		params.put("subCmd", queryLogs.getSubCmd());
		params.put("subBookNo", queryLogs.getSubBookNo());
		params.put("chooseDate", queryLogs.getChooseDate());
		params.put("ddlDate", queryLogs.getDdlDate());
		params.put("ddlRoom", queryLogs.getDdlRoom());
		String result = sendPost(params, URL);
		Document document = Jsoup.parse(result);
		Elements eles=document.getElementsByTag("h2");
		if(eles!=null&&eles.size()>0&&"Object moved to <a href=\"/Login.aspx\">here</a>.".equals(eles.get(0).html()))
		{
			return "noLogin";
		}
		String li = null;
		try {
			
			Elements elements = document.getElementsByTag("ul");
			if (elements == null) {
				return null;
			}
			li = ((Element) elements.get(1)).html();
		} catch (Exception e) {
			System.out.println("-----------------------------------");
			System.out.println("无预约记录");
			System.out.println(result);
			System.out.println("-----------------------------------");
			return "no";
		}
		
		return li;
	}

	public static String sendPost(Map<String, String> params, String url) {
		return sendPost(params, url, null);
	}

	public static String sendPost(Map<String, String> params, String url, Map<String, String> headers) {
		CloseableHttpResponse httpResponse = null;
		HttpPost httpPost = new HttpPost(url);
		//httpPost.setConfig(config);

		httpPost.addHeader("Accept", Accept);
		httpPost.addHeader("Accept-Encoding", AcceptEncoding);
		httpPost.addHeader("Accept-Language", AcceptLanguage);
		httpPost.addHeader("Connection", Connection);
		httpPost.addHeader("Host", Host);
		httpPost.addHeader("Upgrade-Insecure-Requests", UpgradeInsecureRequests);
		httpPost.addHeader("User-Agent", UserAgent);
		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpPost.addHeader((String) entry.getKey(), (String) entry.getValue());
			}
		}
		httpPost = setQueryParams(params, httpPost);
		try {
			httpResponse = httpClient.execute(httpPost);
			return EntityUtils.toString(httpResponse.getEntity());
		} catch (ClientProtocolException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpPost setQueryParams(Map<String, String> params, HttpPost post) {
		List<NameValuePair> params1 = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			String name = (String) entry.getKey();
			String value = (String) entry.getValue();
			params1.add(new BasicNameValuePair(name, value));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(params1, "utf-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return post;
	}
	
}