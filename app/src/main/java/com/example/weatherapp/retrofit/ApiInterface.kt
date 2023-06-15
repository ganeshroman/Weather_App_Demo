package com.example.weatherapp.retrofit

import com.example.weatherapp.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getWeather(@Query("lat") lat: String?,@Query("lon") lon: String?,@Query("appid") appid: String?) : Call<Weather>

}