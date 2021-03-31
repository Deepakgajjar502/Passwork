package com.b2b.passwork.Model.Room.GetBay.NewGetBay;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NewGetBayResponse{

	@SerializedName("bays")
	private List<NewBaysItem> bays;

	@SerializedName("status")
	private int status;

	public void setBays(List<NewBaysItem> bays){
		this.bays = bays;
	}

	public List<NewBaysItem> getBays(){
		return bays;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}