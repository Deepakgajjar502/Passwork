package com.b2b.passwork.Model.SeatList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SeatListResponse{

	@SerializedName("seats")
	private List<SeatsItem> seats;

	@SerializedName("status")
	private int status;

	public void setSeats(List<SeatsItem> seats){
		this.seats = seats;
	}

	public List<SeatsItem> getSeats(){
		return seats;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}