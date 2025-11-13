package com.shoaib.weatherapp.presentation.model

import com.shoaib.weatherapp.domain.model.Weather

sealed class WeatherUiState {
    object Idle : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val weather: Weather) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}