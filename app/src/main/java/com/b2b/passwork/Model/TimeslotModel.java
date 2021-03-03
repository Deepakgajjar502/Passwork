package com.b2b.passwork.Model;

public class TimeslotModel {

    String TimeSlot;
    Boolean isSelected = false;
    String AM_PM;

    public TimeslotModel(String guestName, String ampm, boolean IsSelected) {
        TimeSlot = guestName;
        isSelected = IsSelected;
        AM_PM = ampm;

    }

    public TimeslotModel() {

    }

    public String getTimeSlot() {
        return TimeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        TimeSlot = timeSlot;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getAM_PM() {
        return AM_PM;
    }

    public void setAM_PM(String AM_PM) {
        this.AM_PM = AM_PM;
    }
}
