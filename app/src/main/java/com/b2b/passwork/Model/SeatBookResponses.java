package com.b2b.passwork.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SeatBookResponses{

	@SerializedName("booking_number")
	private List<String> bookingNumber;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}