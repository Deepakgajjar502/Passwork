package com.b2b.passwork.Model.SeatList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AvailablityItem{

	@SerializedName("date")
	private String date;

	@SerializedName("seat_id")
	private int seatId;

	@SerializedName("profile")
	private List<ProfileItem> profile;

	@SerializedName("status")
	private boolean status;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setSeatId(int seatId){
		this.seatId = seatId;
	}

	public int getSeatId(){
		return seatId;
	}

	public void setProfile(List<ProfileItem> profile){
		this.profile = profile;
	}

	public List<ProfileItem> getProfile(){
		return profile;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}