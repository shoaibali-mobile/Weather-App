package com.shoaib.weatherapp.presentation.model


sealed class AuthUiState {
    data object Idle : AuthUiState()
    data object Loading : AuthUiState()
    data object SignUpSuccess : AuthUiState()
    data object LoginSuccess : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}