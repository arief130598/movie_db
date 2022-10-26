package com.arief.moviedb.module

import com.arief.moviedb.ui.popular.PopularViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PopularViewModel(get(), get(), get(), get()) }
}