package com.example.weatherapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.model.Weather
import com.example.weatherapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object WeatherRepository {

    public var APP_ID: String = "345eecec647da5cf804f4e9317e4a1d5"
    val weatherSetterGetter = MutableLiveData<Weather>()




    fun getWeatherApiCall(lat: String?, lon: String?): MutableLiveData<Weather> {

        val call = RetrofitClient.apiInterface.getWeather(lat,lon, APP_ID)

        call.enqueue(object: Callback<Weather> {
            override fun onFailure(call: Call<Weather>, t: Throwable) {

                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<Weather>,
                response: Response<Weather>
            ) {
                Log.v("DEBUG : ", response.body().toString())
                val data = response.body()

                weatherSetterGetter.value=response.body()

                //val msg = data!!.message
                //serviceSetterGetter.value = Weather(msg)
            }
        })

        return weatherSetterGetter
    }




}