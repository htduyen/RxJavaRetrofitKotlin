package com.example.rxkotlinretrofit.Retrofit

import com.example.rxkotlinretrofit.Models.TimeModel
import io.reactivex.Observable

import retrofit2.http.GET

interface TimeAPI {


    @GET("api/timezone/Europe/London")
    fun getTime():Observable<TimeModel>


}