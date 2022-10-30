package com.arief.moviedb.module

import com.arief.moviedb.ui.detail.DetailViewModel
import com.arief.moviedb.ui.movie.MovieViewModel
import com.arief.moviedb.ui.movie.nowplaying.NowPlayingViewModel
import com.arief.moviedb.ui.movie.popular.PopularViewModel
import com.arief.moviedb.ui.movie.upcoming.UpcomingViewModel
import com.arief.moviedb.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get(), get(), get(), get()) }
    viewModel { PopularViewModel(get(), get()) }
    viewModel { NowPlayingViewModel(get(), get()) }
    viewModel { UpcomingViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
}