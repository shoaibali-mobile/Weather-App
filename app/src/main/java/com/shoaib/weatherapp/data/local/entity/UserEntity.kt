package com.shoaib.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shoaib.weatherapp.utils.Constants

@Entity(tableName = Constants.USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)