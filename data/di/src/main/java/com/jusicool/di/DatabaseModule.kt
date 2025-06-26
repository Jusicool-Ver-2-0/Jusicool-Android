package com.jusicool.di

import android.content.Context
import androidx.room.Room
import com.jusicool.local.JusicoolDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideJusicoolDatabase(appContext: Context): JusicoolDataBase {
        return Room.databaseBuilder(
            appContext,
            JusicoolDataBase::class.java,
            "jusicool_database"
        ).build()
    }
}
