package com.b2b.passwork.Model;

import java.util.Date;

public class EventDetailModel {

    String EventTitle;

    Date EventDate;
    String EventType;


    public EventDetailModel(String eventTitle, Date eventDate, String eventType) {
        EventTitle = eventTitle;

        EventDate = eventDate;
        EventType = eventType;
    }

    public EventDetailModel() {

    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }



    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public Date getEventDate() {
        return EventDate;
    }

    public void setEventDate(Date eventDate) {
        EventDate = eventDate;
    }
}
