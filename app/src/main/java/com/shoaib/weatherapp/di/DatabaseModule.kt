package com.shoaib.weatherapp.di

import android.app.Application
import androidx.room.Room
import com.shoaib.weatherapp.data.local.database.AppDatabase
import com.shoaib.weatherapp.utils.KeyStoreUtil
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton
import net.sqlcipher.database.SupportFactory as CipherFactory


@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): AppDatabase{
        SQLiteDatabase.loadLibs(app)

        val passphrase: ByteArray = KeyStoreUtil.getOrCreatePassphrase(app)

        val factory: SupportFactory = CipherFactory(passphrase)

        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "weather_db"
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun  provideWeatherDao(db: AppDatabase) = db.weatherDao()

    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.userDao()


}