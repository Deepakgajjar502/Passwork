package com.b2b.passwork.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeatBookResponse{

	@SerializedName("booking_number")
	private List<String> bookingNumber;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setBookingNumber(List<String> bookingNumber){
		this.bookingNumber = bookingNumber;
	}

	public List<String> getBookingNumber(){
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