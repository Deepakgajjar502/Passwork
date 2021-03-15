package com.b2b.passwork.Model.pollAnswerModel;

import com.google.gson.annotations.SerializedName;

public class PollAnsOptionsItem {

	@SerializedName("offered_answer_id")
	private String offeredAnswerId;

	@SerializedName("option_total_count")
	private String optionTotalCount;

	public void setOfferedAnswerId(String offeredAnswerId){
		this.offeredAnswerId = offeredAnswerId;
	}

	public String getOfferedAnswerId(){
		return offeredAnswerId;
	}

	public void setOptionTotalCount(String optionTotalCount){
		this.optionTotalCount = optionTotalCount;
	}

	public String getOptionTotalCount(){
		return optionTotalCount;
	}
}