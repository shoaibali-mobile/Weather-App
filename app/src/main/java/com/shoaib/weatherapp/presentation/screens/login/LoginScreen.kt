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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.presentation.theme.Dimens
import com.shoaib.weatherapp.presentation.screens.login.components.EmailTextField
import com.shoaib.weatherapp.presentation.screens.login.components.LoginButton
import com.shoaib.weatherapp.presentation.screens.login.components.LoginTitle
import com.shoaib.weatherapp.presentation.screens.login.components.PasswordTextField
import com.shoaib.weatherapp.presentation.screens.login.components.SignUpButton
import com.shoaib.weatherapp.presentation.model.AuthUiState
import com.shoaib.weatherapp.presentation.viewModel.auth.AuthViewModel
import com.shoaib.weatherapp.utils.getEmailError
import com.shoaib.weatherapp.utils.getPasswordError
import kotlinx.coroutines.delay


@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: AuthViewModel
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    val resources = context.resources

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthUiState.LoginSuccess -> {
                Toast.makeText(
                    context,
                    resources.getString(R.string.toast_login_success),
                    Toast.LENGTH_SHORT
                ).show()
                delay(1000)
                onNavigateToHome()
                viewModel.resetState()
            }
            is AuthUiState.Error -> {
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

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
                    val emailErr = email.getEmailError(resources)
                    val passwordErr = password.getPasswordError(resources)

                    emailError = emailErr
                    passwordError = passwordErr

                    if (emailErr == null && passwordErr == null) {
                        viewModel.login(email, password)
                    }
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
