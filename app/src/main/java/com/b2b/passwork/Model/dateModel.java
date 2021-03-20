package com.b2b.passwork.Model;

public class dateModel {

    Boolean isSelected = false;
    String date;

    public dateModel(Boolean isSelected, String date) {
        this.isSelected = isSelected;
        this.date = date;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
