package com.shoaib.weatherapp.domain.model


data class Weather(
    val id: Long = 0L,
    val city: String = "Unknown City",
    val country: String = "N/A",
    val temperatureC: Double = 0.0,
    val sunriseEpoch: Long = 0L,
    val sunsetEpoch: Long = 0L,
    val weatherType: String = "Unknown",
    val fetchedAt: Long = 0L,
    val iconUrl: String = ""
)
