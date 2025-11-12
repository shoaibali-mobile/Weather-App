package com.shoaib.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shoaib.weatherapp.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity)

    @Query("SELECT * FROM weather ORDER BY fetchedAt DESC")
    fun getAllHistory():Flow<List<WeatherEntity>>
}