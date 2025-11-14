package com.shoaib.weatherapp.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.utils.formatEpoch

@Composable
fun MetaSection(weather: Weather) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LabeledValue(
            iconRes = R.drawable.sunrise,
            label = stringResource(R.string.sunrise),
            value = weather.sunriseEpoch.formatEpoch()
        )
        LabeledValue(
            iconRes = R.drawable.sunset,
            label = stringResource(R.string.sunset),
            value = weather.sunsetEpoch.formatEpoch()
        )
    }
}


