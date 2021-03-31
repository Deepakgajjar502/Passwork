package com.b2b.passwork.Model.Room.GetBay;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetBayResponse{

	@SerializedName("bays")
	private List<BaysItems> bays;

	@SerializedName("status")
	private int status;

	public void setBays(List<BaysItems> bays){
		this.bays = bays;
	}

	public List<BaysItems> getBays(){
		return bays;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}