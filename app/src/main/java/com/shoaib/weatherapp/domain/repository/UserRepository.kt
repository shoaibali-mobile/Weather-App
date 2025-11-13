package com.shoaib.weatherapp.domain.repository

import com.shoaib.weatherapp.domain.model.User


interface UserRepository {
    suspend fun signUp(user: User): Result<Long>
    suspend fun login(email: String, password: String): Result<User>
}