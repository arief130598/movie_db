package com.arief.moviedb.core.di

import android.content.Context
import com.arief.moviedb.core.db.MovieDatabase
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
    private fun provideDatabase(context: Context) = MovieDatabase.getInstance(context)
}