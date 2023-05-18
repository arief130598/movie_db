package com.arief.moviedb.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arief.moviedb.domain.model.Genres

@Dao
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