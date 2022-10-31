package com.arief.moviedb.repository.db

import com.arief.moviedb.db.MovieDatabase
import com.arief.moviedb.model.Genres

/**
 *
 * Repository to call from Database -> Genres Table <br/>
 * Repository name must be same from Genres DAO
 *
 */
class GenresRepo(private val db: MovieDatabase) {

    suspend fun getSingle(id: Int): Genres = db.genresTable.getSingle(id)

    suspend fun getList(): List<Genres> = db.genresTable.getList()

    suspend fun insertList(genres: List<Genres>): List<Long> = db.genresTable.insertList(genres)

    suspend fun insertSingle(genres: Genres): Long = db.genresTable.insertSingle(genres)

    suspend fun deleteAll(): Int = db.genresTable.deleteAll()

    suspend fun deleteSingle(id: Int): Int = db.genresTable.deleteSingle(id)
}