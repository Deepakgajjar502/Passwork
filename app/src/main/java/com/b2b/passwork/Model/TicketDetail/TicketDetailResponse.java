package com.b2b.passwork.Model.TicketDetail;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TicketDetailResponse{

	@SerializedName("data")
	private ArrayList<TicketDetailDataItem> data;

	@SerializedName("status")
	private int status;

	public void setData(ArrayList<TicketDetailDataItem> data){
		this.data = data;
	}

	public ArrayList<TicketDetailDataItem> getData(){
		return data;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}