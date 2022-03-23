package com.example.githubprojectv2.api

import com.example.githubprojectv2.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Get user by id
    @GET("users/{id}")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getUser(
        @Path("id") id: String
    ): Call<Accounts>

    //Search user by id?
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getSearch(
        @Query("q") search: String,
        @Query("per_page") perPage: Int = 50
    ): Call<ResponseGit>

    //Get follower by id
    @GET("users/{id}/followers")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollower(
        @Path("id") id: String
    ): Call<ArrayList<ResponseFollowItem>>

    //Get follows by id
    @GET("users/{id}/following")
    @Headers("Authorization: token ${BuildConfig.API_KEY}")
    fun getFollows(
        @Path("id") id: String
    ): Call<ArrayList<ResponseFollowItem>>
}