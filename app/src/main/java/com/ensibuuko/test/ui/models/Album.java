package com.ensibuuko.test.ui.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Album extends RealmObject {

    int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @PrimaryKey
    int id;
    String title;

}
