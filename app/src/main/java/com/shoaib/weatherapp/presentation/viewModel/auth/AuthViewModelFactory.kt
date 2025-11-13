package com.shoaib.weatherapp.presentation.viewModel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shoaib.weatherapp.domain.usecase.LoginUseCase
import com.shoaib.weatherapp.domain.usecase.SignUpUseCase
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(signUpUseCase, loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

