package com.arief.moviedb.feature.search.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arief.moviedb.BuildConfig
import com.arief.moviedb.model.Movies
import com.arief.moviedb.repository.api.ApiMovieDBRepo
import com.arief.moviedb.core.utils.NetworkHelper
import com.arief.moviedb.core.utils.Resource
import kotlinx.coroutines.*

class SearchViewModel(private val apiMovieDBRepo: ApiMovieDBRepo,
                      private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>>
        get() = _movies

    var page = 0
    var listLoadedMovies: MutableList<Movies> = mutableListOf()

    fun getMovies(query: String) {
        page += 1
        viewModelScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieDBRepo.getSearch(BuildConfig.API_KEY, query, page).let {
                    if (it.isSuccessful) {
                        if (it.body() != null) {
                            _movies.postValue(Resource.success(it.body()!!.results))
                            listLoadedMovies.addAll(it.body()!!.results)
                        } else {
                            _movies.postValue(Resource.success(listOf()))
                        }
                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _movies.postValue(Resource.error("No internet connection", null))
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}