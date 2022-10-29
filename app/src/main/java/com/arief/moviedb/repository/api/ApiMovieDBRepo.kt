package com.arief.moviedb.repository.api

import com.arief.moviedb.api.ApiMovieDB
import com.arief.moviedb.model.GenresParams
import com.arief.moviedb.model.MoviesParams
import retrofit2.Response

class ApiMovieDBRepo(private val apiMovieDB: ApiMovieDB) {

    suspend fun getGenres(api_key: String): Response<GenresParams> = apiMovieDB.getGenres(api_key)

    suspend fun getPopular(api_key: String, page:Int): Response<MoviesParams> = apiMovieDB.getPopular(api_key, page)

    suspend fun getNowPlaying(api_key: String, page:Int): Response<MoviesParams> = apiMovieDB.getNowPlaying(api_key, page)

    suspend fun getUpcoming(api_key: String, page:Int): Response<MoviesParams> = apiMovieDB.getUpcoming(api_key, page)

    suspend fun getSearch(api_key: String, query:String, page:Int): Response<MoviesParams> = apiMovieDB.getSearch(api_key, query, page)
}