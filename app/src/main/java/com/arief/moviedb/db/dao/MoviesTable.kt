package com.arief.moviedb.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arief.moviedb.model.Movies

@Dao
interface MoviesTable {
    @Query("SELECT * FROM movies")
    fun getList(): List<Movies>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getSingle(id: Int): Movies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(movies: List<Movies>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(movies: Movies): Long

    @Query("DELETE FROM movies")
    fun deleteAll(): Int

    @Query("DELETE FROM movies WHERE id = :id")
    fun deleteSingle(id: Int): Int
}