package com.shoaib.weatherapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shoaib.weatherapp.data.local.dao.UserDao
import com.shoaib.weatherapp.data.local.dao.WeatherDao
import com.shoaib.weatherapp.data.local.entity.UserEntity
import com.shoaib.weatherapp.data.local.entity.WeatherEntity


@Database(
    entities = [
        WeatherEntity::class,
        UserEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun weatherDao(): WeatherDao
    abstract fun userDao(): UserDao
}