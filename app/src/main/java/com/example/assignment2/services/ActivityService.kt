package com.example.assignment2.services

import com.example.assignment2.models.Activity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ActivityService {
    @GET("activity")
    fun getActivity(@Query("type") type: String?): Call<Activity>
}