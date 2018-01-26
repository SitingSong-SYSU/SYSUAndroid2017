package com.example.petsday.lab11.service;

import com.example.petsday.lab11.model.Repos;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by StellaSong on 2017/12/14.
 */

public interface ReposService {
    @GET("/users/{user}/repos")
    Observable<List<Repos>> getRepos(@Path("user") String user);
}