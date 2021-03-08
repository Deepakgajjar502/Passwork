package com.b2b.passwork.Model;

import java.util.ArrayList;

public class PollModel {

    String Ques;
    Boolean Anonymous ;
    Boolean MultiSelect;
    String totalParticipaent;
    String endDate;



    public PollModel(String ques, Boolean anonymous, Boolean multiSelect, String totalParticipaent, String endDate) {
        Ques = ques;
        Anonymous = anonymous;
        MultiSelect = multiSelect;
        this.totalParticipaent = totalParticipaent;
        this.endDate = endDate;
    }

    public String getTotalParticipaent() {
        return totalParticipaent;
    }

    public void setTotalParticipaent(String totalParticipaent) {
        this.totalParticipaent = totalParticipaent;
    }

    public String getQues() {
        return Ques;
    }

    public void setQues(String ques) {
        Ques = ques;
    }

    public Boolean getAnonymous() {
        return Anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        Anonymous = anonymous;
    }

    public Boolean getMultiSelect() {
        return MultiSelect;
    }

    public void setMultiSelect(Boolean multiSelect) {
        MultiSelect = multiSelect;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
/* public ArrayList<AnswerModel> getListofAnswer() {
        return ListofAnswer;
    }

    public void setListofAnswer(ArrayList<AnswerModel> listofAnswer) {
        ListofAnswer = listofAnswer;
    }*/
}
