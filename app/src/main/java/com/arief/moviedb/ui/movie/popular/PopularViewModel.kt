package com.arief.moviedb.ui.movie.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arief.moviedb.BuildConfig
import com.arief.moviedb.model.Movies
import com.arief.moviedb.repository.api.ApiMovieDBRepo
import com.arief.moviedb.utils.NetworkHelper
import com.arief.moviedb.utils.Resource
import kotlinx.coroutines.*

class PopularViewModel(private val apiMovieDBRepo: ApiMovieDBRepo,
                       private val networkHelper: NetworkHelper) : ViewModel() {

    private val ioScope = CoroutineScope(Job() + Dispatchers.IO)

    private val _movies = MutableLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>>
        get() = _movies

    private var page = 0
    var lastPositionAdapter = 0
    var listLoadedMovies: MutableList<Movies> = mutableListOf()

    init {
        getMovies()
    }

    fun getMovies(){
        page += 1
        ioScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieDBRepo.getPopular(BuildConfig.API_KEY, page).let {
                    if (it.isSuccessful) {
                        if(it.body() != null) {
                            _movies.postValue(Resource.success(it.body()!!.results))
                            listLoadedMovies.addAll(it.body()!!.results)
                        }else{
                            _movies.postValue(Resource.success(listOf()))
                        }
                    } else _movies.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _movies.postValue(Resource.error("No internet connection", null))
        }
    }

    fun setLastPosition(position: Int) {
        lastPositionAdapter = position
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}