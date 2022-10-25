package com.arief.moviedb

import android.app.Application
import com.arief.moviedb.module.dbModule
import com.arief.moviedb.module.networkModule
import com.arief.moviedb.module.repoModule
import com.arief.moviedb.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Apps : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Apps)
            modules(listOf(dbModule, networkModule, repoModule, viewModelModule))
        }
    }
}