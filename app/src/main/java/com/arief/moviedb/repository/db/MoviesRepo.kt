package com.arief.moviedb.repository.db

import com.arief.moviedb.db.MovieDatabase
import com.arief.moviedb.model.Movies

class MoviesRepo(private val db: MovieDatabase) {

    suspend fun getSingle(id: Int) = db.moviesTable.getSingle(id)

    suspend fun getList() = db.moviesTable.getList()

    suspend fun insertList(movies: List<Movies>) = db.moviesTable.insertList(movies)

    suspend fun insertSingle(movies: Movies) = db.moviesTable.insertSingle(movies)

    suspend fun deleteAll() = db.moviesTable.deleteAll()

    suspend fun deleteSingle(id: Int) = db.moviesTable.deleteSingle(id)
}