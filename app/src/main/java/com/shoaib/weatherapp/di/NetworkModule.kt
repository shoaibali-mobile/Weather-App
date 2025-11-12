package com.shoaib.weatherapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shoaib.weatherapp.data.remote.api.OpenWeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object NetworkModule{

    private const val BASE_URL = "https://api.openweathermap.org/"

    @Provides
    @Singleton
    fun provideInterceptors(): Interceptor{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)
}