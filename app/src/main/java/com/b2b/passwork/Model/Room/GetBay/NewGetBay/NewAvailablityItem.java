package com.b2b.passwork.Model.Room.GetBay.NewGetBay;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewAvailablityItem  implements Serializable {

	@SerializedName("date")
	private String date;

	@SerializedName("total")
	private int total;

	@SerializedName("booked")
	private int booked;

	@SerializedName("available")
	private int available;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setBooked(int booked){
		this.booked = booked;
	}

	public int getBooked(){
		return booked;
	}

	public void setAvailable(int available){
		this.available = available;
	}

	public int getAvailable(){
		return available;
	}
}