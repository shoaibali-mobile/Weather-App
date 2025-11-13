package com.shoaib.weatherapp.presentation.screens.signup.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.shoaib.weatherapp.R

@Composable
fun LastNameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.last_name_label)) },
        placeholder = { Text(stringResource(R.string.last_name_hint)) },
        modifier = modifier,
        singleLine = true,
        isError = errorMessage != null,
        supportingText = errorMessage?.let {
            { Text(text = it) }
        }
    )
}

