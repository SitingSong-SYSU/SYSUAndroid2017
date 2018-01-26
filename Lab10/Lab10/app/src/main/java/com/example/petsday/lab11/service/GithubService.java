package com.example.petsday.lab11.service;

import rx.Observable;

import com.example.petsday.lab11.model.User;

import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by StellaSong on 2017/12/12.
 */

public interface GithubService {
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String user);
}