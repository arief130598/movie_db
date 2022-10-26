package com.arief.moviedb.api

import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.GenresParams
import com.arief.moviedb.model.Movies
import com.arief.moviedb.model.MoviesParams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovieDB {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") api: String
    ): Response<GenresParams>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") api: String,
        @Query("page") page: Int,
    ): Response<MoviesParams>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Response<MoviesParams>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Response<MoviesParams>
}