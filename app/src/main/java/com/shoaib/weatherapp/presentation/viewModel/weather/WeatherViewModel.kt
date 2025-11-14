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
import com.shoaib.weatherapp.domain.usecase.GetWeatherHistoryUseCase
import com.shoaib.weatherapp.domain.usecase.SaveWeatherUseCase
import com.shoaib.weatherapp.utils.Constants

class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase
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
                    saveWeatherUseCase(result)
                    _weatherState.value = WeatherUiState.Success(result)
                } else {
                    _weatherState.value = WeatherUiState.Error(Constants.ERROR_NO_WEATHER_DATA)
                }
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error(
                    e.localizedMessage ?: Constants.ERROR_SOMETHING_WENT_WRONG
                )
            }
        }
    }


    fun getWeatherHistory() = getWeatherHistoryUseCase()

}

