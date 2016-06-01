package com.splash2016.app.objects;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class Friend {

    private String name;
    private String description;

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
}
