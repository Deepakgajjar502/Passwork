package com.b2b.passwork.Model.PollList;

import com.google.gson.annotations.SerializedName;

public class OptionsItem{

	@SerializedName("answer_text")
	private String answerText;

	@SerializedName("offered_answer_id")
	private String offeredAnswerId;

	@SerializedName("answer_by_user")
	private boolean answerByUser;

	@SerializedName("count")
	private String count;

	boolean selectedAns = false;

	public void setAnswerText(String answerText){
		this.answerText = answerText;
	}

	public String getAnswerText(){
		return answerText;
	}

	public void setOfferedAnswerId(String offeredAnswerId){
		this.offeredAnswerId = offeredAnswerId;
	}

	public String getOfferedAnswerId(){
		return offeredAnswerId;
	}

	public void setAnswerByUser(boolean answerByUser){
		this.answerByUser = answerByUser;
	}

	public boolean isAnswerByUser(){
		return answerByUser;
	}

	public void setCount(String count){
		this.count = count;
	}

	public String getCount(){
		return count;
	}
}