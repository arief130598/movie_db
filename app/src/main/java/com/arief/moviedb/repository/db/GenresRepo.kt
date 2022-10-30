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

    suspend fun getSingle(id: Int) = db.genresTable.getSingle(id)

    suspend fun getList() = db.genresTable.getList()

    suspend fun insertList(genres: List<Genres>) = db.genresTable.insertList(genres)

    suspend fun insertSingle(genres: Genres) = db.genresTable.insertSingle(genres)

    suspend fun deleteAll() = db.genresTable.deleteAll()

    suspend fun deleteSingle(id: Int) = db.genresTable.deleteSingle(id)
}