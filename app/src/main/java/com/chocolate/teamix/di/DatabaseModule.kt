package com.chocolate.teamix.di

import android.content.Context
import androidx.room.Room
import com.chocolate.local.dao.TeamixDao
import com.chocolate.local.database.TeamixDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "databaseName"

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named(DATABASE_NAME) databaseName: String
    ): TeamixDatabase {
        return Room.databaseBuilder(context, TeamixDatabase::class.java, databaseName).build()
    }

    @Singleton
    @Provides
    @Named(DATABASE_NAME)
    fun provideDataBaseName(): String = "TeamixDatabase.db"

    @Singleton
    @Provides
    fun provideDao(teamixDatabase: TeamixDatabase): TeamixDao = teamixDatabase.teamixDao
}