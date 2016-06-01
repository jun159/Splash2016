package com.splash2016.app.objects;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class Message {

    private String friendName;
    private String message;
    private boolean isSelf;
    private String date;
    private String time;

    public Message() {

    }

    public Message(String friendName, String message, boolean isSelf, String date, String time) {
        this.friendName = friendName;
        this.message = message;
        this.isSelf = isSelf;
        this.date = date;
        this.time = time;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFromName(String friendName) {
        this.friendName = friendName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
