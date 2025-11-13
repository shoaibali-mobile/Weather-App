package com.shoaib.weatherapp.di

import com.shoaib.weatherapp.data.repository.UserRepositoryImpl
import com.shoaib.weatherapp.data.repository.WeatherRepositoryImpl
import com.shoaib.weatherapp.domain.repository.UserRepository
import com.shoaib.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}


