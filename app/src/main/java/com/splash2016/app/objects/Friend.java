package com.splash2016.app.objects;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class Friend implements Comparable<Friend> {

    private static final String FRIEND_NAME_1 = "Friend 1";
    private static final String FRIEND_NAME_2 = "Friend 2";
    private static final String FRIEND_NAME_3 = "Friend 3";
    private static final String FRIEND_NAME_4 = "Friend 4";
    private static final String FRIEND_NAME_5 = "Friend 5";
    private static final String FRIEND_NAME_6 = "Friend 6";
    private static final String FRIEND_NAME_7 = "Friend 7";
    private static final String FRIEND_NAME_8 = "Friend 8";
    private static final String FRIEND_NAME_9 = "Friend 9";
    private static final String FRIEND_NAME_10 = "Friend 10";
    private static final String FRIEND_DESCRIPTION= "Hi, nice to meet you >:)";

    private String name;
    private String description;
    private String details;
    private String lastMessageDate;
    private String lastMessageTime;
    private long lastMessageDateTime;

    public Friend() {

    }

    public Friend(String name, String description, String details) {
        this.name = name;
        this.description = description;
        this.details = details;
    }

    public Friend(String name, String description, String lastMessageDate,
                  String lastMessageTime, long lastMessageDateTime) {
        this.name = name;
        this.description = description;
        this.lastMessageDate = lastMessageDate;
        this.lastMessageTime = lastMessageTime;
        this.lastMessageDateTime = lastMessageDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public long getLastMessageDateTime() {
        return lastMessageDateTime;
    }

    public void setLastMessageDateTime(long lastMessageDateTime) {
        this.lastMessageDateTime = lastMessageDateTime;
    }

    public static List<Friend> getFriendList() {
        List<Friend> friendList = new ArrayList<>();

        friendList.add(new Friend(Friend.FRIEND_NAME_1, Friend.FRIEND_DESCRIPTION, "27 | 0.8km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_2, Friend.FRIEND_DESCRIPTION, "33 | 1.1km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_3, Friend.FRIEND_DESCRIPTION, "23 | 1.3km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_4, Friend.FRIEND_DESCRIPTION, "40 | 1.3km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_5, Friend.FRIEND_DESCRIPTION, "38 | 1.6km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_6, Friend.FRIEND_DESCRIPTION, "38 | 1.8km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_7, Friend.FRIEND_DESCRIPTION, "36 | 1.8km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_8, Friend.FRIEND_DESCRIPTION, "32 | 2.1km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_9, Friend.FRIEND_DESCRIPTION, "33 | 2.3km"));
        friendList.add(new Friend(Friend.FRIEND_NAME_10, Friend.FRIEND_DESCRIPTION, "28 | 2.4km"));

        return friendList;
    }

    @Override
    public int compareTo(@NonNull Friend another) {
        if (this.getLastMessageDateTime() < another.getLastMessageDateTime()) {
            return 1;
        } else if (this.getLastMessageDateTime() > another.getLastMessageDateTime()) {
            return -1;
        }

        return 0;
    }
}
