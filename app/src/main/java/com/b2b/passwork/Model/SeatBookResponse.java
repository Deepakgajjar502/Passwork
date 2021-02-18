package com.b2b.passwork.Model;

import com.google.gson.annotations.SerializedName;

public class SeatBookResponse{

	@SerializedName("booking_number")
	private String bookingNumber;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setBookingNumber(String bookingNumber){
		this.bookingNumber = bookingNumber;
	}

	public String getBookingNumber(){
		return bookingNumber;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}