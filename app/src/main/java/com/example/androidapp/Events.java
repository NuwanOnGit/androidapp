package com.example.androidapp;

public class Events {
    private String eventTitle;
    private String eventType;
    private String eventDate;
    private String eventDescription;
    private String eventExpertise;
    private String eventTalentCount;
    private String eventLocation;
    private String eventStatus;
    private String eventUid;

    private String eventJoinedUid;

    public Events(){

    }

    public Events(String eventTitle, String eventType, String eventDate, String eventStatus, String eventDescription, String eventExpertise, String eventTalentCount, String eventLocation, String eventUid, String eventJoinedUid) {
        this.eventTitle = eventTitle;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.eventExpertise = eventExpertise;
        this.eventTalentCount = eventTalentCount;
        this.eventLocation = eventLocation;
        this.eventStatus = eventStatus;
        this.eventUid = eventUid;
        this.eventJoinedUid = eventJoinedUid;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventExpertise() {
        return eventExpertise;
    }

    public void setEventExpertise(String eventExpertise) {
        this.eventExpertise = eventExpertise;
    }

    public String getEventTalentCount() {
        return eventTalentCount;
    }

    public void setEventTalentCount(String eventTalentCount) {
        this.eventTalentCount = eventTalentCount;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
    }

    public String getEventJoinedUid() {
        return eventJoinedUid;
    }

    public void setEventJoinedUid(String eventJoinedUid) {
        this.eventJoinedUid = eventJoinedUid;
    }

    //    public Events(String eventTitle, String eventType, String date, String status, String eventDescription, String expertise, String talentCount, String location) {
//        this.eventTitle = eventTitle;
//        this.eventType = eventType;
//        this.date = date;
//        this.status = status;
//        this.eventDescription = eventDescription;
//        this.expertise = expertise;
//        this.talentCount = talentCount;
//        this.location = location;
//    }


}
