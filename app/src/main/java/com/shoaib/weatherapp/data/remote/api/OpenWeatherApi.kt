package com.shoaib.weatherapp.data.remote.api

import com.shoaib.weatherapp.data.remote.model.OneCallWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): OneCallWeatherResponse
}