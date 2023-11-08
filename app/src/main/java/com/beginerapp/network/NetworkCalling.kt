package com.beginerapp.network


import com.beginerapp.model.UserParentModel
import retrofit2.Call
import retrofit2.http.GET

interface NetworkCalling {
    @GET("?results=5")
    fun getAllUserDetails(): Call<UserParentModel>
}