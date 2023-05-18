package com.arief.moviedb.data.di

import android.content.Context
import com.arief.moviedb.data.datasource.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = MovieDatabase.getInstance(context)
}