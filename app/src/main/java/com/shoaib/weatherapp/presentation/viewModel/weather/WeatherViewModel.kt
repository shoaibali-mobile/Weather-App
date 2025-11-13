package com.shoaib.weatherapp.presentation.viewModel.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.weatherapp.presentation.model.WeatherUiState
import com.shoaib.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shoaib.weatherapp.BuildConfig

class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
): ViewModel() {

    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherState.value = WeatherUiState.Loading
            try {
                val apiKey = BuildConfig.API_KEY
                val result = getCurrentWeatherUseCase(lat, lon, apiKey)
                if (result != null) {
                    _weatherState.value = WeatherUiState.Success(result)
                } else {
                    _weatherState.value = WeatherUiState.Error("No weather data available.")
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error(
                    e.localizedMessage ?: "Something went wrong."
                )
            }
        }
    }

}

