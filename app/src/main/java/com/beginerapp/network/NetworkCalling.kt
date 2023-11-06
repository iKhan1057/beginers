package com.beginerapp.network


import com.beginerapp.model.UsersParentModel
import retrofit2.Call
import retrofit2.http.GET

interface NetworkCalling {
    @GET("?results=10")
    fun getAllUserDetails(): Call<UsersParentModel>
}