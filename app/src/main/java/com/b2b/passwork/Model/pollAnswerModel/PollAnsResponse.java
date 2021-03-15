package com.b2b.passwork.Model.pollAnswerModel;

import com.google.gson.annotations.SerializedName;

public class PollAnsResponse{

	@SerializedName("data")
	private PollAnsData data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setData(PollAnsData data){
		this.data = data;
	}

	public PollAnsData getData(){
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