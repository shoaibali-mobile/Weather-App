package com.shoaib.weatherapp.presentation.viewModel.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoaib.weatherapp.presentation.model.AuthUiState
import com.shoaib.weatherapp.domain.usecase.LoginUseCase
import com.shoaib.weatherapp.domain.usecase.SignUpUseCase
import com.shoaib.weatherapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            signUpUseCase(firstName, lastName, email, password)
                .onSuccess {
                    _authState.value = AuthUiState.SignUpSuccess
                }
                .onFailure { exception ->
                    _authState.value = AuthUiState.Error(
                        exception.message ?:"Sign up failed"
                    )
                }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            loginUseCase(email, password)
                .onSuccess {
                    _authState.value = AuthUiState.LoginSuccess
                }
                .onFailure { exception ->
                    _authState.value = AuthUiState.Error(
                        exception.message ?: "error_login_failed"
                    )
                }
        }
    }


    fun resetState() {
        _authState.value = AuthUiState.Idle
    }


}

