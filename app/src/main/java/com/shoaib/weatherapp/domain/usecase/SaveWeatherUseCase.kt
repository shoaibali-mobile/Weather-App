package com.shoaib.weatherapp.domain.usecase

import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(weather: Weather) {
        repository.save(weather)
    }
}