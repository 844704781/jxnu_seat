package com.watermelon.utils;

import java.util.List;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import com.watermelon.pojo.BookMessage;
import com.watermelon.pojo.BookSeat;
import com.watermelon.pojo.BookSuccess;
import com.watermelon.web.SeatController;

public class BookSeatJob extends TimerTask {

	private String roomId;
	private String[] seatName;
	private HttpServletRequest request;
	private Object object;

	private String result;

	/**
	 * 用于回调获取result
	 * 
	 * @return
	 */
	public String getResult() {
		return this.result;
	}

	public BookSeatJob(String roomId, String[] seatName, HttpServletRequest request,Object object) {
		super();
		this.roomId = roomId;
		this.seatName = seatName;
		this.request = request;
		this.object = object;
	}

	// {"status":"ok","value":{"roomId":"101001","bookSuccess":{"message":"预定成功:","seats":["N12"]},"bookError":{"message":"预定失败","seats":[]}}}

	@Override
	public void run() {
		SeatController seatController = new SeatController();
		result = seatController.bookSeat(roomId, seatName, request);
		System.out.println(result);
		if(result.equals("{\"status\":\"2\",\"value\":\"No login\"}")||result.equals("{\"status\":\"3\",\"value\":\"you have\"}")){
			cancel();
			//thread.notifyAll();
			object.notifyAll();
			return ;
		};
		BookMessage bookMessage = CommonUtils.jsonToObject(result);
		BookSuccess bookSuccess = bookMessage.getBookSuccess();
		if (bookSuccess != null) {
			List<String> seats = bookSuccess.getSeats();
			if (seats != null) {
				cancel();
				object.notifyAll();
				return ;
			}
		}

	}

}
