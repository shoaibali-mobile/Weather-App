package com.shoaib.weatherapp.domain.usecase

import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double, apiKey: String): Weather?? {
        return withContext(Dispatchers.IO) {
            try {
                repository.getCurrentWeather(lat, lon, apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}