package com.shoaib.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("main")
    val main: String? = null,

    @SerializedName("icon")
    val icon: String? = null
)




