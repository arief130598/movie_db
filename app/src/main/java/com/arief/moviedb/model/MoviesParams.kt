package com.arief.moviedb.model

/**
 *
 * Model class for receiving API to get list of Movies
 *
 */
data class MoviesParams(
    var page:Int = 0,
    var results: List<Movies> = listOf(),
    var total_pages:Int = 0,
    var total_results:Int = 0
)
