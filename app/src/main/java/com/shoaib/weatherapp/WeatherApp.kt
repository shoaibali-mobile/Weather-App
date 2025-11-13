package com.shoaib.weatherapp

import android.app.Application
import com.shoaib.weatherapp.di.AppComponent
import com.shoaib.weatherapp.di.DaggerAppComponent

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}


