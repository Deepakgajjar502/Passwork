package com.b2b.passwork.Model.Upcoming;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UpComingResponse {

	@SerializedName("data")
	private List<upcomingDataItem> data;

	@SerializedName("status")
	private int status;

	public void setData(List<upcomingDataItem> data){
		this.data = data;
	}

	public List<upcomingDataItem> getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}