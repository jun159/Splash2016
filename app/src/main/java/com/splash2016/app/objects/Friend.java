package com.splash2016.app.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class Friend {

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

    public Friend() {

    }

    public Friend(String name, String description) {
        this.name = name;
        this.description = description;
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

    public static List<Friend> getFriendList() {
        List<Friend> friendList = new ArrayList<>();

        friendList.add(new Friend(Friend.FRIEND_NAME_1, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_2, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_3, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_4, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_5, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_6, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_7, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_8, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_9, Friend.FRIEND_DESCRIPTION));
        friendList.add(new Friend(Friend.FRIEND_NAME_10, Friend.FRIEND_DESCRIPTION));

        return friendList;
    }
}
