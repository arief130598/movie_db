package com.arief.moviedb.model

/**
 *
 * Model class for receiving API to get list of Genres
 *
 * @property genres
 */
data class GenresParams(
    var genres: List<Genres> = listOf()
)
