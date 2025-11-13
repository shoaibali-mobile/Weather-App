package com.shoaib.weatherapp.di

import android.app.Application
import com.shoaib.weatherapp.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent{

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance app: Application) : AppComponent
    }

    fun inject(activity: MainActivity)

}

