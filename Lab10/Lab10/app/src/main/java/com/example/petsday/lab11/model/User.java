package com.example.petsday.lab11.model;

/**
 * Created by StellaSong on 2017/12/12.
 */

public class User {
    private String login;
    private String blog;
    private int id;

    public User(String login, String blog, int id) {
        this.login = login;
        this.blog = blog;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getBlog() {
        return blog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
