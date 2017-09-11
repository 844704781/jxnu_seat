package com.watermelon.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.mysql.fabric.xmlrpc.base.Data;
import com.watermelon.pojo.BookError;
import com.watermelon.pojo.BookMessage;
import com.watermelon.pojo.BookSeat;
import com.watermelon.pojo.BookSuccess;
import com.watermelon.pojo.Cancel;
import com.watermelon.pojo.QueryLogs;
import com.watermelon.pojo.User;
import com.watermelon.utils.BookSeatJob;
import com.watermelon.utils.CommonUtils;
import com.watermelon.utils.JnueberryUtils;
import com.watermelon.utils.JsonObject;

@Controller
@RequestMapping("seat")
public class SeatController {

	@Autowired
	private LoginController LoginController;

	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			ModelAndView modelAndView = new ModelAndView("login");
			modelAndView.addObject("message", "No login");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("bookSeat");
		return modelAndView;
	}

	/**
	 * 预定某个教室的座位某某座位
	 * 
	 * @param roomId
	 * @param seatName
	 * @return
	 */
	@RequestMapping(value = "book", method = RequestMethod.POST)
	public @ResponseBody String bookSeat(String roomId, String[] seatName, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			JsonObject jsonObject = new JsonObject("2", "No login");
			return JsonObject.toJson(jsonObject);
		}
		if (CommonUtils.isEmpty(roomId)) {
			JsonObject jsonObject = new JsonObject("0", "room name is null");
			return JsonObject.toJson(jsonObject);
		}
		if (CommonUtils.isEmpty(seatName) || seatName.length == 0) {
			JsonObject jsonObject = new JsonObject("1", "seat number is null");
			return JsonObject.toJson(jsonObject);
		}
		List<String> bookSuccessList = new ArrayList<>();
		List<String> bookErrorList = new ArrayList<>();
		for (int i = 0; i < seatName.length; i++) {
			if (!CommonUtils.isEmpty(seatName[i])) {
				// 有教室，有座位
				BookSeat bookSeat = new BookSeat();
				bookSeat.setSeatShortNo(seatName[i]);// 座位号
				bookSeat.setRoomNo(roomId);
				bookSeat.setDate(CommonUtils.getNextDate(new Date()));
				bookSeat.setTimeSpan(null);
				bookSeat.set__VIEWSTATE(
						"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");
				bookSeat.set__VIEWSTATEGENERATOR("7629D439");
				bookSeat.set__EVENTVALIDATION(
						"/wEWAwL55vP0CgL0ntKNAwLCi9reA0yaRpkdgpS8+9pWCS+9JOUpucy4wjhzcv9qx7/TWLH3");
				bookSeat.setSubCmd("query");
				bookSeat.setSpanSelect("7:00");
				String result = JnueberryUtils.bookSeat(bookSeat);
				if (result.equals("success")) {
					bookSuccessList.add(seatName[i]);
				} else if (result.equals("noLogin")) {
					JsonObject jsonObject = new JsonObject("2", "No login");
					return JsonObject.toJson(jsonObject);
				} else if (result.equals("have")) {
					JsonObject jsonObject = new JsonObject("3", "you have");
					return JsonObject.toJson(jsonObject);
				} else {
					bookErrorList.add(seatName[i]);
				}

			}
		}
		BookSuccess bookSuccess = new BookSuccess();
		bookSuccess.setMessage("预定成功:");
		bookSuccess.setSeats(bookSuccessList);
		BookError bookError = new BookError();
		bookError.setMessage("预定失败");
		bookError.setSeats(bookErrorList);
		BookMessage bookMessage = new BookMessage();
		bookMessage.setRoomId(roomId);
		bookMessage.setBookSuccess(bookSuccess);
		bookMessage.setBookError(bookError);
		return JsonObject.toJson(bookMessage);
	}

	public String bookSeatDemo1(String roomId, String[] seatName, HttpServletRequest request,
			HttpServletResponse response) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			JsonObject jsonObject = new JsonObject("2", "您的登陆session失效了,请重新登录");
			return JsonObject.toJson(jsonObject);
		}
		if (CommonUtils.isEmpty(roomId)) {
			JsonObject jsonObject = new JsonObject("0", "请输入一个自习室号");
			return JsonObject.toJson(jsonObject);
		}
		if (CommonUtils.isEmpty(seatName) || seatName.length == 0) {
			JsonObject jsonObject = new JsonObject("1", "请至少输入一个座位");
			return JsonObject.toJson(jsonObject);
		}
		// List<String> bookSuccessList = new ArrayList<>();
		// List<String> bookErrorList = new ArrayList<>();
		int j = 0;// 记录被预约的位置个数
		for (int i = 0; i < seatName.length; i++) {
			if (!CommonUtils.isEmpty(seatName[i])) {
				// 有教室，有座位
				BookSeat bookSeat = new BookSeat();
				LoginController.login(user, request, response);
				bookSeat.setSeatShortNo(seatName[i]);// 座位号
				bookSeat.setRoomNo(roomId);
				bookSeat.setDate(CommonUtils.getNextDate(new Date()));
				bookSeat.setTimeSpan(null);
				bookSeat.set__VIEWSTATE(
						"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");
				bookSeat.set__VIEWSTATEGENERATOR("7629D439");
				bookSeat.set__EVENTVALIDATION(
						"/wEWAwL55vP0CgL0ntKNAwLCi9reA0yaRpkdgpS8+9pWCS+9JOUpucy4wjhzcv9qx7/TWLH3");
				bookSeat.setSubCmd("query");
				bookSeat.setSpanSelect("7:00");
				String result = JnueberryUtils.bookSeat(bookSeat);
				if (result.equals("success")) {
					// bookSuccessList.add(seatName[i]);
				} else if (result.equals("")) {
					return "noLogin";
				} else if (result.equals("have")) {
					return "have";
				} else if (result.equals("isBeAppointment")) {
					j++;
				} else {
					// bookErrorList.add(seatName[i]);
				}

			}
		}
		return "success";
	}

	public List<Map<String, String>> bookSeatDemo(String roomId, String[] seatName, HttpServletRequest request,
			HttpServletResponse response) {
		// Map<String, String> map = new HashMap<>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			Map<String, String> map = new HashMap<>();
			map.put("2", "您的session失效了,请重新登录");
			list.add(map);
			return list;
		}
		if (CommonUtils.isEmpty(roomId)) {
			Map<String, String> map = new HashMap<>();
			map.put("0", "教室号未填");
			list.add(map);
			return list;
		}
		if (CommonUtils.isEmpty(seatName) || seatName.length == 0) {
			Map<String, String> map = new HashMap<>();
			map.put("1", "请至少填入一个座位号");
			list.add(map);
			return list;
		}

		for (int i = 0; i < seatName.length; i++) {
			if (!CommonUtils.isEmpty(seatName[i])) {
				// 有教室，有座位
				BookSeat bookSeat = new BookSeat();
				bookSeat.setSeatShortNo(seatName[i]);// 座位号
				bookSeat.setRoomNo(roomId);
				bookSeat.setDate(CommonUtils.getNextDate(new Date()));
				bookSeat.setTimeSpan(null);
				bookSeat.set__VIEWSTATE(
						"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");
				bookSeat.set__VIEWSTATEGENERATOR("7629D439");
				bookSeat.set__EVENTVALIDATION(
						"/wEWAwL55vP0CgL0ntKNAwLCi9reA0yaRpkdgpS8+9pWCS+9JOUpucy4wjhzcv9qx7/TWLH3");
				bookSeat.setSubCmd("query");
				bookSeat.setSpanSelect("7:00");
				String result = JnueberryUtils.bookSeat(bookSeat);// 预订座位
				Map<String, String> map = new HashMap<>();
				if (result.equals("success")) {
					map.put("5", "恭喜您,成功抢得"+seatName[i]);
				} else if (result.equals("noLogin")) {
					map.put("7", "远程登录失效,请尝试注销账号后重新登录");
				} else if (result.equals("have")) {
					map.put("3", "您已经抢到了一个座位,莫贪 = =");
				} else if (result.equals("isBeAppointment")) {
					map.put("4", seatName[i]+"已经被人抢跑了");
				} else {
					map.put("6", result);
				}
				list.add(map);
			}
		}
		return list;// 返回抢位置的结果集
	}

	@RequestMapping(value = "setTimeBook", method = RequestMethod.POST)
	public @ResponseBody String setTime(String roomId, String[] seatName, HttpServletRequest request,
			Date beginDateTime, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			JsonObject jsonObject = new JsonObject("2", "No login");
			return JsonObject.toJson(jsonObject);
		}
		Date date = beginDateTime;// 获取用户输入的开始时间
		JsonObject jsonObject = null;
		System.out.println("开抢时间:" + CommonUtils.formatDateToString(date));
		List<Map<String, String>> list = null;

		while (true) {
			Calendar cal = Calendar.getInstance();
			Date date1 = cal.getTime();// 获取当前时间
			if (date.before(date1)) {

				list = bookSeatDemo(roomId, seatName, request, response);// 获取一轮抢位置信息集合

				String result0 = null;// 教室为null
				String result1 = null;// 座位为null
				String result2 = null;// 未登陆
				String result3 = null;// 已经有座位了
				String result4 = null;// 位置已经被人抢了
				String result5 = null;// 抢位置成功
				String result6 = null;// 未知情况
				String result7 = null;// 远程登录失效

				for (int i = 0; i < list.size(); i++)// 遍历查询的所有座位的结果
				{
					Map<String, String> map = list.get(i);
					result0 = map.get("0");// 教室为null
					result1 = map.get("1");// 座位为null
					result2 = map.get("2");// 未登陆
					result3 = map.get("3");// 已经有座位了
					result4 = map.get("4");// 位置已经被人抢了
					result5 = map.get("5");// 抢位置成功
					result6 = map.get("6");// 未知情况
					result7 = map.get("7");// 远程登录失效
					if (result0 != null)// 如果查询的结果中有
					{
						jsonObject = new JsonObject("0", result0);
						return JsonObject.toJson(jsonObject);
					}
					if (result1 != null)// 如果查询的结果中有
					{
						jsonObject = new JsonObject("1", result1);
						return JsonObject.toJson(jsonObject);
					}
					if (result2 != null)// 如果查询的结果中有
					{
						jsonObject = new JsonObject("2", result2);
						return JsonObject.toJson(jsonObject);
					}

					if (result3 != null)// 如果查询的结果中有
					{
						jsonObject = new JsonObject("3", result3);
						return JsonObject.toJson(jsonObject);
					}
					if (i == list.size() - 1&&result4 != null)// 如果查询的结果中有
					{
						jsonObject = new JsonObject("4", "您预约的所有位置都被人抢走了");
						return JsonObject.toJson(jsonObject);
					}
					if (result5 != null)//
					{
						jsonObject = new JsonObject("5", result5);
						return JsonObject.toJson(jsonObject);
					}
					if (i == list.size() - 1 && result6 != null)// 如果最后一个出现了未知情况,将未知情况告诉用户,如果之前出现,则不理.
					{

						jsonObject = new JsonObject("6", result6);
						return JsonObject.toJson(jsonObject);
					}
					if (result7 != null)// 远程登录失效,跳出从结果集中查询,远程登录后再次查询结果
					{
						break;
					}

				}
				if(result7!=null)// 远程登录失效,从session中获取账号密码,重新远程登录,\
				{
					LoginController.login(user, request, response);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@RequestMapping("query")
	public ModelAndView queryList(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			ModelAndView modelAndView = new ModelAndView("login");
			modelAndView.addObject("message", "No login");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("query");
		return modelAndView;
	}

	@RequestMapping(value = "query", method = RequestMethod.POST)
	public @ResponseBody String query(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			JsonObject jsonObject = new JsonObject("2", "No login");
			return JsonObject.toJson(jsonObject);
		}
		QueryLogs queryLogs = new QueryLogs();
		queryLogs.set__VIEWSTATE(
				"/wEPDwUKMTY3NjM4MDk3NA9kFgICAw9kFgQCBg8QZA8WBQIBAgICAwIEAgUWBRAFEeiHquS5oOWupDEwMSjljZcpBQYxMDEwMDFnEAUR6Ieq5Lmg5a6kMjAxKOWNlykFBjEwMTAwMmcQBRHoh6rkuaDlrqQyMDIo5YyXKQUGMTAxMDAzZxAFEeiHquS5oOWupDMwMSjljZcpBQYxMDEwMDRnEAUR6Ieq5Lmg5a6kMzAyKOWMlykFBjEwMTAwNWdkZAIHDxYCHgdWaXNpYmxlaGRkuNNJ88meOHQR4U6H0pQ0xWrJGebU5BBBHiKMNXjnVaI=");
		queryLogs.set__VIEWSTATEGENERATOR("47429C9F");
		queryLogs.set__EVENTVALIDATION(
				"/wEWBQLgoa2UCALgu8z3BgKk%2B56eBwLMgJnOCQLytq6nD0azTZjETU9Qq%2BN3rZIc46kabQ/aa7nEe0FXajQMeYny");
		queryLogs.setSubCmd("");
		queryLogs.setSubBookNo("");
		queryLogs.setChooseDate("选择日期");
		queryLogs.setDdlDate("7");
		queryLogs.setDdlRoom("-1");

		String result = JnueberryUtils.queryLogs(queryLogs);
		if (result.equals("no")) {
			JsonObject jsonObject = new JsonObject("1", "no");
			return JsonObject.toJson(jsonObject);
		} else if (result.equals("noLogin")) {
			JsonObject jsonObject = new JsonObject("2", "No login");
			return JsonObject.toJson(jsonObject);
		} else {
			JsonObject jsonObject = new JsonObject("3", result);
			return JsonObject.toJson(jsonObject);
		}

	}

	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public @ResponseBody String cancel(String id, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			JsonObject jsonObject = new JsonObject("2", "No login");
			return JsonObject.toJson(jsonObject);
		}
		Cancel cancel = new Cancel();
		cancel.set__VIEWSTATE(
				"/wEPDwUKMTY3NjM4MDk3NA9kFgICAw9kFgoCAg8WAh4FY2xhc3MFDXVpLWJ0bi1hY3RpdmVkAgMPFgIfAGVkAgQPFgIfAGVkAgYPEGQPFgUCAQICAgMCBAIFFgUQBRHoh6rkuaDlrqQxMDEo5Y2XKQUGMTAxMDAxZxAFEeiHquS5oOWupDIwMSjljZcpBQYxMDEwMDJnEAUR6Ieq5Lmg5a6kMjAyKOWMlykFBjEwMTAwM2cQBRHoh6rkuaDlrqQzMDEo5Y2XKQUGMTAxMDA0ZxAFEeiHquS5oOWupDMwMijljJcpBQYxMDEwMDVnZGQCBw8WAh4HVmlzaWJsZWhkZGD9eBr/IadjWW0IL4y1WR4FBrSGh5D1gj6lVVxCVu3S");
		cancel.set__VIEWSTATEGENERATOR("47429C9F");
		cancel.set__EVENTVALIDATION(
				"/wEWBQLR1aHCDQLgu8z3BgKk+56eBwLMgJnOCQLytq6nD9ftacDfHnbBjjMcjqehy+7ugquD4S9TTZp4BnSM6jML");
		cancel.setSubCmd("cancel");
		cancel.setSubBookNo(id);
		cancel.setChooseDate("选择日期");
		cancel.setDdlDate("7");
		cancel.setDdlRoom("-1");

		boolean result = JnueberryUtils.cancelBook(cancel);
		if (result == true) {
			JsonObject jsonObject = new JsonObject("3", "取消成功");
			return JsonObject.toJson(jsonObject);
		} else {
			JsonObject jsonObject = new JsonObject("4", "取消失败");
			return JsonObject.toJson(jsonObject);
		}

	}

	/**
	 * 自动将此Controller类yyyy-MM-dd HH:mm:ss类型字符串转换成Date对象
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
