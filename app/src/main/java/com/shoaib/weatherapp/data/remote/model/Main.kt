package com.shoaib.weatherapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: Double? = null
)




