package com.shoaib.weatherapp.domain.model

data class User(
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)