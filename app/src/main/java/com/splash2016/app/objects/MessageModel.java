package com.splash2016.app.objects;

import java.util.ArrayList;

/**
 * Created by BAOJUN on 4/6/16.
 */
public class MessageModel {

    private String dateHeader;
    private ArrayList<Message> allMessagesInSection;

    public String getDateHeader() {
        return dateHeader;
    }

    public void setDateHeader(String dateHeader) {
        this.dateHeader = dateHeader;
    }

    public ArrayList<Message> getAllMessagesInSection() {
        return allMessagesInSection;
    }

    public void setAllMessagesInSection(ArrayList<Message> allMessagesInSection) {
        this.allMessagesInSection = allMessagesInSection;
    }
}
