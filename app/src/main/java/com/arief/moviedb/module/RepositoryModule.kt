package com.arief.moviedb.module

import com.arief.moviedb.repository.api.ApiMovieDBRepo
import com.arief.moviedb.repository.db.GenresRepo
import com.arief.moviedb.repository.db.MoviesRepo
import org.koin.dsl.module

/**
 *
 * Module to Provide Repository from API or DB Repo
 *
 */
val repoModule = module {
    single { ApiMovieDBRepo(get()) }
    single { GenresRepo(get()) }
    single { MoviesRepo(get()) }
}