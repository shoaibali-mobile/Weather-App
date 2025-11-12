package com.shoaib.weatherapp.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.presentation.theme.Dimens
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay


@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.PaddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginTitle()

            Spacer(modifier = Modifier.height(Dimens.PaddingExtraLarge))

            EmailTextField(
                value = email,
                onValueChange = { email = it },
                errorMessage = emailError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                errorMessage = passwordError,
                passwordVisible = passwordVisible,
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

            LoginButton(
                onClick = {
                    onNavigateToHome()
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            SignUpButton(
                onClick = onSignUpClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.password_label)) },
        placeholder = { Text(stringResource(R.string.password_hint)) },
        modifier = modifier,
        singleLine = true,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = errorMessage != null,
        supportingText = errorMessage?.let {
            { Text(text = it) }
        },
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityToggle) {
                Icon(
                    imageVector = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = if (passwordVisible) {
                        stringResource(R.string.hide_password)
                    } else {
                        stringResource(R.string.show_password)
                    }
                )
            }
        }
    )
}



@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.email_label)) },
        placeholder = { Text(stringResource(R.string.email_hint)) },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = errorMessage != null,
        supportingText = errorMessage?.let {
            { Text(text = it) }
        }
    )
}

@Composable
fun LoginTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.login_title),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}


@Composable
fun SignUpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.signup_button_text),
            color = MaterialTheme.colorScheme.primary
        )
    }
}



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
