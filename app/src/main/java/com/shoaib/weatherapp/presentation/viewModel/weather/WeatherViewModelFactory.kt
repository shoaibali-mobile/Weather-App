package com.shoaib.weatherapp.presentation.viewModel.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shoaib.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.shoaib.weatherapp.domain.usecase.GetWeatherHistoryUseCase
import com.shoaib.weatherapp.domain.usecase.SaveWeatherUseCase
import javax.inject.Inject


class WeatherViewModelFactory @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(getCurrentWeatherUseCase, getWeatherHistoryUseCase,saveWeatherUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}

