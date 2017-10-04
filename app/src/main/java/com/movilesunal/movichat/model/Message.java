package com.movilesunal.movichat.model;

/**
 * Created by erick on 4/10/2017.
 */

public class Message {

    private String user;
    private String text;
    private String hour;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
