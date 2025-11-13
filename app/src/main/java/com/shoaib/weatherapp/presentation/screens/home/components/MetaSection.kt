package com.shoaib.weatherapp.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.domain.model.Weather

@Composable
fun MetaSection(weather: Weather) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LabeledValue(
            iconRes = R.drawable.sunrise,
            label = "Sunrise",
            value = formatEpoch(weather.sunriseEpoch)
        )
        LabeledValue(
            iconRes = R.drawable.sunset,
            label = "Sunset",
            value = formatEpoch(weather.sunsetEpoch)
        )
    }
}

private fun formatEpoch(epoch: Long): String {
    if (epoch <= 0L) return "--:--"
    val sdf = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(epoch * 1000))
}


