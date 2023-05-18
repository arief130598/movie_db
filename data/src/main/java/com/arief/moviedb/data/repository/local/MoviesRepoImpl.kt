package com.arief.moviedb.data.repository.local

import com.arief.moviedb.data.datasource.local.MovieDatabase
import com.arief.moviedb.domain.model.Movies
import javax.inject.Inject

/**
 *
 * Repository to call from Database -> Genres Table <br/>
 * Repository name must be same from Movies DAO
 *
 */
class MoviesRepoImpl @Inject constructor(private val db: MovieDatabase) {

    suspend fun getSingle(id: Int): Movies = db.moviesTable.getSingle(id)

    suspend fun getList(): List<Movies> = db.moviesTable.getList()

    suspend fun insertList(movies: List<Movies>): List<Long> = db.moviesTable.insertList(movies)

    suspend fun insertSingle(movies: Movies): Long = db.moviesTable.insertSingle(movies)

    suspend fun deleteAll(): Int = db.moviesTable.deleteAll()

    suspend fun deleteSingle(id: Int): Int = db.moviesTable.deleteSingle(id)
}