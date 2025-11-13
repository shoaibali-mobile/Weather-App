package com.shoaib.weatherapp.data.repository

import com.shoaib.weatherapp.data.local.dao.WeatherDao
import com.shoaib.weatherapp.data.local.entity.WeatherEntity
import com.shoaib.weatherapp.data.remote.api.OpenWeatherApi
import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.domain.repository.WeatherRepository
import com.shoaib.weatherapp.utils.toIconUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val api: OpenWeatherApi
): WeatherRepository{
    override suspend fun save(weather: Weather) {
        val entity = WeatherEntity(
            id = weather.id,
            city = weather.city,
            country = weather.country,
            temperatureC = weather.temperatureC,
            sunriseEpoch = weather.sunriseEpoch,
            sunsetEpoch = weather.sunsetEpoch,
            weatherType = weather.weatherType,
            fetchedAt = weather.fetchedAt,
            iconUrl = weather.iconUrl
        )
        weatherDao.insert(entity)
    }

    override fun getHistory(): Flow<List<Weather>> =
        weatherDao.getAllHistory().map { entities ->
            entities.map { e ->
                Weather(
                    id = e.id,
                    city = e.city ?: "Unknown City",
                    country = e.country ?: "N/A",
                    temperatureC = e.temperatureC ?: 0.0,
                    sunriseEpoch = e.sunriseEpoch ?: 0L,
                    sunsetEpoch = e.sunsetEpoch ?: 0L,
                    weatherType = e.weatherType ?: "Unknown",
                    fetchedAt = e.fetchedAt ?: System.currentTimeMillis(),
                    iconUrl = e.iconUrl ?: ""
                )
            }
        }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        apiKey: String
    ): Weather {
        val response = api.getCurrentWeather(lat, lon, apiKey)


        val city = response.name ?: "Unknown City"
        val country = response.sys?.country ?: "N/A"
        val temperature = response.main?.temp ?: 0.0
        val sunrise = response.sys?.sunrise ?: 0L
        val sunset = response.sys?.sunset ?: 0L
        val weatherItem = response.weather?.firstOrNull()
        val iconUrl = weatherItem?.icon?.toIconUrl() ?: ""
        val weatherType = weatherItem?.main ?: "Unknown"

        return Weather(
            city = city,
            country = country,
            temperatureC = temperature,
            sunriseEpoch = sunrise,
            sunsetEpoch = sunset,
            weatherType = weatherType,
            fetchedAt = System.currentTimeMillis(),
            iconUrl = iconUrl
        )
    }

}