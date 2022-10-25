package com.arief.moviedb.module

import android.content.Context
import com.arief.moviedb.db.MovieDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { provideDatabase(androidContext()) }
}

private fun provideDatabase(context: Context) = MovieDatabase.getInstance(context)
