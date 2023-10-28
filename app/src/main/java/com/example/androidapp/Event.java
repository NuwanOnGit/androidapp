package com.example.androidapp;

public class Event {
    private String eventTitle, eventType, eventDate, eventLocation, eventDescription,eventExpertise,eventTalentCount;

    public Event(String eventTitle,String eventType,String eventDate,String eventLocation,String eventDescription,String eventExpertise,String eventTalentCount) {
        this.eventTitle = eventTitle;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventDescription = eventDescription;
        this.eventExpertise = eventExpertise ;
        this.eventTalentCount = eventTalentCount;


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

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
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

    Event(){

    }
}
