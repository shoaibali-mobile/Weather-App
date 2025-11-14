package com.shoaib.weatherapp.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.utils.Constants
import com.shoaib.weatherapp.presentation.theme.TypographySizes
import kotlinx.coroutines.delay



@Composable
fun SplashScreen(
    onNavigateToLogin:()->Unit
){
    val configuration = LocalConfiguration.current

    val textSize = (configuration.screenWidthDp * 0.12f)
        .coerceIn(TypographySizes.TitleMin.value, TypographySizes.TitleMax.value)
        .sp

    LaunchedEffect(Unit) {
        delay(Constants.SPLASH_DELAY_MS)

            onNavigateToLogin()

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.app_title),
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayMedium
        )
    }


}
