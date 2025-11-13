package com.shoaib.weatherapp.domain.repository

import com.google.android.gms.common.api.internal.ApiKey
import com.shoaib.weatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository{
    suspend fun save(weather:Weather)
    fun getHistory():Flow<List<Weather>>
    suspend fun getCurrentWeather(lat:Double,lon:Double,apiKey: String): Weather
}