package com.example.network.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
/*
    @Binds
    abstract fun bindAuthRemoteDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ) : AuthDataSource
*/

}