package com.b2b.passwork.Model.FloorList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FloorListResponse{

	@SerializedName("floors")
	private List<FloorsItem> floors;

	@SerializedName("status")
	private int status;

	public void setFloors(List<FloorsItem> floors){
		this.floors = floors;
	}

	public List<FloorsItem> getFloors(){
		return floors;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}