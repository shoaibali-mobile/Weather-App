package com.shoaib.weatherapp.presentation.screens.login.components

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shoaib.weatherapp.R

@Composable
fun LoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isTextButton: Boolean = false,
    text: String? = null
) {
    val buttonText = text ?: stringResource(R.string.login_button_text)
    if (isTextButton) {
        TextButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Text(
                text = buttonText,
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier
        ) {
            Text(buttonText)
        }
    }
}



