package com.jusicool.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
// import com.jusicool.local.JusicoolDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    //    @Provides
//    @Singleton
//    fun provideJusicoolDatabase(appContext: Context): JusicoolDataBase {
//        return Room.databaseBuilder(
//            appContext,
//            JusicoolDataBase::class.java,
//            "jusicool_database"
//        ).build()
//    }
    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}
