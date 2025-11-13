package com.shoaib.weatherapp.data.repository

import com.shoaib.weatherapp.data.local.dao.UserDao
import com.shoaib.weatherapp.data.local.entity.UserEntity
import com.shoaib.weatherapp.domain.model.User
import com.shoaib.weatherapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun signUp(user: User): Result<Long> {
        return try {
            val userEntity = UserEntity(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                password = user.password
            )
            val userId = userDao.insertUser(userEntity)
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val userEntity = userDao.getUserByEmailAndPassword(email, password)
            if (userEntity != null) {
                val user = User(
                    id = userEntity.id,
                    firstName = userEntity.firstName,
                    lastName = userEntity.lastName,
                    email = userEntity.email,
                    password = userEntity.password
                )
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid email or password"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}