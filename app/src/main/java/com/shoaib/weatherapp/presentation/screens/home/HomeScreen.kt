package com.shoaib.weatherapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.presentation.model.WeatherUiState
import com.shoaib.weatherapp.presentation.screens.home.components.WeatherContent
import com.shoaib.weatherapp.presentation.viewModel.weather.WeatherViewModel
import com.shoaib.weatherapp.utils.LocationPermissionHandler
import com.shoaib.weatherapp.utils.rememberLocationPermissionLauncher




@Composable
fun HomeScreen(
    viewModel: WeatherViewModel,
    locationPermissionHandler: LocationPermissionHandler
) {
    val weatherState = viewModel.weatherState.collectAsState()
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    rememberLocationPermissionLauncher(
        locationPermissionHandler = locationPermissionHandler,
        onLocationReceived = { lat, lon ->
            latitude = lat
            longitude = lon
            viewModel.fetchWeather(lat = lat, lon = lon)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (val state = weatherState.value) {
            is WeatherUiState.Error -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
            WeatherUiState.Idle -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text(stringResource(R.string.fetching_live_weather_data))
                }
            }
            WeatherUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is WeatherUiState.Success -> {
                WeatherContent(
                    weather = state.weather,
                    latitude = latitude,
                    longitude = longitude,
                    onRefresh = {
                        if (latitude != null && longitude != null) {
                            viewModel.fetchWeather(lat = latitude!!, lon = longitude!!)
                        } else {
                            if (locationPermissionHandler.hasLocationPermission()) {
                                try {
                                    locationPermissionHandler.getCurrentLocation(
                                        onLocationReceived = { lat, lon ->
                                            latitude = lat
                                            longitude = lon
                                            viewModel.fetchWeather(lat = lat, lon = lon)
                                        }
                                    )
                                } catch (e: SecurityException) {
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}



