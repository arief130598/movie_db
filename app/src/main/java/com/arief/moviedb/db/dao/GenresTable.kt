package com.arief.moviedb.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.Movies

interface GenresTable {
    @Query("SELECT * FROM genres")
    fun getList(): List<Genres>

    @Query("SELECT * FROM genres WHERE id = :id")
    fun getSingle(id: Int): Genres

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(movies: List<Genres>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(movies: Genres): Long

    @Query("DELETE FROM genres")
    fun deleteAll(): Int

    @Query("DELETE FROM genres WHERE id = :id")
    fun deleteSingle(id: Int): Int
}