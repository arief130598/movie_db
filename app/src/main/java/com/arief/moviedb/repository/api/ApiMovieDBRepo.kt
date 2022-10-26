package com.arief.moviedb.repository.api

import com.arief.moviedb.api.ApiMovieDB
import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.GenresParams
import com.arief.moviedb.model.Movies
import com.arief.moviedb.model.MoviesParams
import retrofit2.Response

class ApiMovieDBRepo(private val apiMovieDB: ApiMovieDB) {

    suspend fun getGenres(api_key: String): Response<GenresParams> = apiMovieDB.getGenres(api_key)

    suspend fun getPopular(api_key: String, page:Int): Response<MoviesParams> = apiMovieDB.getPopular(api_key, page)
}