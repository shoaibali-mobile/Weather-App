package com.shoaib.weatherapp.presentation.screens.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.shoaib.weatherapp.domain.model.Weather

@Composable
fun HeaderSection(weather: Weather) {
    Text(
        text = listOfNotBlank(weather.city, weather.country).joinToString(", "),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
    )
}

private fun listOfNotBlank(vararg items: String): List<String> =
    items.filter { it.isNotBlank() }


