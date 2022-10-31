package com.arief.moviedb.repository.api

import com.arief.moviedb.api.ApiMovieDB
import com.arief.moviedb.model.GenresResponse
import com.arief.moviedb.model.MoviesResponse
import retrofit2.Response

/**
 *
 * Repository to call Movie DB from its interface <br/>
 * Repository name must be same from interface function
 *
 */
class ApiMovieDBRepo(private val apiMovieDB: ApiMovieDB) {

    suspend fun getGenres(api_key: String): Response<GenresResponse> = apiMovieDB.getGenres(api_key)

    suspend fun getPopular(api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getPopular(api_key, page)

    suspend fun getNowPlaying(api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getNowPlaying(api_key, page)

    suspend fun getUpcoming(api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getUpcoming(api_key, page)

    suspend fun getSearch(api_key: String, query:String, page:Int): Response<MoviesResponse> = apiMovieDB.getSearch(api_key, query, page)

    suspend fun getSimilar(movie_id: Int, api_key: String, page:Int): Response<MoviesResponse> = apiMovieDB.getSimilar(movie_id, api_key, page)
}