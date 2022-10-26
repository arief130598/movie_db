package com.arief.moviedb.module

import com.arief.moviedb.ui.nowplaying.NowPlayingViewModel
import com.arief.moviedb.ui.popular.PopularViewModel
import com.arief.moviedb.ui.upcoming.UpcomingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularViewModel(get(), get(), get(), get()) }
    viewModel { NowPlayingViewModel(get(), get(), get(), get()) }
    viewModel { UpcomingViewModel(get(), get(), get(), get()) }
}