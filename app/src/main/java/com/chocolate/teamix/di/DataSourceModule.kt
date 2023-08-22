package com.chocolate.teamix.di

import com.chocolate.local.dao.RoomDataSource
import com.chocolate.local.datastore.DataStoreDataSource
import com.chocolate.remote.RetrofitDataSource
import com.chocolate.repository.datastore.PreferencesDataSource
import com.chocolate.repository.service.local.LocalDataSource
import com.chocolate.repository.service.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindTeamixLocalDataSource(
        roomDataSource: RoomDataSource
    ): LocalDataSource

    @Singleton
    @Binds
    abstract fun bindPreferencesDataSource(
        dataStoreDataSource: DataStoreDataSource
    ): PreferencesDataSource

    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(retrofitDataSource: RetrofitDataSource): RemoteDataSource
}