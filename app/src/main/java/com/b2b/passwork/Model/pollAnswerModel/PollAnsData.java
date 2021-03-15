package com.b2b.passwork.Model.pollAnswerModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PollAnsData {

	@SerializedName("totalAnswers")
	private String totalAnswers;

	@SerializedName("options")
	private List<PollAnsOptionsItem> options;

	public void setTotalAnswers(String totalAnswers){
		this.totalAnswers = totalAnswers;
	}

	public String getTotalAnswers(){
		return totalAnswers;
	}

	public void setOptions(List<PollAnsOptionsItem> options){
		this.options = options;
	}

	public List<PollAnsOptionsItem> getOptions(){
		return options;
	}
}