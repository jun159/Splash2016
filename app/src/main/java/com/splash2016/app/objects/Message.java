package com.splash2016.app.objects;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class Message {

    private long id;
    private String friendName;
    private String message;
    private boolean isSelf;
    private String date;
    private String time;
    private long dateTime;

    public Message() {

    }

    public Message(long id, String friendName, String message, boolean isSelf, String date, String time, long dateTime) {
        this.id = id;
        this.friendName = friendName;
        this.message = message;
        this.isSelf = isSelf;
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
