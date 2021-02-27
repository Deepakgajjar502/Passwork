package com.b2b.passwork.Model.Room;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetRoomResponse{

	@SerializedName("bays")
	private List<BaysItem> bays;

	@SerializedName("status")
	private int status;

	public void setBays(List<BaysItem> bays){
		this.bays = bays;
	}

	public List<BaysItem> getBays(){
		return bays;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}