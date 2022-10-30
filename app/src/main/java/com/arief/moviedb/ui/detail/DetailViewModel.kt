package com.arief.moviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arief.moviedb.BuildConfig
import com.arief.moviedb.model.Movies
import com.arief.moviedb.repository.api.ApiMovieDBRepo
import com.arief.moviedb.utils.NetworkHelper
import com.arief.moviedb.utils.Resource
import kotlinx.coroutines.launch


class DetailViewModel(private val apiMovieDBRepo: ApiMovieDBRepo,
                      private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movies>>>()
    val movies: LiveData<Resource<List<Movies>>>
        get() = _movies

    var page = 0

    /**
     * Call MovieDB API to get the similar movies from selected movie in detail
     *
     * @param movie_id
     */
    fun getSimilar(movie_id: Int) {
        page += 1
        viewModelScope.launch {
            _movies.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiMovieDBRepo.getSimilar(movie_id, BuildConfig.API_KEY, page).let {
                    if (it.isSuccessful) {
                        if (it.body() != null) {
                            val data = mutableListOf<Movies>()
                            for(i in it.body()!!.results.indices){
                                if(i < 5){
                                    data.add(it.body()!!.results[i])
                                }else{
                                    break
                                }
                            }
                            _movies.postValue(Resource.success(data))
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