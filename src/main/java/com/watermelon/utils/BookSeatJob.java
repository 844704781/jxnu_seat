package com.watermelon.utils;

import java.util.List;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import com.watermelon.pojo.BookMessage;
import com.watermelon.web.SeatController;

public class BookSeatJob  extends TimerTask{

	private String roomId;
	private String[] seatName;
	private HttpServletRequest request;
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minute;
	private String second;
	
	private String result;
	/**
	 * 用于回调获取result
	 * @return
	 */
	public String getResult()
	{
		return this.result;
	}
	




	public BookSeatJob(String roomId, String[]seatName, HttpServletRequest request, String year, String month,
			String day, String hour, String minute, String second) {
		super();
		this.roomId = roomId;
		this.seatName = seatName;
		this.request = request;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}


	//{"status":"ok","value":{"roomId":"101001","bookSuccess":{"message":"预定成功:","seats":["N12"]},"bookError":{"message":"预定失败","seats":[]}}}




	@Override
	public void run() {
		SeatController seatController=new SeatController();
		result=seatController.bookSeat(roomId, seatName, request, year, month, day, hour, minute, second);
		JsonObject jsonObject=CommonUtils.jsonToObject(result);
		BookMessage bookMessage=(BookMessage) jsonObject.getValue();
		List<String> seats=bookMessage.getBookSuccess().getSeats();
				if(seats!=null||seats.size()>0)
				{
					cancel();
				}
	}

}
