package com.shoaib.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class OneCallWeatherResponse(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("sys")
    val sys: Sys? = null,

    @SerializedName("main")
    val main: Main? = null,

    @SerializedName("weather")
    val weather: List<WeatherItem>? = null
)
