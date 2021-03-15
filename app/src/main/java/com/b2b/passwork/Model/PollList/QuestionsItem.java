package com.b2b.passwork.Model.PollList;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuestionsItem{

	@SerializedName("question_text")
	private String questionText;

	@SerializedName("total_of_answers")
	private String totalOfAnswers;

	@SerializedName("question_type")
	private boolean questionType;

	@SerializedName("options")
	private List<OptionsItem> options;

	@SerializedName("question_id")
	private String questionId;

	public void setQuestionText(String questionText){
		this.questionText = questionText;
	}

	public String getQuestionText(){
		return questionText;
	}

	public void setTotalOfAnswers(String totalOfAnswers){
		this.totalOfAnswers = totalOfAnswers;
	}

	public String getTotalOfAnswers(){
		return totalOfAnswers;
	}

	public void setQuestionType(boolean questionType){
		this.questionType = questionType;
	}

	public boolean isQuestionType(){
		return questionType;
	}

	public void setOptions(List<OptionsItem> options){
		this.options = options;
	}

	public List<OptionsItem> getOptions(){
		return options;
	}

	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return questionId;
	}
}