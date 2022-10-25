package com.arief.moviedb.api

import com.arief.moviedb.model.Genres
import com.arief.moviedb.model.Movies
import retrofit2.Response
import retrofit2.http.GET

interface ApiMovieDB {

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<List<Genres>>

    @GET("movie/now_playing")
    suspend fun getMovies(): Response<List<Movies>>
}