package com.shoaib.weatherapp.presentation.viewmodel

import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.shoaib.weatherapp.domain.usecase.GetWeatherHistoryUseCase
import com.shoaib.weatherapp.domain.usecase.SaveWeatherUseCase
import com.shoaib.weatherapp.presentation.model.WeatherUiState
import com.shoaib.weatherapp.presentation.viewModel.weather.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class WeatherViewModelTest {

    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase
    private lateinit var getWeatherHistoryUseCase: GetWeatherHistoryUseCase
    private lateinit var saveWeatherUseCase: SaveWeatherUseCase
    private lateinit var viewModel: WeatherViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCurrentWeatherUseCase = mock()
        getWeatherHistoryUseCase = mock()
        saveWeatherUseCase = mock()
        viewModel = WeatherViewModel(getCurrentWeatherUseCase, getWeatherHistoryUseCase, saveWeatherUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchWeatherSuccess() = runTest {

        val lat = 40.7128
        val lon = -74.0060
        val weather = Weather(
            city = "New York",
            country = "US",
            temperatureC = 25.5,
            weatherType = "Clear"
        )

        whenever(getCurrentWeatherUseCase(eq(lat), eq(lon), any())).thenAnswer { weather }

        viewModel.fetchWeather(lat, lon)
        advanceUntilIdle()

        val state = viewModel.weatherState.value
        assertTrue(state is WeatherUiState.Success)

        if (state is WeatherUiState.Success) {
            assertEquals("New York", state.weather.city)
            assertEquals("US", state.weather.country)
            assertEquals(25.5, state.weather.temperatureC, 0.01)
        }

        verify(saveWeatherUseCase).invoke(weather)
    }

    @Test
    fun testFetchWeatherNullResponse() = runTest {
        val lat = 40.7128
        val lon = -74.0060
        whenever(getCurrentWeatherUseCase(eq(lat), eq(lon), any())).thenAnswer { null }

        viewModel.fetchWeather(lat, lon)
        advanceUntilIdle()

        val state = viewModel.weatherState.value
        assertTrue(state is WeatherUiState.Error)

        if (state is WeatherUiState.Error) {
            assertEquals("No weather data available.", state.message)
        }
    }

    @Test
    fun testInitialStateIsIdle() {
        val state = viewModel.weatherState.value

        assertTrue(state is WeatherUiState.Idle)
    }

    @Test
    fun testFetchWeatherExceptionWithNullMessage() = runTest {
        val lat = 40.7128
        val lon = -74.0060

        whenever(getCurrentWeatherUseCase(eq(lat), eq(lon), any()))
            .thenThrow(RuntimeException())

        viewModel.fetchWeather(lat, lon)
        advanceUntilIdle()

        val state = viewModel.weatherState.value
        assertTrue(state is WeatherUiState.Error)

        if (state is WeatherUiState.Error) {
            assertEquals("Something went wrong.", state.message)
        }
    }

    @Test
    fun testFetchWeatherExceptionWithMessage() = runTest {
        val lat = 40.7128
        val lon = -74.0060
        val errorMessage = "Network connection failed"

        whenever(getCurrentWeatherUseCase(eq(lat), eq(lon), any()))
            .thenThrow(RuntimeException(errorMessage))

        viewModel.fetchWeather(lat, lon)
        advanceUntilIdle()

        val state = viewModel.weatherState.value
        assertTrue(state is WeatherUiState.Error)

        if (state is WeatherUiState.Error) {
            assertEquals(errorMessage, state.message)
        }
    }

    @Test
    fun testGetWeatherHistory() = runTest {
        val historyList = listOf(
            Weather(
                city = "Paris",
                country = "FR",
                temperatureC = 20.0,
                weatherType = "Cloudy",
                fetchedAt = System.currentTimeMillis()
            ),
            Weather(
                city = "Berlin",
                country = "DE",
                temperatureC = 15.0,
                weatherType = "Rainy",
                fetchedAt = System.currentTimeMillis()
            )
        )

        whenever(getWeatherHistoryUseCase()).thenReturn(flowOf(historyList))

        val result = viewModel.getWeatherHistory()

        val collectedHistory = result.first()
        assertEquals(2, collectedHistory.size)
        assertEquals("Paris", collectedHistory[0].city)
        assertEquals("Berlin", collectedHistory[1].city)

        verify(getWeatherHistoryUseCase).invoke()
    }
}
