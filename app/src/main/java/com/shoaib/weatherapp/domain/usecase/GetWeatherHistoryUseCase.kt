package com.shoaib.weatherapp.domain.usecase

import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetWeatherHistoryUseCase @Inject constructor(private val repository: WeatherRepository) {

    operator fun invoke(): Flow<List<Weather>> {
        return repository.getHistory()
    }

}