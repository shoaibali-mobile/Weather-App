package com.shoaib.weatherapp.presentation.screens.signup.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.presentation.theme.TypographySizes

@Composable
fun SignUpTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.signup_title),
        fontSize = TypographySizes.TitleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

