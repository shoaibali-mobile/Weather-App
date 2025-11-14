package com.shoaib.weatherapp.presentation.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.utils.toFormattedTime

@Composable
fun HistoryItemView(weather: Weather) {
    val sunriseTime = weather.sunriseEpoch.toFormattedTime()
    val sunsetTime = weather.sunsetEpoch.toFormattedTime()
    val fetchedAt = weather.fetchedAt.toFormattedTime(stringResource(R.string.date_month_year))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            WeatherHeader(city = weather.city, country = weather.country)

            Spacer(Modifier.height(12.dp))

            TemperatureSection(
                temperature = weather.temperatureC,
                iconUrl = weather.iconUrl,
                weatherType = weather.weatherType

            )

            Spacer(Modifier.height(12.dp))

            ExtraInfoRow(
                sunriseTime = sunriseTime,
                sunsetTime = sunsetTime,
                fetchedAt = fetchedAt
            )
        }
    }
}

@Composable
fun ExtraInfoRow(sunriseTime: String, sunsetTime: String, fetchedAt: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.sunrise_history, sunriseTime),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.sunset_history, sunsetTime),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "$fetchedAt",
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun TemperatureSection(temperature: Double, iconUrl: String, weatherType: String) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp


    val iconSize = (screenWidth * 0.25f).dp.coerceIn(80.dp, 120.dp)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.temperature, temperature),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Text(
                text = weatherType,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        Image(
            painter = rememberAsyncImagePainter(
                iconUrl
            ),
            contentDescription = weatherType,
            modifier = Modifier.size(iconSize),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun WeatherHeader(city: String, country: String) {
    Text(
        text = stringResource(R.string.city_country, city, country),
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary
        )
    )
}

