package com.watermelon.pojo;


public class BookMessage {
	private String roomId;
	private BookSuccess bookSuccess;
	private BookError bookError;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public BookSuccess getBookSuccess() {
		return bookSuccess;
	}

	public void setBookSuccess(BookSuccess bookSuccess) {
		this.bookSuccess = bookSuccess;
	}

	public BookError getBookError() {
		return bookError;
	}

	public void setBookError(BookError bookError) {
		this.bookError = bookError;
	}
}
