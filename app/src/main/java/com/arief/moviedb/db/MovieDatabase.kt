package com.arief.moviedb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arief.moviedb.db.dao.GenresTable
import com.arief.moviedb.db.dao.MoviesTable
import com.arief.moviedb.model.Movies

@Database(
    entities = [ // Add class to add new DB,
        Movies::class
    ],
    version = 10000, // Change db version with apps version
    exportSchema = false)

abstract class MovieDatabase : RoomDatabase() {

    abstract val moviesTable: MoviesTable
    abstract val genresTable: GenresTable

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "moviedb.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}