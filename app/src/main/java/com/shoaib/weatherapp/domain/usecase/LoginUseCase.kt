package com.shoaib.weatherapp.domain.usecase

import com.shoaib.weatherapp.domain.model.User
import com.shoaib.weatherapp.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<User> {
        return userRepository.login(email, password)
    }
}