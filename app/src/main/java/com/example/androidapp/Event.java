package com.example.androidapp;

public class Event {
    private String title, type, date, status, description,count;

    public Event(String name, String type, String date, String status, String description, String count) {
        this.title = name;
        this.type = type;
        this.date = date;
        this.status = status;
        this.description = description;
        this.count = count;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    Event(){

    }
}
