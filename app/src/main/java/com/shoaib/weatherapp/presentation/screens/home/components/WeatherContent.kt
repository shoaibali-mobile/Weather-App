package com.shoaib.weatherapp.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.coerceIn
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.domain.model.Weather
import com.shoaib.weatherapp.presentation.theme.Dimens

@Composable
fun WeatherContent(
    weather: Weather,
    latitude: Double?,
    longitude: Double?,
    onRefresh: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val topPadding = (screenHeight * 0.08f).coerceIn(Dimens.PaddingMedium, Dimens.TopPaddingMax)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.PaddingMedium)
            .padding(top = topPadding),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.CardPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderSection(weather)
                Spacer(Modifier.height(Dimens.SpacingMedium))
                IconAndTempSection(weather)
                Spacer(Modifier.height(Dimens.SpacingSmall))
                MetaSection(weather)
            }
        }
        Spacer(Modifier.height(Dimens.SpacingLarge))
        Button(onClick = {
            if (latitude != null && longitude != null) {
                onRefresh()
            }
        }) {
            Text(stringResource(R.string.refresh))
        }
    }
}

