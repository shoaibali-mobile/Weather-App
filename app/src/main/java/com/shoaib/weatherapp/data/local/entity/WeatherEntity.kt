package com.shoaib.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shoaib.weatherapp.utils.Constants


@Entity(tableName = Constants.WEATHER_TABLE)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val city: String? = null,
    val country: String? = null,
    val temperatureC: Double? = null,
    val sunriseEpoch: Long? = null,
    val sunsetEpoch: Long? = null,
    val weatherType: String? = null,
    val fetchedAt: Long? = null,
    val iconUrl: String? = null
)
