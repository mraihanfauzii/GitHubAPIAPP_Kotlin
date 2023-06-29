package com.bangkit.submissionfundamentalaplikasiandroid.api

import com.bangkit.submissionfundamentalaplikasiandroid.model.DetailUser
import com.bangkit.submissionfundamentalaplikasiandroid.model.User
import com.bangkit.submissionfundamentalaplikasiandroid.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}