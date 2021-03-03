package com.b2b.passwork.Model;

public class TimeDurationModel {

    String TimeDuration;
    Boolean isSelected = false;
    String type;

    public TimeDurationModel(String guestName, String typea, boolean IsSelected) {
        TimeDuration = guestName;
        isSelected = IsSelected;
        type = typea;

    }

    public TimeDurationModel() {

    }

    public String getTimeDuration() {
        return TimeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        TimeDuration = timeDuration;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
