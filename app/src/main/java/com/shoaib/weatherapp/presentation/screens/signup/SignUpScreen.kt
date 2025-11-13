package com.shoaib.weatherapp.presentation.screens.signup

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
import androidx.compose.ui.unit.dp
import com.shoaib.weatherapp.presentation.model.AuthUiState
import com.shoaib.weatherapp.presentation.theme.Dimens
import com.shoaib.weatherapp.presentation.screens.signup.components.ConfirmPasswordTextField
import com.shoaib.weatherapp.presentation.screens.signup.components.FirstNameTextField
import com.shoaib.weatherapp.presentation.screens.signup.components.LastNameTextField
import com.shoaib.weatherapp.presentation.screens.signup.components.SignUpTitle
import com.shoaib.weatherapp.presentation.viewModel.auth.AuthViewModel
import com.shoaib.weatherapp.presentation.screens.login.EmailTextField
import com.shoaib.weatherapp.presentation.screens.login.LoginButton
import com.shoaib.weatherapp.presentation.screens.login.PasswordTextField
import com.shoaib.weatherapp.presentation.screens.signup.components.SignUpButton
import com.shoaib.weatherapp.utils.getEmailError
import com.shoaib.weatherapp.utils.getPasswordError
import com.shoaib.weatherapp.R
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay


@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel
){
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    var firstNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var lastNameError by rememberSaveable { mutableStateOf<String?>(null) }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmPasswordError by rememberSaveable { mutableStateOf<String?>(null) }

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    val resources = context.resources

    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthUiState.SignUpSuccess -> {
                Toast.makeText(
                    context,
                    resources.getString(R.string.toast_signup_success),
                    Toast.LENGTH_SHORT
                ).show()
                delay(1500)
                onNavigateToLogin()
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
    ){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.PaddingLarge, vertical = Dimens.PaddingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            SignUpTitle()

            Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

            FirstNameTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                    firstNameError = null
                },
                errorMessage = firstNameError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            LastNameTextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                    lastNameError = null
                },
                errorMessage = lastNameError,
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            EmailTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                errorMessage = emailError,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            PasswordTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                errorMessage = passwordError,
                passwordVisible = passwordVisible,
                onPasswordVisibilityToggle = { passwordVisible = !passwordVisible },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            ConfirmPasswordTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = null
                },
                errorMessage = confirmPasswordError,
                passwordVisible = confirmPasswordVisible,
                onPasswordVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingLarge))

            SignUpButton(
                onClick = {
                    val firstNameErr = if (firstName.isEmpty()) resources.getString(R.string.error_first_name_required) else null
                    val lastNameErr = if (lastName.isEmpty()) resources.getString(R.string.error_last_name_required) else null
                    val emailErr = email.getEmailError(resources)
                    val passwordErr = password.getPasswordError(resources)
                    val confirmPasswordErr = when {
                        confirmPassword.isEmpty() -> resources.getString(R.string.error_confirm_password_required)
                        confirmPassword != password -> resources.getString(R.string.error_passwords_not_match)
                        else -> null
                    }

                    firstNameError = firstNameErr
                    lastNameError = lastNameErr
                    emailError = emailErr
                    passwordError = passwordErr
                    confirmPasswordError = confirmPasswordErr

                    if (firstNameErr == null && lastNameErr == null &&
                        emailErr == null && passwordErr == null && confirmPasswordErr == null) {
                        viewModel.signUp(firstName, lastName, email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))

            LoginButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth(),
                isTextButton = true,
                text = stringResource(R.string.already_user_login)
            )

        }

    }
}

