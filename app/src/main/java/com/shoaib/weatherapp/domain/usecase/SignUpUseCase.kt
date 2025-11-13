package com.shoaib.weatherapp.domain.usecase

import com.shoaib.weatherapp.domain.model.User
import com.shoaib.weatherapp.domain.repository.UserRepository
import javax.inject.Inject


class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<Long> {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
        return userRepository.signUp(user)
    }
}