package com.shoaib.weatherapp.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.presentation.theme.Dimens

@Composable
fun IconAndTempSection(weather: Weather) {
    val configuration = LocalConfiguration.current

    val iconSize = (configuration.screenWidthDp * 0.15f).dp.coerceIn(Dimens.IconSizeMin, Dimens.IconSizeMax)

    Image(
        painter = rememberAsyncImagePainter(weather.iconUrl),
        contentDescription = stringResource(R.string.weather_icon_description),
        modifier = Modifier.size(iconSize)
    )

    Spacer(Modifier.height(Dimens.SpacingSmall))
    Text(
        text = stringResource(R.string.temperature_format, weather.temperatureC),
        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold)
    )
    Spacer(Modifier.height(Dimens.SpacingExtraSmall))
    Text(text = weather.weatherType, style = MaterialTheme.typography.bodyLarge)
}