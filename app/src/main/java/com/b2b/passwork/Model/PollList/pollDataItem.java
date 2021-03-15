package com.b2b.passwork.Model.PollList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class pollDataItem {

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("poll_description")
	private String pollDescription;

	@SerializedName("poll_id")
	private String pollId;

	@SerializedName("is_expired")
	private boolean isExpired;

	@SerializedName("questions")
	private List<QuestionsItem> questions;

	@SerializedName("poll_type")
	private boolean pollType;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("status")
	private boolean status;

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setPollDescription(String pollDescription){
		this.pollDescription = pollDescription;
	}

	public String getPollDescription(){
		return pollDescription;
	}

	public void setPollId(String pollId){
		this.pollId = pollId;
	}

	public String getPollId(){
		return pollId;
	}

	public void setIsExpired(boolean isExpired){
		this.isExpired = isExpired;
	}

	public boolean isIsExpired(){
		return isExpired;
	}

	public void setQuestions(List<QuestionsItem> questions){
		this.questions = questions;
	}

	public List<QuestionsItem> getQuestions(){
		return questions;
	}

	public void setPollType(boolean pollType){
		this.pollType = pollType;
	}

	public boolean isPollType(){
		return pollType;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}