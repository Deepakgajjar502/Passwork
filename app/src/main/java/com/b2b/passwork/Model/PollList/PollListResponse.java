package com.b2b.passwork.Model.PollList;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PollListResponse{

	@SerializedName("data")
	private ArrayList<pollDataItem> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(ArrayList<pollDataItem> data){
		this.data = data;
	}

	public ArrayList<pollDataItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}
}