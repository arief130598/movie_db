package com.arief.moviedb.repository.api

import com.arief.moviedb.api.ApiMovieDB
import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.Movies
import retrofit2.Response

class ApiMovieDBRepo(private val apiMovieDB: ApiMovieDB) {

    suspend fun getGenres(): Response<List<Genres>> = apiMovieDB.getGenres()

    suspend fun getMovies(): Response<List<Movies>> = apiMovieDB.getMovies()
}