package com.b2b.passwork.Model;

public class AddGuestModel {

    String GuestName;
    String GuestEmailId;
    String GuestMobile;

    public AddGuestModel(String guestName, String guestEmailId, String guestMobile) {
        GuestName = guestName;
        GuestEmailId = guestEmailId;
        GuestMobile = guestMobile;
    }

    public AddGuestModel() {

    }


    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String guestName) {
        GuestName = guestName;
    }

    public String getGuestEmailId() {
        return GuestEmailId;
    }

    public void setGuestEmailId(String guestEmailId) {
        GuestEmailId = guestEmailId;
    }

    public String getGuestMobile() {
        return GuestMobile;
    }

    public void setGuestMobile(String guestMobile) {
        GuestMobile = guestMobile;
    }
}
